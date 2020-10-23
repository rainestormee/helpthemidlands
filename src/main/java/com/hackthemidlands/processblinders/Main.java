package com.hackthemidlands.processblinders;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello world");

        get("/goodbye/:name", (req,res) -> {
            return "See ya, " + req.params(":name");
        });
    }
}