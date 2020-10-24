package com.hackthemidlands.processblinders;

import com.hackthemidlands.processblinders.api.Order;
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

    @Getter
    public static final List<Order> allValidOrders = new ArrayList<>();


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
        } else {
            response.redirect("/dev/fuck-off");
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

        allValidUsers.add(User.dummyVolunteer(0));
        allValidUsers.add(User.dummyVolunteer(1));
        allValidUsers.add(User.dummyVolunteer(3));

        get("/support", new SupportViewRoute(), new ThymeleafTemplateEngine());
        get("/login/volunteer", new VolunteerViewRoute(), new ThymeleafTemplateEngine());

        path("/login", () -> {
            get("", new TestViewRoute(), new ThymeleafTemplateEngine());
            post("", (re, rs) -> {
                Set<String> params = re.queryParams();
                if (!params.containsAll(Arrays.asList("email", "password"))) {
                    // rs.redirect("/login");
                    return "Invalid login credentials";
                }
                User u = findUserFromDatabase(re.queryParams("email"));
                if (u == null || !u.getPassword().equals(re.queryParams("password"))) {
                    return "Invalid login credentials";
                }
                setCookie(rs, u);
                return "Successfully logged in as: " + u.getFirstName() + " " + u.getLastName();
            });
        });

        path("/register", () -> {
            post("", (re, rs) -> {
                Set<String> params = re.queryParams();
                if (!params.containsAll(Arrays.asList("fname", "lname", "validate", "email", "password"))) {
                    // it means we do not have all of the complete form data, so we can send them back to the login page
                    rs.redirect("/register");
                    return "";
                }
                boolean volunteer = re.queryParams("validate").equalsIgnoreCase("Volunteer");
                User u = User.builder()
                        .firstName(re.queryParams("fname"))
                        .lastName(re.queryParams("lname"))
                        .isVolunteer(re.queryParams("validate").equalsIgnoreCase("volunteer"))
                        .email(re.queryParams("email"))
                        .password(re.queryParams("password"))
                        .build();
                addNewUserToDatabase(u);
                setCookie(rs, u);// logs in the user
                if (volunteer) {
                    return "Welcome new Volunteer!";
                }
                return "Welcome new client!";
            });
            get("", new MakeAccountViewRoute(), new ThymeleafTemplateEngine());
        });
        path("/dev", () -> {
            before("/protected", setSession);
            get("/protected", (req, res) -> {
                User u = getUserFromEmail(getCookie(req));
                if (u == null) return "";
                return "Your name is " + u.getFirstName() + " " + u.getLastName();
            });
            get("/fuck-off", (req, res) -> "You have been fucked off");
            get("/login", (req, res) -> {
                User u = User.dummyVolunteer(0);
                loggedInUsers.put(u.getEmail(), u);
                setCookie(res, u);
                return "You have been logged as: " + u.getEmail();
            });
            get("/logout", (req, res) -> {
                setCookie(res, User.builder().build());
                return "You have been logged out.";
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