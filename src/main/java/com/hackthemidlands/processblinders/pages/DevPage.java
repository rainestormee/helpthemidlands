package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.User;
import spark.RouteGroup;

import static com.hackthemidlands.processblinders.util.CookieUtil.getCookie;
import static com.hackthemidlands.processblinders.util.CookieUtil.setCookie;
import static com.hackthemidlands.processblinders.util.FilterUtil.requiresLogin;
import static com.hackthemidlands.processblinders.util.UserUtil.getLoggedInUsers;
import static com.hackthemidlands.processblinders.util.UserUtil.getUserFromEmail;
import static spark.Spark.before;
import static spark.Spark.get;

public class DevPage {

    public RouteGroup getRoutes = () -> {
        before("/protected", requiresLogin);
        get("/protected", (req, res) -> {
            User u = getUserFromEmail(getCookie(req));
            if (u == null) return "";
            return "Your name is " + u.getFirstName() + " " + u.getLastName();
        });
        get("/login", (req, res) -> {
            User u = User.dummyVolunteer(0);
            getLoggedInUsers().put(u.getEmail(), u);
            setCookie(res, u);
            return "You have been logged as: " + u.getEmail();
        });
        get("/logout", (req, res) -> {
            setCookie(res, User.builder().build());
            return "You have been logged out.";
        });
    };
}
