package models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a single delivery order in the Smart Delivery System.
 * Used by: OrderQueue (pending) and DeliveryStack (history).
 */
public class Order {

    public int    orderId;
    public int    customerId;
    public String customerName;
    public String deliveryAddress;
    public String item;
    public double weight;        // kg
    public String status;        // PENDING | DELIVERED
    public String createdAt;

    public Order(int orderId, int customerId, String customerName,
                 String deliveryAddress, String item, double weight) {
        this.orderId         = orderId;
        this.customerId      = customerId;
        this.customerName    = customerName;
        this.deliveryAddress = deliveryAddress;
        this.item            = item;
        this.weight          = weight;
        this.status          = "PENDING";
        this.createdAt       = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
            "Order #%-4d | Customer: %-18s | Item: %-15s | %.1f kg | %-25s | [%s]",
            orderId, customerName, item, weight, deliveryAddress, status);
    }
}
