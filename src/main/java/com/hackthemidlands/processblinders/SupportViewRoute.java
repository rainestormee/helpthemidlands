package com.hackthemidlands.processblinders;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class SupportViewRoute implements TemplateViewRoute {

    private int drones = 0;

    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();


        return new ModelAndView(model, "support");
    }
}
