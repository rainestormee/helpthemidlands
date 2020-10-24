package com.hackthemidlands.processblinders.pages;

<<<<<<< HEAD
=======
import com.hackthemidlands.processblinders.api.Order;
import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.util.CookieUtil;
import com.hackthemidlands.processblinders.util.UserUtil;
>>>>>>> 3a728c3c4f00c022100bcf45e8dcebc1776fff62
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

<<<<<<< HEAD
import java.util.HashMap;
=======
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
>>>>>>> 3a728c3c4f00c022100bcf45e8dcebc1776fff62

public class ViewOrdersPage implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
<<<<<<< HEAD
        return new ModelAndView(new HashMap<>(), "viewOrders");
=======
        User u = UserUtil.findUserFromDatabase(CookieUtil.getCookie(request));
        if (u == null) return null; // should be redirected anyway before this point is hit
        if (u.isVolunteer()) {
            return volunteerPage(request, response);
        } else {
            return userPage(request, response);
        }
    }

    public ModelAndView volunteerPage(Request request, Response response) {
        Map<String, Object> models = new HashMap<>();
        Order o = Order.builder().shopList(Arrays.asList("first item in first list", "second item in first list")).build();
        Order o2 = Order.builder().shopList(Arrays.asList("first item in second list", "second item in second list")).build();
        models.put("orders", Arrays.asList(o, o2));
        return new ModelAndView(models, "orderVolunteerPage");
    }

    public ModelAndView userPage(Request request, Response response) {
        return null;
>>>>>>> 3a728c3c4f00c022100bcf45e8dcebc1776fff62
    }
}
