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

    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView(model, "support");
    }
}
