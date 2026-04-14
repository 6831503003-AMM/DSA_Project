package models;

/**
 * Represents a single stop on the delivery route.
 * Stored in: RouteDLL (Doubly Linked List).
 */
public class RouteStop {

    public String location;
    public double distanceFromPrev;  // km from the previous stop

    public RouteStop(String location, double distanceFromPrev) {
        this.location         = location;
        this.distanceFromPrev = distanceFromPrev;
    }

    @Override
    public String toString() {
        return String.format("%-40s | %.1f km", location, distanceFromPrev);
    }
}
