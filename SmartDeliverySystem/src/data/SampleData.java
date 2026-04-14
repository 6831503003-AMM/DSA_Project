package data;

import datastructures.CustomerSLL;
import datastructures.DeliveryStack;
import datastructures.OrderQueue;
import datastructures.RouteDLL;
import models.Customer;
import models.Order;

/**
 * Pre-loads realistic sample data into all four data structures
 * so the system can be demonstrated immediately on startup.
 *
 * Data loaded:
 *   • 5 customers  → CustomerSLL
 *   • 4 orders     → OrderQueue  (pending)
 *   • 2 orders     → DeliveryStack (already delivered)
 *   • 7 route stops → RouteDLL
 */
public class SampleData {

    private SampleData() { /* static utility — no instances */ }

    public static void load(CustomerSLL customers,
                            OrderQueue   pendingOrders,
                            DeliveryStack history,
                            RouteDLL     route,
                            int[]        orderIdCounter,
                            int[]        custIdCounter) {

        // ── Customers ──────────────────────────────────────────
        customers.addCustomer(new Customer(custIdCounter[0]++, "Alice Johnson",    "123 Maple Street",     "081-234-5678"));
        customers.addCustomer(new Customer(custIdCounter[0]++, "Bob Smith",        "456 Oak Avenue",       "082-345-6789"));
        customers.addCustomer(new Customer(custIdCounter[0]++, "Charlie Brown",    "789 Pine Road",        "083-456-7890"));
        customers.addCustomer(new Customer(custIdCounter[0]++, "Diana Prince",     "321 Elm Street",       "084-567-8901"));
        customers.addCustomer(new Customer(custIdCounter[0]++, "Edward Lee",       "654 Birch Boulevard",  "085-678-9012"));

        // Set realistic order counts
        Customer alice   = customers.searchById(1);
        Customer bob     = customers.searchById(2);
        Customer charlie = customers.searchById(3);
        if (alice   != null) alice.totalOrders   = 2;
        if (bob     != null) bob.totalOrders     = 1;
        if (charlie != null) charlie.totalOrders = 1;

        // ── Pending Orders (Queue) ─────────────────────────────
        pendingOrders.enqueue(new Order(orderIdCounter[0]++, 1, "Alice Johnson",  "123 Maple Street",    "Laptop",      2.5));
        pendingOrders.enqueue(new Order(orderIdCounter[0]++, 2, "Bob Smith",      "456 Oak Avenue",      "Books x3",    1.8));
        pendingOrders.enqueue(new Order(orderIdCounter[0]++, 3, "Charlie Brown",  "789 Pine Road",       "Headphones",  0.6));
        pendingOrders.enqueue(new Order(orderIdCounter[0]++, 1, "Alice Johnson",  "123 Maple Street",    "Phone Case",  0.2));

        // ── Delivery History (Stack) ───────────────────────────
        Order h1 = new Order(998, 5, "Edward Lee",   "654 Birch Boulevard", "Monitor",  5.4);
        Order h2 = new Order(999, 4, "Diana Prince", "321 Elm Street",      "Keyboard", 1.1);
        h1.setStatus("DELIVERED");
        h2.setStatus("DELIVERED");
        history.push(h1);   // pushed first → deeper in stack
        history.push(h2);   // h2 is on top (most recent)

        // ── Route Stops (DLL) ─────────────────────────────────
        route.addStop("Warehouse (Start)",            0.0);
        route.addStop("123 Maple St   — Alice",       5.2);
        route.addStop("456 Oak Ave    — Bob",         3.8);
        route.addStop("789 Pine Rd    — Charlie",     6.1);
        route.addStop("321 Elm St     — Diana",       2.5);
        route.addStop("654 Birch Blvd — Edward",      4.3);
        route.addStop("Warehouse (Return)",           7.9);
    }
}
