package com.hackthemidlands.processblinders;

import com.hackthemidlands.processblinders.api.Order;
import com.hackthemidlands.processblinders.api.User;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hackthemidlands.processblinders.util.CookieUtil.getCookie;
import static com.hackthemidlands.processblinders.util.CookieUtil.setCookie;
import static com.hackthemidlands.processblinders.util.FilterUtil.requiresLogin;
import static com.hackthemidlands.processblinders.util.UserUtil.*;
import static spark.Spark.*;

public final class Main {

    @Getter
    public static final List<Order> allValidOrders = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Spark.exception(Exception.class, (exception, request, response) -> exception.printStackTrace()); // allow spark to internally handle exceptions
        staticFileLocation("/public");
        port(8080);

        getAllValidUsers().addAll(IntStream.range(0, 3).mapToObj(User::dummyVolunteer).collect(Collectors.toList()));

        get("/support", new SupportViewRoute(), new ThymeleafTemplateEngine());

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
        });
    }
}