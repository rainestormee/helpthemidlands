package com.hackthemidlands.processblinders;

import com.hackthemidlands.processblinders.api.Order;
import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.pages.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hackthemidlands.processblinders.util.FilterUtil.clientOnly;
import static com.hackthemidlands.processblinders.util.FilterUtil.volunteerOnly;
import static com.hackthemidlands.processblinders.util.UserUtil.getAllValidUsers;
import static spark.Spark.*;

public final class Main {

    @Getter
    public static final List<Order> allValidOrders = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Spark.exception(Exception.class, (exception, request, response) -> exception.printStackTrace()); // allow spark to internally handle exceptions
        staticFileLocation("/public");
        port(8080);

        getAllValidUsers().addAll(IntStream.range(0, 3).mapToObj(User::dummyVolunteer).collect(Collectors.toList())); // here i add the dummy volunteers 0, 1, 2
        getAllValidUsers().addAll(IntStream.range(0, 3).mapToObj(User::dummyUser).collect(Collectors.toList())); // here i add the dummy users 0, 1, 2

        get("/error", (re, rs) -> "error");

        get("/support", new SupportPage(), new ThymeleafTemplateEngine());

        before("/volunteerPage", volunteerOnly);
        get("/volunteerPage", new VolunteerPage(), new ThymeleafTemplateEngine());

        before("userPage", clientOnly);
        get("/userPage", new VolunteerPage(), new ThymeleafTemplateEngine());
        get("/placeOrder", new PlaceOrderPage(), new ThymeleafTemplateEngine());

        path("/login", () -> {
            LoginPage loginPage = new LoginPage();
            get("", loginPage, new ThymeleafTemplateEngine());
            post("", loginPage.post);
        });

        path("/register", () -> {
            RegisterPage registerPage = new RegisterPage();
            post("", registerPage.post);
            get("", registerPage, new ThymeleafTemplateEngine());
        });

        path("/dev", new DevPage().getRoutes);
    }
}