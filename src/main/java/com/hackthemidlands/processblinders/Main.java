package com.hackthemidlands.processblinders;

import com.hackthemidlands.processblinders.api.Order;
import com.hackthemidlands.processblinders.api.OrderStatus;
import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.pages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hackthemidlands.processblinders.util.OrderUtil.*;
import static com.hackthemidlands.processblinders.util.UserUtil.*;
import static java.util.function.Predicate.not;
import static spark.Spark.*;

public final class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Spark.exception(Exception.class, (exception, request, response) -> exception.printStackTrace()); // allow spark to internally handle exceptions
        staticFileLocation("/public");
        port(8080);

        Random random = new Random();

        getAllValidUsers().addAll(IntStream.range(0, 3).mapToObj(User::dummyVolunteer).collect(Collectors.toList())); // here i add the dummy volunteers 0, 1, 2
        getAllValidUsers().addAll(IntStream.range(0, 3).mapToObj(User::dummyUser).collect(Collectors.toList())); // here i add the dummy users 0, 1, 2
        getAllValidOrders().addAll(IntStream.range(0, 3)
                .mapToObj(i -> Order.builder().shopList(Arrays.asList(
                        (random.nextInt(10) + i) + " tins of beans", (random.nextInt(i + 1) + 1) + " loaves of bread", (random.nextInt(i + 4) + i) + " pints of milk"))
                        .id(i).location("P057 C0D3")
                        .user(getAllValidUsers().stream().filter(not(User::isVolunteer)).collect(Collectors.toList()).get(i))
                        .maxPrice(69d).status(OrderStatus.PENDING)
                        .build()).collect(Collectors.toList()));

        get("/error", (re, rs) -> new ModelAndView(new HashMap<>(), "error"), new ThymeleafTemplateEngine());

        get("/support", new SupportPage(), new ThymeleafTemplateEngine());
        get("/frontPage", new FrontPage(), new ThymeleafTemplateEngine());

        get("/placeOrder", new PlaceOrderPage(), new ThymeleafTemplateEngine());

        path("/placeOrder", () -> {
            PlaceOrderPage placeOrderPage = new PlaceOrderPage();
            post("", placeOrderPage.post);
            get("", placeOrderPage, new ThymeleafTemplateEngine());
        });


        path("/orders", () -> {
            ViewOrdersPage viewOrdersPage = new ViewOrdersPage();
            get("/view", viewOrdersPage, new ThymeleafTemplateEngine());
        });

        path("/settings", () -> {
            SettingsPage settingsPage = new SettingsPage();
            get("", settingsPage, new ThymeleafTemplateEngine());
            post("", settingsPage.post);
        });

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