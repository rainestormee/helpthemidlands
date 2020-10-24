package com.hackthemidlands.processblinders.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {

    private List<String> shopList;
    private int id;
    private double maxPrice;
    private String location;
    private OrderStatus status;
    private User user;

    public int getID(){
        return this.id;
    }
    //time window?
    // Time sent??
}
