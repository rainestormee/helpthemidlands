package com.hackthemidlands.processblinders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
