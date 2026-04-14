package datastructures;

import models.RouteStop;

/**
 * ╔══════════════════════════════════════════════════════════╗
 * ║       DOUBLY LINKED LIST  —  Delivery Route             ║
 * ╚══════════════════════════════════════════════════════════╝
 *
 * Each node has BOTH a next and a prev pointer.
 * This enables efficient forward (outbound) AND backward (return)
 * traversal — a key advantage over a singly linked list.
 *
 *   null ← [Warehouse] ⇄ [Stop1] ⇄ [Stop2] ⇄ [Stop3] → null
 *            ↑ head                              ↑ tail
 *
 * Operations:
 *   addStop()              — Iterative : append at tail
 *   insertAfter()          — Iterative : insert node between two existing nodes
 *   removeStop()           — Iterative : unlink a node by location name
 *   displayForward()       — Iterative : head → tail  (outbound route)
 *   displayBackward()      — Iterative : tail → head  (return path, uses prev)
 *   totalDistance()        — RECURSIVE : sum all distanceFromPrev values
 *   displayForwardRecursive() — RECURSIVE : print stops head → tail
 */
public class RouteDLL {

    // ── Inner node class ───────────────────────────────────────
    private static class Node {
        RouteStop data;
        Node      prev, next;
        Node(RouteStop data) { this.data = data; prev = next = null; }
    }

    // ── Fields ─────────────────────────────────────────────────
    private Node head, tail;
    public  int  size;

    public RouteDLL() { head = tail = null; size = 0; }

    // ── Helpers ────────────────────────────────────────────────
    public boolean isEmpty() { return head == null; }

    // ══════════════════════════════════════════════════════════
    //  ITERATIVE OPERATIONS
    // ══════════════════════════════════════════════════════════

    /** Append a new stop at the end of the route. */
    public void addStop(String location, double distanceFromPrev) {
        Node newNode = new Node(new RouteStop(location, distanceFromPrev));
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next    = newNode;
            newNode.prev = tail;
            tail         = newNode;
        }
        size++;
    }

    /**
     * Insert a new stop immediately after an existing location.
     * Returns true on success, false if afterLocation is not found.
     */
    public boolean insertAfter(String afterLocation, String newLocation, double dist) {
        Node cur = head;
        while (cur != null) {
            if (cur.data.location.equalsIgnoreCase(afterLocation)) {
                Node newNode  = new Node(new RouteStop(newLocation, dist));
                newNode.next  = cur.next;
                newNode.prev  = cur;
                if (cur.next != null) cur.next.prev = newNode;
                else                  tail          = newNode;
                cur.next = newNode;
                size++;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /** Remove a stop by its location name (case-insensitive). */
    public boolean removeStop(String location) {
        Node cur = head;
        while (cur != null) {
            if (cur.data.location.equalsIgnoreCase(location)) {
                if (cur.prev != null) cur.prev.next = cur.next; else head = cur.next;
                if (cur.next != null) cur.next.prev = cur.prev; else tail = cur.prev;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /** Iterative forward traversal: head → tail (outbound delivery path). */
    public void displayForward() {
        if (isEmpty()) { System.out.println("  [!] No route stops defined."); return; }
        System.out.println("\n  ┌─ Delivery Route — FORWARD  (DLL head → tail) ──────────────────────────────┐");
        Node   cur = head;
        double cum = 0.0;
        int    s   = 1;
        while (cur != null) {
            cum += cur.data.distanceFromPrev;
            System.out.printf("  │ Stop %-2d ▶ %-38s | +%.1f km | Total: %.1f km%n",
                s++, cur.data.location, cur.data.distanceFromPrev, cum);
            cur = cur.next;
        }
        System.out.printf("  └─ Total outbound distance: %.2f km ───────────────────────────────────────────┘%n", cum);
    }

    /** Iterative backward traversal: tail → head (return path — uses prev pointer). */
    public void displayBackward() {
        if (isEmpty()) { System.out.println("  [!] No route stops defined."); return; }
        System.out.println("\n  ┌─ Return Path — BACKWARD  (DLL tail → head, using prev pointers) ──────────┐");
        Node cur = tail;
        int  s   = size;
        while (cur != null) {
            System.out.printf("  │ Stop %-2d ◀ %-38s | %.1f km%n",
                s--, cur.data.location, cur.data.distanceFromPrev);
            cur = cur.prev;
        }
        System.out.println("  └───────────────────────────────────────────────────────────────────────────┘");
    }

    // ══════════════════════════════════════════════════════════
    //  RECURSIVE OPERATIONS
    // ══════════════════════════════════════════════════════════

    /**
     * RECURSIVE: compute the total route distance.
     * Base case : node == null  → 0.0
     * Recursive : current distance + total of remaining stops.
     */
    public double totalDistance() {
        return totalDistanceRec(head);
    }

    private double totalDistanceRec(Node node) {
        if (node == null) return 0.0;                                  // base case
        return node.data.distanceFromPrev + totalDistanceRec(node.next); // recurse
    }

    /**
     * RECURSIVE: display all stops from head to tail with cumulative distance.
     * Base case : node == null  → stop.
     * Recursive : print current stop, then recurse on next.
     */
}
