package ui;

import datastructures.CustomerSLL;
import datastructures.DeliveryStack;
import datastructures.OrderQueue;
import datastructures.RouteDLL;
import models.Customer;
import models.Order;
import utils.InputHelper;

/**
 * Handles all console UI rendering and user interaction.
 * Delegates data operations to the four data-structure classes.
 *
 * Sub-menus:
 *   manageCustomers()     — SLL  operations
 *   manageOrders()        — Queue operations
 *   manageRoute()         — DLL  operations
 *   viewHistory()         — Stack display
 *   processNextDelivery() — dequeue → deliver → push
 *   searchMenu()          — recursive search demos
 *   viewStatistics()      — aggregated system stats
 */
public class Menu {

    // ── Shared data structures ─────────────────────────────────
    private final CustomerSLL   customers;
    private final OrderQueue    pendingOrders;
    private final DeliveryStack history;
    private final RouteDLL      route;

    // ── ID counters (passed in from Main) ─────────────────────
    private final int[] nextOrderId;
    private final int[] nextCustId;

    public Menu(CustomerSLL customers, OrderQueue pendingOrders,
                DeliveryStack history, RouteDLL route,
                int[] nextOrderId, int[] nextCustId) {
        this.customers     = customers;
        this.pendingOrders = pendingOrders;
        this.history       = history;
        this.route         = route;
        this.nextOrderId   = nextOrderId;
        this.nextCustId    = nextCustId;
    }

    // ══════════════════════════════════════════════════════════
    //  MAIN MENU
    // ══════════════════════════════════════════════════════════

    public void printMainMenu() {
        System.out.println("\n  ╔══════════════════════════════════════════╗");
        System.out.println("  ║              MAIN MENU                   ║");
        System.out.println("  ╠══════════════════════════════════════════╣");
        System.out.println("  ║  1. Manage Customers      [SLL]          ║");
        System.out.println("  ║  2. Manage Orders         [Queue]        ║");
        System.out.println("  ║  3. Manage Delivery Route [DLL]          ║");
        System.out.println("  ║  4. View Delivery History [Stack]        ║");
        System.out.println("  ║  5. Process Next Delivery                ║");
        System.out.println("  ║  6. Search                               ║");
        System.out.println("  ║  7. System Statistics                    ║");
        System.out.println("  ║  0. Exit                                 ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
    }

    // ══════════════════════════════════════════════════════════
    //  [1] CUSTOMER MANAGEMENT  —  SLL
    // ══════════════════════════════════════════════════════════

    public void manageCustomers() {
        boolean back = false;
        while (!back) {
            System.out.println("\n  ╔══════════════════════════════════════════╗");
            System.out.println("  ║      CUSTOMER MANAGEMENT  (SLL)          ║");
            System.out.println("  ╠══════════════════════════════════════════╣");
            System.out.println("  ║  1. View All Customers                   ║");
            System.out.println("  ║  2. Add New Customer                     ║");
            System.out.println("  ║  3. Remove Customer by ID                ║");
            System.out.println("  ║  0. Back to Main Menu                    ║");
            System.out.println("  ╚══════════════════════════════════════════╝");

            switch (InputHelper.readInt("  Enter choice: ")) {
                case 1:
                    customers.displayAll();
                    break;
                case 2:
                    String name = InputHelper.readLine("  Name    : ");
                    String addr = InputHelper.readLine("  Address : ");
                    String ph   = InputHelper.readLine("  Phone   : ");
                    Customer nc = new Customer(nextCustId[0]++, name, addr, ph);
                    customers.addCustomer(nc);
                    System.out.println("  [✓] Customer added — ID: " + nc.customerId);
                    break;
                case 3:
                    customers.displayAll();
                    int rid = InputHelper.readInt("  Enter Customer ID to remove: ");
                    if (customers.removeCustomer(rid))
                        System.out.println("  [✓] Customer #" + rid + " removed.");
                    else
                        System.out.println("  [!] Customer ID " + rid + " not found.");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("  [!] Invalid choice.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    //  [2] ORDER MANAGEMENT  —  Queue
    // ══════════════════════════════════════════════════════════

    public void manageOrders() {
        boolean back = false;
        while (!back) {
            System.out.println("\n  ╔══════════════════════════════════════════╗");
            System.out.println("  ║        ORDER MANAGEMENT  (Queue)         ║");
            System.out.println("  ╠══════════════════════════════════════════╣");
            System.out.println("  ║  1. View Pending Orders Queue            ║");
            System.out.println("  ║  2. Place New Order    (Enqueue)         ║");
            System.out.println("  ║  3. Peek at Next Order (Front)           ║");
            System.out.println("  ║  4. Cancel Front Order (Dequeue)         ║");
            System.out.println("  ║  0. Back                                 ║");
            System.out.println("  ╚══════════════════════════════════════════╝");

            switch (InputHelper.readInt("  Enter choice: ")) {
                case 1:
                    pendingOrders.displayAll();
                    break;
                case 2:
                    customers.displayAll();
                    int cid = InputHelper.readInt("  Enter Customer ID: ");
                    Customer cust = customers.searchById(cid);
                    if (cust == null) { System.out.println("  [!] Customer not found."); break; }
                    String item = InputHelper.readLine("  Item name  : ");
                    double wt   = InputHelper.readDouble("  Weight (kg): ");
                    Order no = new Order(nextOrderId[0]++, cust.customerId, cust.name, cust.address, item, wt);
                    pendingOrders.enqueue(no);
                    cust.totalOrders++;
                    System.out.println("  [✓] Order #" + no.orderId + " enqueued.");
                    break;
                case 3:
                    Order peek = pendingOrders.peek();
                    if (peek == null) System.out.println("  [!] Queue is empty.");
                    else              System.out.println("  Next to deliver: " + peek);
                    break;
                case 4:
                    Order cancelled = pendingOrders.dequeue();
                    if (cancelled == null) System.out.println("  [!] Queue is empty.");
                    else                   System.out.println("  [✓] Removed from queue: " + cancelled);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("  [!] Invalid choice.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    //  [3] ROUTE MANAGEMENT  —  DLL
    // ══════════════════════════════════════════════════════════

    public void manageRoute() {
        boolean back = false;
        while (!back) {
            System.out.println("\n  ╔══════════════════════════════════════════╗");
            System.out.println("  ║       ROUTE MANAGEMENT  (DLL)            ║");
            System.out.println("  ╠══════════════════════════════════════════╣");
            System.out.println("  ║  1. View Route (Forward — Iterative)     ║");
            System.out.println("  ║  2. View Route (Backward — prev ptrs)    ║");
            System.out.println("  ║  3. Add Stop at End                      ║");
            System.out.println("  ║  4. Insert Stop After Location           ║");
            System.out.println("  ║  5. Remove a Stop                        ║");
            System.out.println("  ║  6. Total Route Distance (Recursive)     ║");
            System.out.println("  ║  0. Back                                 ║");
            System.out.println("  ╚══════════════════════════════════════════╝");

            switch (InputHelper.readInt("  Enter choice: ")) {
                case 1:
                    route.displayForward();
                    break;
                case 2:
                    route.displayBackward();
                    break;
                case 3:
                    String loc  = InputHelper.readLine("  Location name                    : ");
                    double dist = InputHelper.readDouble("  Distance from previous stop (km): ");
                    route.addStop(loc, dist);
                    System.out.println("  [✓] Stop \"" + loc + "\" added.");
                    break;
                case 4:
                    route.displayForward();
                    String after  = InputHelper.readLine("  Insert AFTER location (exact)   : ");
                    String newLoc = InputHelper.readLine("  New stop name                   : ");
                    double d      = InputHelper.readDouble("  Distance from above stop (km)   : ");
                    if (route.insertAfter(after, newLoc, d))
                        System.out.println("  [✓] Stop \"" + newLoc + "\" inserted after \"" + after + "\".");
                    else
                        System.out.println("  [!] Location \"" + after + "\" not found.");
                    break;
                case 5:
                    route.displayForward();
                    String rem = InputHelper.readLine("  Stop to remove (exact name): ");
                    if (route.removeStop(rem))
                        System.out.println("  [✓] Stop \"" + rem + "\" removed.");
                    else
                        System.out.println("  [!] Stop not found.");
                    break;
                case 6:
                    System.out.printf("  [✓] Total route distance (recursive): %.2f km%n", route.totalDistance());
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("  [!] Invalid choice.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    //  [4] DELIVERY HISTORY  —  Stack
    // ══════════════════════════════════════════════════════════

    public void viewHistory() {
        history.displayAll();
        if (!history.isEmpty()) {
            System.out.printf("%n  [Stats] Total weight delivered (recursive): %.2f kg%n",
                history.totalWeightDelivered());
        }
    }

    // ══════════════════════════════════════════════════════════
    //  [5] PROCESS NEXT DELIVERY
    // ══════════════════════════════════════════════════════════

    public void processNextDelivery() {
        if (pendingOrders.isEmpty()) {
            System.out.println("  [!] No pending orders to process.");
            return;
        }
        Order o = pendingOrders.peek();
        System.out.println("\n  Next order to deliver:");
        System.out.println("  " + o);
        String ans = InputHelper.readLine("\n  Confirm delivery? (y/n): ");
        if (ans.equalsIgnoreCase("y")) {
            pendingOrders.dequeue();
            o.setStatus("DELIVERED");
            history.push(o);
            System.out.println("\n  [✓] Order #" + o.orderId + " marked DELIVERED and pushed to history stack.");
            System.out.println("  [i] Orders remaining in queue: " + pendingOrders.size);
        } else {
            System.out.println("  [–] Delivery skipped.");
        }
    }

    // ══════════════════════════════════════════════════════════
    //  [6] SEARCH  —  Recursive algorithms
    // ══════════════════════════════════════════════════════════

    public void searchMenu() {
        System.out.println("\n  ╔══════════════════════════════════════════╗");
        System.out.println("  ║                 SEARCH                   ║");
        System.out.println("  ╠══════════════════════════════════════════╣");
        System.out.println("  ║  1. Customer by ID    (SLL recursive)    ║");
        System.out.println("  ║  2. Customer by Name  (SLL recursive)    ║");
        System.out.println("  ║  3. Pending orders by Customer ID        ║");
        System.out.println("  ║  4. Deliveries by Customer Name          ║");
        System.out.println("  ╚══════════════════════════════════════════╝");

        switch (InputHelper.readInt("  Enter choice: ")) {
            case 1:
                int sid = InputHelper.readInt("  Enter Customer ID: ");
                Customer found = customers.searchById(sid);
                if (found != null) System.out.println("  [✓] Found: " + found);
                else               System.out.println("  [!] Customer #" + sid + " not found.");
                break;
            case 2:
                customers.searchByName(InputHelper.readLine("  Enter name (partial): "));
                break;
            case 3:
                int qcid = InputHelper.readInt("  Enter Customer ID: ");
                System.out.println("  [✓] Customer #" + qcid + " has "
                    + pendingOrders.countForCustomer(qcid) + " pending order(s) in queue.");
                break;
            case 4:
                String nm = InputHelper.readLine("  Enter customer name (partial): ");
                System.out.println("  [✓] " + history.countByCustomer(nm)
                    + " delivered order(s) match \"" + nm + "\".");
                break;
            default:
                System.out.println("  [!] Invalid choice.");
        }
    }

    // ══════════════════════════════════════════════════════════
    //  [7] SYSTEM STATISTICS
    // ══════════════════════════════════════════════════════════

    public void viewStatistics() {
        System.out.println("\n  ╔═══════════════════════════════════════════════════════════╗");
        System.out.println("  ║                  SYSTEM STATISTICS                       ║");
        System.out.println("  ╠═══════════════════════════════════════════════════════════╣");
        System.out.printf ("  ║  Registered Customers  (SLL)    : %-5d                  ║%n", customers.size);
        System.out.printf ("  ║  Pending Orders in Queue        : %-5d                  ║%n", pendingOrders.size);
        System.out.printf ("  ║  Delivered Orders (Stack)       : %-5d                  ║%n", history.size);
        System.out.printf ("  ║  Route Stops (DLL)              : %-5d                  ║%n", route.size);
        System.out.printf ("  ║  Total Route Distance (rec.)    : %-8.2f km             ║%n", route.totalDistance());
        System.out.printf ("  ║  Pending Weight    (recursive)  : %-8.2f kg             ║%n", pendingOrders.totalPendingWeight());
        System.out.printf ("  ║  Delivered Weight  (recursive)  : %-8.2f kg             ║%n", history.totalWeightDelivered());
        System.out.println("  ╚═══════════════════════════════════════════════════════════╝");
    }
}
