package datastructures;

import models.Order;

/**
 * ╔══════════════════════════════════════════════════════════╗
 * ║          QUEUE (SLL-based)  —  Pending Orders           ║
 * ╚══════════════════════════════════════════════════════════╝
 *
 * FIFO: new orders join at the REAR, deliveries leave from the FRONT.
 *
 *   front → [O1] → [O2] → [O3] → null
 *                                  ↑
 *                                 rear
 *
 * Operations:
 *   enqueue()              — Iterative : insert at rear
 *   dequeue()              — Iterative : remove from front
 *   peek()                 — Iterative : inspect front without removing
 *   displayAll()           — Iterative : front → rear traversal
 *   countForCustomer()     — RECURSIVE : count orders matching a customer ID
 *   totalPendingWeight()   — RECURSIVE : sum weight of all queued orders
 */
public class OrderQueue {

    // ── Inner node class ───────────────────────────────────────
    private static class Node {
        Order data;
        Node  next;
        Node(Order data) { this.data = data; this.next = null; }
    }

    // ── Fields ─────────────────────────────────────────────────
    private Node front, rear;
    public  int  size;

    public OrderQueue() { front = rear = null; size = 0; }

    // ── Helpers ────────────────────────────────────────────────
    public boolean isEmpty() { return front == null; }

    // ══════════════════════════════════════════════════════════
    //  ITERATIVE OPERATIONS
    // ══════════════════════════════════════════════════════════

    /** Enqueue: add a new order at the rear. */
    public void enqueue(Order o) {
        Node newNode = new Node(o);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /** Dequeue: remove and return the front order (FIFO). */
    public Order dequeue() {
        if (isEmpty()) return null;
        Order o = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return o;
    }

    /** Peek: inspect the front order without removing it. */
    public Order peek() {
        return isEmpty() ? null : front.data;
    }

    /** Display the full queue from front to rear. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("  [!] No pending orders in queue.");
            return;
        }
        System.out.println("\n  ┌─ Pending Orders Queue  (FIFO — front to rear) ─────────────────────────────┐");
        Node cur = front;
        int  pos = 1;
        while (cur != null) {
            System.out.println("  │ [" + pos++ + "] " + cur.data);
            cur = cur.next;
        }
        System.out.println("  └─ Queue size: " + size + " ──────────────────────────────────────────────────────────┘");
    }

    // ══════════════════════════════════════════════════════════
    //  RECURSIVE OPERATIONS
    // ══════════════════════════════════════════════════════════

    /**
     * RECURSIVE: count how many pending orders belong to a given customer.
     * Base case : node == null  → return accumulated count.
     * Recursive : check current node, then recurse on next.
     */
    public int countForCustomer(int customerId) {
        return countForCustomerRec(front, customerId);
    }

    private int countForCustomerRec(Node node, int customerId) {
        if (node == null) return 0;                            // base case
        int hit = (node.data.customerId == customerId) ? 1 : 0;
        return hit + countForCustomerRec(node.next, customerId);  // recurse
    }

    /**
     * RECURSIVE: sum the total weight of all orders in the queue.
     * Base case : node == null  → 0.0
     * Recursive : current weight + sum of rest.
     */
    public double totalPendingWeight() {
        return totalPendingWeightRec(front);
    }

    private double totalPendingWeightRec(Node node) {
        if (node == null) return 0.0;                          // base case
        return node.data.weight + totalPendingWeightRec(node.next);  // recurse
    }
}
