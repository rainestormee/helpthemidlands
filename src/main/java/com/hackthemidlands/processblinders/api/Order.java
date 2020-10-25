package com.hackthemidlands.processblinders.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

    public static int maxId = -1;

    private String[] shopList;
    private int id;
    private double maxPrice;
    private OrderStatus status;
    private User user;
    private User volunteer;
    private String priority;

    public int getID() {
        return this.id;
    }
    //time window?
    // Time sent??
}
