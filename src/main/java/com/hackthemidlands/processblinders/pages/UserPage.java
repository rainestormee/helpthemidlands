package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.util.CookieUtil;
import com.hackthemidlands.processblinders.util.UserUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class UserPage implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> models = new HashMap<>();
        User u = UserUtil.findUserFromDatabase(CookieUtil.getCookie(request));
        if (u == null) return null;
        models.put("user", u);
        return new ModelAndView(models, "userPage");
    }
}
