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

    private int drones = 0;

    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        drones++;
        model.put("drones", drones);
        LocalDateTime dateTime = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        dateTime = dateTime.plus(1, ChronoUnit.HOURS);
        model.put("startTime", dateTime.toString());

        return new ModelAndView(model, "main");
    }
}
