package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.User;
import spark.RouteGroup;

import static com.hackthemidlands.processblinders.util.CookieUtil.*;
import static com.hackthemidlands.processblinders.util.FilterUtil.*;
import static com.hackthemidlands.processblinders.util.UserUtil.*;
import static spark.Spark.before;
import static spark.Spark.get;

public class DevPage {

    public RouteGroup getRoutes = () -> {
        before("/protected", requiresLogin);
        get("/protected", (req, res) -> {
            User u = findUserFromDatabase(getCookie(req));
            if (u == null) return "";
            return "Your name is " + u.getFirstName() + " " + u.getLastName();
        });
        before("/volunteers-only", volunteerOnly);
        get("/volunteers-only", (re, rs) -> {
            User u = findUserFromDatabase(getCookie(re));
            if (u == null || !u.isVolunteer()) return "";
            return "Hello volunteer!";
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
