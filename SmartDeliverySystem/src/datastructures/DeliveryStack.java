package datastructures;

import models.Order;

/**
 * ╔══════════════════════════════════════════════════════════╗
 * ║        STACK (SLL-based)  —  Delivery History           ║
 * ╚══════════════════════════════════════════════════════════╝
 *
 * LIFO: each completed delivery is pushed on top.
 * The top of the stack is always the MOST RECENT delivery.
 *
 *   top → [Latest] → [Older] → [Oldest] → null
 *
 * Operations:
 *   push()                 — Iterative : insert at top
 *   pop()                  — Iterative : remove from top
 *   peek()                 — Iterative : inspect top without removing
 *   displayAll()           — Iterative : top → bottom traversal
 *   totalWeightDelivered() — RECURSIVE : sum weight of all past deliveries
 *   countByCustomer()      — RECURSIVE : count deliveries matching a name
 */
public class DeliveryStack {

    // ── Inner node class ───────────────────────────────────────
    private static class Node {
        Order data;
        Node  next;
        Node(Order data) { this.data = data; this.next = null; }
    }

    // ── Fields ─────────────────────────────────────────────────
    private Node top;
    public  int  size;

    public DeliveryStack() { top = null; size = 0; }

    // ── Helpers ────────────────────────────────────────────────
    public boolean isEmpty() { return top == null; }

    // ══════════════════════════════════════════════════════════
    //  ITERATIVE OPERATIONS
    // ══════════════════════════════════════════════════════════

    /** Push: place a delivered order on top of the stack. */
    public void push(Order o) {
        Node newNode = new Node(o);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /** Pop: remove and return the most recent delivery. */
    public Order pop() {
        if (isEmpty()) return null;
        Order o = top.data;
        top = top.next;
        size--;
        return o;
    }

    /** Peek: inspect the most recent delivery without removing it. */
    public Order peek() {
        return isEmpty() ? null : top.data;
    }

    /** Display all history entries from top (most recent) to bottom. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("  [!] No delivery history yet.");
            return;
        }
        System.out.println("\n  ┌─ Delivery History  (Stack — most recent first) ────────────────────────────┐");
        Node cur = top;
        int  idx = 1;
        while (cur != null) {
            System.out.println("  │ [" + idx++ + "] " + cur.data);
            cur = cur.next;
        }
        System.out.println("  └─ Total deliveries completed: " + size + " ───────────────────────────────────────┘");
    }

    // ══════════════════════════════════════════════════════════
    //  RECURSIVE OPERATIONS
    // ══════════════════════════════════════════════════════════

    /**
     * RECURSIVE: sum the weight of every completed delivery.
     * Base case : node == null  → 0.0
     * Recursive : current weight + total of the rest.
     */
    public double totalWeightDelivered() {
        return totalWeightRec(top);
    }

    private double totalWeightRec(Node node) {
        if (node == null) return 0.0;                          // base case
        return node.data.weight + totalWeightRec(node.next);  // recurse
    }

    /**
     * RECURSIVE: count deliveries whose customer name contains the given string.
     * Base case : node == null  → 0
     * Recursive : check current, then recurse on next.
     */
    public int countByCustomer(String name) {
        return countByCustomerRec(top, name.toLowerCase());
    }

    private int countByCustomerRec(Node node, String name) {
        if (node == null) return 0;                            // base case
        int hit = node.data.customerName.toLowerCase().contains(name) ? 1 : 0;
        return hit + countByCustomerRec(node.next, name);     // recurse
    }
}
