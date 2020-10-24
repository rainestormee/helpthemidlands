package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.Order;
import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.util.CookieUtil;
import com.hackthemidlands.processblinders.util.UserUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewOrdersPage implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        User u = UserUtil.findUserFromDatabase(CookieUtil.getCookie(request));
        if (u == null) return new ErrorPage().handle(request, response); // should be redirected anyway before this point is hit
        HashMap<String, Object> models = new HashMap<>();
        models.put("user", u);
        if (u.isVolunteer()) {
            return volunteerPage(request, response, models);
        } else {
            return userPage(request, response, models);
        }
    }

    public ModelAndView volunteerPage(Request request, Response response, Map<String, Object> models) {
        Order o = Order.builder().shopList(Arrays.asList("first item in first list", "second item in first list")).build();
        Order o2 = Order.builder().shopList(Arrays.asList("first item in second list", "second item in second list")).build();
        models.put("orders", Arrays.asList(o, o2));
        return new ModelAndView(models, "volunteerPage");
    }

    public ModelAndView userPage(Request request, Response response, Map<String, Object> models) {
        return new ModelAndView(models, "userPage");
    }
}
