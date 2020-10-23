package com.hackthemidlands.processblinders;

import com.hackthemidlands.processblinders.api.User;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public final class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Map<String, User> loggedInUsers = new HashMap<>();

    @Getter
    private static final List<User> allValidUsers = new ArrayList<>();

    public static boolean addNewUserToDatabase(User user) {
        if (allValidUsers.stream().map(User::getEmail).anyMatch(u -> u.equalsIgnoreCase(user.getEmail()))) {
            return false; // invalid! can't have a user with the same email as an existing user
        }
        allValidUsers.add(user);
        return true;
    }

    public static void main(String[] args) {
        Spark.exception(Exception.class, (exception, request, response) -> exception.printStackTrace()); // allow spark to internally handle exceptions
        staticFileLocation("/public");

        get("/test/test", new TestViewRoute(), new ThymeleafTemplateEngine());
        get("/hello", (req, res) -> "Hello World");
        get("/test", (req, res) -> "timer");
        get("/goodbye/:name", (req, res) -> "See ya, " + req.params(":name"));
        get("/thyme", new TestViewRoute(), new ThymeleafTemplateEngine());
        get("/support", new SupportViewRoute(), new ThymeleafTemplateEngine());
        get("/login/volunteer", new VolunteerViewRoute(), new ThymeleafTemplateEngine());
        get("/dev/login", (req, res) -> "You are :");
        path("/dev", () -> {
            before("/protected", setSession);
            get("/protected", (req, res) -> "This is Protected");
            get("/fuck-off", (req, res) -> "You have been fucked off");
            get("/login", (req, res) -> {
                loggedInUsers.put(new User().getEmail(), new User());
                setSession(req, new User());
                return "You have been logged in.";
            });
        });
    }

    public static Filter setSession = (Request request, Response response) -> {
        if (!hasSession(request)) {

            String sessionId = getCookie(request);
            System.out.println(sessionId);
            User user = getUserFromSession(sessionId);

            if (user != null) {
                setSession(request, user);
            } else {
                response.redirect("/dev/fuck-off");
            }
        }
    };

    public static boolean hasSession(Request request) {
        if (request.session().attribute("user") == null)
            return false;
        return true;
    }

    public static String getCookie(Request request) {
        return request.cookie("auth-code");
    }

    public static void setSession(Request request, User user) {
        request.session().attribute("user", user.getEmail());
    }


    public static void setSession(Response response, String cookie) {
        response.cookie("auth-code", cookie);
    }

    public static User getUserFromSession(String sessionId) {
        return loggedInUsers.getOrDefault(sessionId, null);
    }

}