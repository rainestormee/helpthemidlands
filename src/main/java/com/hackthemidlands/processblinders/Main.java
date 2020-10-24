package com.hackthemidlands.processblinders;

import com.hackthemidlands.processblinders.api.User;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public final class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Map<String, User> loggedInUsers = new HashMap<>();

    @Getter
    private static final List<User> allValidUsers = new ArrayList<>();

    public static Filter setSession = (Request request, Response response) -> {
        if (hasCookie(request)) {
            String cookie = getCookie(request);
            System.out.println(cookie);
            User user = getUserFromEmail(cookie);
            if (user != null) {
                loggedInUsers.put(user.getEmail(), user);
            } else {
                response.redirect("/dev/fuck-off");
            }
        }
    };

    public static boolean addNewUserToDatabase(User user) {
        if (findUserFromDatabase(user.getEmail()) != null) {
            return false;
        }
        allValidUsers.add(user);
        return true;
    }

    public static User findUserFromDatabase(String email) {
        return allValidUsers.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    public static boolean isLoggedIn(User user) {
        return loggedInUsers.containsValue(user);
    }

    public static void main(String[] args) {
        Spark.exception(Exception.class, (exception, request, response) -> exception.printStackTrace()); // allow spark to internally handle exceptions
        staticFileLocation("/public");
        port(8080);

        allValidUsers.add(new User());
        allValidUsers.add(new User("test", "admin", "admin@admin.com", "pass"));

        get("/support", new SupportViewRoute(), new ThymeleafTemplateEngine());
        get("/login/volunteer", new VolunteerViewRoute(), new ThymeleafTemplateEngine());

        get("/login", new TestViewRoute(), new ThymeleafTemplateEngine());
        path("/register", () -> {
            post("", (re, rs) -> {
                Set<String> params = re.queryParams();
                List<String> expectedParams = Arrays.asList("");
                if (re.queryParams("validate") == null) {
                    rs.redirect("/register");
                }
                if (re.queryParams("validate").equalsIgnoreCase("Volunteer")) {
                    return "volunteer";
                }
                return "not volunteer";
            });
            get("/register", new MakeAccountViewRoute(), new ThymeleafTemplateEngine());
        });
        path("/dev", () -> {
            before("/protected", setSession);
            get("/protected", (req, res) -> {
                User u = getUserFromEmail(getCookie(req));
                return "Your name is " + u.getFirstName() + " " + u.getLastName();
            });
            get("/fuck-off", (req, res) -> "You have been fucked off");
            get("/login", (req, res) -> {
                loggedInUsers.put(new User().getEmail(), new User());
                System.err.println("YOU ARE SUPPOSED TO BE LOGGED IN HERE");
                setCookie(res, new User());
                return "You have been logged in.";
            });
        });
    }

    public static void setCookie(Response response, User user) {
        response.cookie("/", "email", user.getEmail(), -1, false);
    }

    public static boolean hasCookie(Request request) {
        return request.cookie("email") != null;
    }

    public static String getCookie(Request request) {
        return request.cookie("email");
    }

    public static User getUserFromEmail(String email) {
        return findUserFromDatabase(email);
    }

}