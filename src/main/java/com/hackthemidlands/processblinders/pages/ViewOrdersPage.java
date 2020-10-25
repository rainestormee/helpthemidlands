package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.util.CookieUtil;
import com.hackthemidlands.processblinders.util.OrderUtil;
import com.hackthemidlands.processblinders.util.UserUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class ViewOrdersPage implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        User u = UserUtil.findUserFromDatabase(CookieUtil.getCookie(request));
        if (u == null)
            return new ErrorPage().handle(request, response); // should be redirected anyway before this point is hit
        HashMap<String, Object> models = new HashMap<>();
        models.put("user", u);
        if (u.isVolunteer()) {
            return volunteerPage(request, response, models);
        } else {
            return userPage(request, response, models);
        }
    }

    public ModelAndView volunteerPage(Request request, Response response, Map<String, Object> models) {
        models.put("orders", OrderUtil.getAllPendingOrders());
        return new ModelAndView(models, "orders-view-volunteer");
    }

    public ModelAndView userPage(Request request, Response response, Map<String, Object> models) {
        return new ModelAndView(models, "orders-view-user");
    }
}
