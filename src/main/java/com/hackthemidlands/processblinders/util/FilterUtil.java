package com.hackthemidlands.processblinders.util;

import com.hackthemidlands.processblinders.api.User;
import spark.Filter;
import spark.Request;
import spark.Response;

import static com.hackthemidlands.processblinders.util.CookieUtil.getCookie;
import static com.hackthemidlands.processblinders.util.CookieUtil.hasCookie;
import static com.hackthemidlands.processblinders.util.UserUtil.getLoggedInUsers;
import static com.hackthemidlands.processblinders.util.UserUtil.getUserFromEmail;

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
