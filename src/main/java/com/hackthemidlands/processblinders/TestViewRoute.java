package com.hackthemidlands.processblinders;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;

public class TestViewRoute implements TemplateViewRoute {

    public ModelAndView handle(Request request, Response response) {
        return new ModelAndView(new HashMap<>(), "main");
    }
}
