package com.hackthemidlands.processblinders;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;

public class VolunteerViewRoute implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) {
        return new ModelAndView(new HashMap<>(), "volunteer");
    }
}
