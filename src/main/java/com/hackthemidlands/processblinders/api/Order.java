package com.hackthemidlands.processblinders.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {
    private List<String> shopList;
    private double maxPrice;
    private String location;
    private OrderStatus status;
    private User user;
    //time window?
}
