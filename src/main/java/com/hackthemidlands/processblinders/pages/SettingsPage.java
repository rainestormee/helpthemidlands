package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.util.CookieUtil;
import com.hackthemidlands.processblinders.util.UserUtil;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class SettingsPage implements TemplateViewRoute {

    public Route post = (Request request, Response Response) -> {
        // DO CODE FOR UPDATING USER HERE

        return "Your account has been updated! :)";
    };

    @Override
    public ModelAndView handle(Request request, Response response) {

        Map<String, Object> models = new HashMap<>();
        User u = UserUtil.findUserFromDatabase(CookieUtil.getCookie(request));
        if (u == null) return null;
        models.put("user", u);
        return new ModelAndView(models, "settings");
    }
}
