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

    @Override
    public ModelAndView handle(Request request, Response response) {
        return new ModelAndView(new HashMap<>(), "support");
    }
}
