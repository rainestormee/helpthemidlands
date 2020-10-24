package com.hackthemidlands.processblinders.util;

import com.hackthemidlands.processblinders.api.User;
import spark.Filter;
import spark.Request;
import spark.Response;

import static com.hackthemidlands.processblinders.util.CookieUtil.*;
import static com.hackthemidlands.processblinders.util.UserUtil.*;

public class FilterUtil {

    public static Filter requiresLogin = (Request request, Response response) -> {
        if (hasCookie(request)) {
            String cookie = getCookie(request);
            System.out.println(cookie);
            User user = getUserFromEmail(cookie);
            if (user != null) {
                getLoggedInUsers().put(user.getEmail(), user);
            } else {
                response.redirect("/login");
            }
        } else {
            response.redirect("/login");
        }
    };
}
