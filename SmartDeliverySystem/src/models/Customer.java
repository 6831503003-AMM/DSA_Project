package models;

/**
 * Represents a registered customer in the Smart Delivery System.
 * Stored in: CustomerSLL (Singly Linked List).
 */
public class Customer {

    public int    customerId;
    public String name;
    public String address;
    public String phone;
    public int    totalOrders;

    public Customer(int customerId, String name, String address, String phone) {
        this.customerId  = customerId;
        this.name        = name;
        this.address     = address;
        this.phone       = phone;
        this.totalOrders = 0;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %-3d | %-20s | Phone: %-14s | Address: %-25s | Orders: %d",
            customerId, name, phone, address, totalOrders);
    }
}
