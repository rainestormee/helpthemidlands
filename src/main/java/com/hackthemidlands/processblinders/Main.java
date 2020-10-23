package com.hackthemidlands.processblinders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Spark.exception(Exception.class, (exception, request, response) -> exception.printStackTrace()); // allow spark to internally handle exceptions
        staticFileLocation("/public");
        get("/hello", (req, res) -> "Hello World");
        get("/test", (req, res) -> "timer");
    }
}
