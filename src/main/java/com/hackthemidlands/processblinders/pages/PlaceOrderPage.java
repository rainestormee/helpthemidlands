package com.hackthemidlands.processblinders.pages;

import spark.*;

import java.util.HashMap;
import java.util.Set;
import java.util.Arrays;

import com.hackthemidlands.processblinders.util.RequestUtil;
import com.hackthemidlands.processblinders.api.Order;
import static com.hackthemidlands.processblinders.util.OrderUtil.*;


public class PlaceOrderPage implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        return new ModelAndView(new HashMap<>(), "placeOrder");
    }


    public Route post = (Request request, Response response) -> {
        Set<String> params = request.queryParams();
        System.out.print(params);
        if (!RequestUtil.checkIfAllQueryParamsArePresentAndNotNull(request, "<items>", "priority", "maxPrice")) {
            // it means we do not have all of the complete form data, so we can send them back to the login page
            System.out.println("Not all");
            response.redirect("/support");
            return "";
        }

        Order o = Order.builder()
                .shopList(Arrays.asList(request.queryParams("items")))
                .maxPrice(Integer.parseInt(request.queryParams("maxPrice")))
                .priority(request.queryParams("password"))
                .build();
        addNewOrderToDatabase(o);
        response.redirect("/orders/view");
        return "";
    };
}
