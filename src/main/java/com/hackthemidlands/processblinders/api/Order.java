package com.hackthemidlands.processblinders.api;

import lombok.Data;

import java.util.*;

@Data
public class Order {
    private List <String> shopList = new ArrayList<>();
    private double maxPrice;
    private String location;
    private OrderStatus status;
    //time window?
}
