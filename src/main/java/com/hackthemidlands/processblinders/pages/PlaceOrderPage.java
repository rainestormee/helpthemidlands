package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.util.CookieUtil;
import com.hackthemidlands.processblinders.util.UserUtil;
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
        User u = UserUtil.findUserFromDatabase(CookieUtil.getCookie(request));
        if (u == null) {
            response.redirect("/error");
            return new ModelAndView(new HashMap<>(), null);
        }
        return new ModelAndView(new HashMap<>(), "orders-create");
    }


    public Route post = (Request request, Response response) -> {
        User u = UserUtil.findUserFromDatabase(CookieUtil.getCookie(request));
        if (u == null) {
            response.redirect("/error");
            return "";
        }
        if (!RequestUtil.checkIfAllQueryParamsArePresentAndNotNull(request, "items", "priority", "maxPrice","submit")) {
            // it means we do not have all of the complete form data, so we can send them back to the login page
            response.redirect("/orders/view");
            return "";
        }
        String items = request.queryParams("items");
        String[] itemsList = items.split("[\\r\\n]+");

        Order o = Order.builder()
                .shopList(itemsList)
                .maxPrice(Integer.parseInt(request.queryParams("maxPrice")))
                .priority(request.queryParams("password"))
                .user(u)
                .build();
        addNewOrderToDatabase(o);
        response.redirect("/orders/view");
        return "";
    };
}
