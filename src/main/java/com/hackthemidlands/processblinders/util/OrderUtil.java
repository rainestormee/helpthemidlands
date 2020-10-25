package com.hackthemidlands.processblinders.util;

import com.hackthemidlands.processblinders.api.Order;
import com.hackthemidlands.processblinders.api.OrderStatus;
import com.hackthemidlands.processblinders.api.User;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderUtil {

    @Getter
    private static final List<Order> allValidOrders = new ArrayList<>();

    public static boolean addNewOrderToDatabase(Order order) {
        if (findOrderFromDatabase(order.getID()) != null) {
            return false;
        }
        allValidOrders.add(order);
        return true;
    }

    public static void commitToOrder(){

    }

    public static Order findOrderFromDatabase(int id) {
        return allValidOrders.stream().filter(o -> o.getID() == id).findFirst().orElse(null);
    }

    public static List<Order> getAllPendingOrders() {
        return getAllOrdersWithStatus(OrderStatus.PENDING);
    }

    public static List<Order> getAllOrdersWithStatus(OrderStatus status) {
        return allValidOrders.stream().filter(o -> status.equals(o.getStatus())).collect(Collectors.toList());
    }

    public static boolean isPending(Order order) {
        return order.getStatus().getNum() == 0;
    }

    public static boolean clearVolunteer(int orderId, User u) {
        if (u == null) return false;
        Order o = findOrderFromDatabase(orderId);
        if (o == null || o.getVolunteer() != u) return false;
        o.setStatus(OrderStatus.PENDING);
        o.setVolunteer(null);
        return true;
    }

    public static List<Order> getAllOrdersForUser(User u) {
        return allValidOrders.stream().filter(o -> o.getUser().equals(u)).collect(Collectors.toList());
    }

    public static boolean setVolunteer(Order order, User u) {
        if (order == null) return false;
        if (u == null) {
            order.setVolunteer(null);
            order.setStatus(OrderStatus.PENDING);
        } else {
            // TODO: Extend logic to not allow people to claim an already claimed order...
            if (!isPending(order)) {
                return false; // can't assign.
            }
            order.setVolunteer(u);
            order.setStatus(OrderStatus.ACCEPTED);
        }
        return true; // successful
    }
}
