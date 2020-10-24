package com.hackthemidlands.processblinders.pages;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;

public class PlaceOrderPage implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        return new ModelAndView(new HashMap<>(), "placeOrder");
    }
}
