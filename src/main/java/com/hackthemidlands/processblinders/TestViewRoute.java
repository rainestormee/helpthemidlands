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

public class TestViewRoute implements TemplateViewRoute {

    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "testing 123");
        LocalDateTime dateTime = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        dateTime = dateTime.plus(1, ChronoUnit.HOURS);
        model.put("startTime", dateTime.toString());

        return new ModelAndView(model, "timer");
    }
}
