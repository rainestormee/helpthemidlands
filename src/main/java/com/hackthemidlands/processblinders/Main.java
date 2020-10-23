package com.hackthemidlands.processblinders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Spark.exception(Exception.class, (exception, request, response) -> exception.printStackTrace()); // allow spark to internally handle exceptions
        staticFileLocation("/public");
        get("/hello", (req, res) -> "Hello World");
        get("/test", (req, res) -> "timer");
        get("/goodbye/:name", (req,res) -> {
            return "See ya, " + req.params(":name");
        });
        get("/there", Main::overHere);
        get("/thyme", new TestViewRoute(), new ThymeleafTemplateEngine());
        get("/support", new SupportViewRoute(), new ThymeleafTemplateEngine());
        get("/cool/:name", Main::reubenIsCool);
        get("/volunteer/login/", new VolunteerViewRoute(), new ThymeleafTemplateEngine());
        // hello world
        // yeah it's a little clunky but it does the job and supports quite a few users, it doesn't really support using Ctrl+Z ect tho
    }

    public static String overHere(Request request, Response response) {
        return "i'm over here!";
    }

    public static String reubenIsCool(Request req, Response res){
        return req.params(":name") + " thinks Reuben is cool";
        /*
        yeah it's a bit weird but other than that, nah it usually fixes itself
        they can set it up yeah - or web editor
        btw if you wanna run this to test it
        run
        $ mvn clean package
        $ java -jar target/*.jar
        from terminal
        */
        //

    }
}