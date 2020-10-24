package com.hackthemidlands.processblinders.util;

import com.hackthemidlands.processblinders.api.User;
import spark.Filter;
import spark.Request;
import spark.Response;

import static com.hackthemidlands.processblinders.util.CookieUtil.*;
import static com.hackthemidlands.processblinders.util.UserUtil.*;

public class FilterUtil {

    // WARNING! THIS WILL ONLY REDIRECT THE USER, YOU WILL STILL NEED TO DDO A VALIDITY CHECK
    // AND RETURN ""; IN YOUR PAGE CLASS.

    public static Filter requiresLogin = (Request request, Response response) -> {
        if (hasCookie(request)) {
            String cookie = getCookie(request);
            User user = UserUtil.findUserFromDatabase(cookie);
            if (user != null) {
                getLoggedInUsers().put(user.getEmail(), user);
            } else {
                response.redirect("/login");
            }
        } else {
            response.redirect("/login");
        }
    };

    // WARNING! YOU NEED TO PUT A requiresLogin before you use this filter as it could work unexpectedly!

    public static Filter volunteerOnly = (Request request, Response response) -> {
        if (!hasCookie(request)) {
            response.redirect("/login");
        } else {
            String cookie = getCookie(request);
            User user = UserUtil.findUserFromDatabase(cookie);
            if (user == null) {
                response.redirect("/login");
            } else {
                if (!user.isVolunteer()) {
                    response.redirect("/");
                }
            }
        }
    };

    public static Filter clientOnly = (Request request, Response response) -> {
        if (!hasCookie(request)) {
            response.redirect("/login");
        } else {
            String cookie = getCookie(request);
            User user = UserUtil.findUserFromDatabase(cookie);
            if (user == null) {
                response.redirect("/login");
            } else {
                if (user.isVolunteer()) {
                    response.redirect("/");
                }
            }
        }
    };
}
