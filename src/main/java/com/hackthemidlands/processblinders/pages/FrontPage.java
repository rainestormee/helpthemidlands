package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.util.CookieUtil;
import com.hackthemidlands.processblinders.util.UserUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class FrontPage implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> models = new HashMap<>();
        models.put("loggedIn", UserUtil.findUserFromDatabase(CookieUtil.getCookie(request)) != null);
        return new ModelAndView(models, "index");
    }
}
