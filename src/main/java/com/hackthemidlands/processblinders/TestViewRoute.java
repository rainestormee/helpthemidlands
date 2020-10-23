package com.hackthemidlands.processblinders;

import spark.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

public class TestViewRoute implements TemplateViewRoute {

    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", "testing 123");
        LocalDateTime dateTime = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        dateTime = dateTime.plus(1, ChronoUnit.HOURS);
        model.put("startTime", dateTime.toString());

        return new ModelAndView(model, "timer");
    }
}
