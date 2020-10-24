package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.User;
import spark.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import static com.hackthemidlands.processblinders.util.CookieUtil.setCookie;
import static com.hackthemidlands.processblinders.util.UserUtil.findUserFromDatabase;

public class LoginPage implements TemplateViewRoute {

    // HTTP POST

    public Route post = (Request request, Response response) -> {
        Set<String> params = request.queryParams();
        if (!params.containsAll(Arrays.asList("email", "password"))) {
            // rs.redirect("/login");
            return "Invalid login credentials";
        }
        User u = findUserFromDatabase(request.queryParams("email"));
        if (u == null || !u.getPassword().equals(request.queryParams("password"))) {
            return "Invalid login credentials";
        }
        setCookie(response, u);
        return "Successfully logged in as: " + u.getFirstName() + " " + u.getLastName();
    };

    // HTTP GET

    @Override
    public ModelAndView handle(Request request, Response response) {
        return new ModelAndView(new HashMap<>(), "main");
    }
}
