import data.SampleData;
import datastructures.CustomerSLL;
import datastructures.DeliveryStack;
import datastructures.OrderQueue;
import datastructures.RouteDLL;
import ui.Menu;
import utils.InputHelper;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║            SMART DELIVERY SYSTEM  — Entry Point             ║
 * ╠══════════════════════════════════════════════════════════════╣
 * ║  Course  : Data Structures & Algorithms  (1501118)          ║
 * ║  Project : Group Project (10%)                              ║
 * ╠══════════════════════════════════════════════════════════════╣
 * ║  Data Structures                                            ║
 * ║    • Singly Linked List (SLL)  — Customer Registry         ║
 * ║    • Queue  (SLL-based)        — Pending Orders  (FIFO)    ║
 * ║    • Stack  (SLL-based)        — Delivery History (LIFO)   ║
 * ║    • Doubly Linked List (DLL)  — Route Management          ║
 * ╠══════════════════════════════════════════════════════════════╣
 * ║  Algorithms                                                 ║
 * ║    • Iterative : queue/stack/SLL/DLL traversal,  display   ║
 * ║    • Recursive : search, count, distance, weight totals    ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * How to compile and run:
 *   javac -d out -sourcepath src src/Main.java
 *   java  -cp out Main
 */
public class Main {

    public static void main(String[] args) {

        // ── Initialise data structures ─────────────────────────
        CustomerSLL   customers     = new CustomerSLL();
        OrderQueue    pendingOrders = new OrderQueue();
        DeliveryStack history       = new DeliveryStack();
        RouteDLL      route         = new RouteDLL();

        // ── Auto-increment ID counters (int[] used so they can
        //    be passed by reference into Menu and SampleData) ──
        int[] nextOrderId = { 1001 };
        int[] nextCustId  = { 1    };

        // ── Load sample data ───────────────────────────────────
        SampleData.load(customers, pendingOrders, history, route, nextOrderId, nextCustId);

        System.out.println("\n  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║                 SMART DELIVERY SYSTEM                ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝");

        // ── Create menu and run the main loop ──────────────────
        Menu menu    = new Menu(customers, pendingOrders, history, route, nextOrderId, nextCustId);
        boolean running = true;

        while (running) {
            menu.printMainMenu();
            int choice = InputHelper.readInt("  Enter choice: ");
            System.out.println();

            switch (choice) {
                case 1:  menu.manageCustomers();      break;
                case 2:  menu.manageOrders();         break;
                case 3:  menu.manageRoute();          break;
                case 4:  menu.viewHistory();          break;
                case 5:  menu.processNextDelivery();  break;
                case 6:  menu.searchMenu();           break;
                case 7:  menu.viewStatistics();       break;
                case 0:  running = false;             break;
                default: System.out.println("  [!] Invalid choice. Please try again.");
            }
        }

        System.out.println("\n  ══════════════════════════════════════════════════");
        System.out.println("       Thank you for using Smart Delivery System!");
        System.out.println("  ══════════════════════════════════════════════════\n");
        InputHelper.close();
    }
}
