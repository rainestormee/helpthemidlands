package com.hackthemidlands.processblinders;

import spark.*;
import java.util.Map;
import java.util.HashMap;

public class TestViewRoute implements TemplateViewRoute {

    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", "testing 123");
        model.put("hotel", "trivago");

        return new ModelAndView(model, "timer");
    }
}
