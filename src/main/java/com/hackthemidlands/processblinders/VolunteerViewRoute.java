package com.hackthemidlands.processblinders;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class VolunteerViewRoute implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        return new ModelAndView(null, "volunteer");
    }
}
