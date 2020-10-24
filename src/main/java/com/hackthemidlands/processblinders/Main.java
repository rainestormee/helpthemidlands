package com.hackthemidlands.processblinders;

import com.hackthemidlands.processblinders.api.Order;
import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.pages.DevPage;
import com.hackthemidlands.processblinders.pages.LoginPage;
import com.hackthemidlands.processblinders.pages.RegisterPage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        getAllValidUsers().addAll(IntStream.range(0, 3).mapToObj(User::dummyVolunteer).collect(Collectors.toList()));

        get("/support", new SupportViewRoute(), new ThymeleafTemplateEngine());

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