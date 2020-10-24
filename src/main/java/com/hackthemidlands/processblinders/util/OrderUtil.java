package com.hackthemidlands.processblinders.util;

import com.hackthemidlands.processblinders.api.Order;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderUtil {

    @Getter
    private static final Map<String, User> loggedInUsers = new HashMap<>();

    @Getter
    private static final List<User> allValidUsers = new ArrayList<>();


    public static boolean addNewOrderToDatabase(Order order) {
        if (findOrderFromDatabase(order.getID()) != null) {
            return false;
        }
        allValidOrders.add(order);
        return true;
    }

    public static Order findOrderFromDatabase(Int id) {
        return allValidOrder.stream().filter(o -> o.getID().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public static boolean isPending(Order order) {
        return order.getStatus()==0;
    }
}
