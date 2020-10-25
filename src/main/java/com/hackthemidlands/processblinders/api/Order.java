package com.hackthemidlands.processblinders.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {

    private String[] shopList;
    private int id;
    private double maxPrice;
    private String location;
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
