package datastructures;

import models.Customer;

/**
 * ╔══════════════════════════════════════════════════════════╗
 * ║         SINGLY LINKED LIST  —  Customer Registry        ║
 * ╚══════════════════════════════════════════════════════════╝
 *
 * Each node holds a Customer and a reference to the next node.
 *
 *   head → [C1] → [C2] → [C3] → null
 *
 * Operations:
 *   addCustomer()    — Iterative  : append at tail
 *   removeCustomer() — Iterative  : unlink by ID
 *   displayAll()     — Iterative  : traverse head → null
 *   searchById()     — RECURSIVE  : traverses nodes recursively
 *   searchByName()   — RECURSIVE  : partial-match, prints all hits
 */
public class CustomerSLL {

    // ── Inner node class ───────────────────────────────────────
    private static class Node {
        Customer data;
        Node     next;
        Node(Customer data) { this.data = data; this.next = null; }
    }

    // ── Fields ─────────────────────────────────────────────────
    private Node head;
    public  int  size;

    public CustomerSLL() { head = null; size = 0; }

    // ── Helpers ────────────────────────────────────────────────
    public boolean isEmpty() { return head == null; }

    // ══════════════════════════════════════════════════════════
    //  ITERATIVE OPERATIONS
    // ══════════════════════════════════════════════════════════

    /** Append a new customer at the tail of the list. */
    public void addCustomer(Customer c) {
        Node newNode = new Node(c);
        if (head == null) {
            head = newNode;
        } else {
            Node cur = head;
            while (cur.next != null) cur = cur.next;   // traverse to tail
            cur.next = newNode;
        }
        size++;
    }

    /** Remove a customer by ID. Returns true if found and removed. */
    public boolean removeCustomer(int id) {
        if (head == null) return false;
        if (head.data.customerId == id) {
            head = head.next;
            size--;
            return true;
        }
        Node cur = head;
        while (cur.next != null) {
            if (cur.next.data.customerId == id) {
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /** Display every customer from head to tail. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("  [!] No customers registered.");
            return;
        }
        System.out.println("\n  ┌─ Customer Registry (SLL) ──────────────────────────────────────────────────┐");
        Node cur = head;
        int  idx = 1;
        while (cur != null) {
            System.out.println("  │ " + idx++ + ". " + cur.data);
            cur = cur.next;
        }
        System.out.println("  └─ Total: " + size + " customer(s) ───────────────────────────────────────────────────┘");
    }

    // ══════════════════════════════════════════════════════════
    //  RECURSIVE OPERATIONS
    // ══════════════════════════════════════════════════════════

    /**
     * RECURSIVE search by customer ID.
     * Base case  : node == null  → not found.
     * Recursive  : if no match, call self on node.next.
     */
    public Customer searchById(int id) {
        return searchByIdRec(head, id);
    }

    private Customer searchByIdRec(Node node, int id) {
        if (node == null) return null;                          // base case
        if (node.data.customerId == id) return node.data;      // found
        return searchByIdRec(node.next, id);                   // recurse
    }

    /**
     * RECURSIVE search by name (partial, case-insensitive).
     * Prints every match and returns the total count found.
     */
    public void searchByName(String name) {
        System.out.println("\n  Search results for \"" + name + "\":");
        int found = searchByNameRec(head, name.toLowerCase(), 0);
        if (found == 0) System.out.println("  [!] No matching customer found.");
        else            System.out.println("  >> " + found + " result(s) found.");
    }

    private int searchByNameRec(Node node, String name, int count) {
        if (node == null) return count;                         // base case
        if (node.data.name.toLowerCase().contains(name)) {
            System.out.println("  >> " + node.data);
            count++;
        }
        return searchByNameRec(node.next, name, count);        // recurse
    }
}
