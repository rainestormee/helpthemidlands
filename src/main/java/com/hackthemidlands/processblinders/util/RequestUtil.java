package com.hackthemidlands.processblinders.util;

import spark.Request;

import java.util.Arrays;
import java.util.Set;

public class RequestUtil {

    public static boolean checkIfAllQueryParamsArePresentAndNotNull(Request request, String... toCheck) {
        if (!checkIfAllQueryParamsArePresent(request.queryParams(), toCheck)) return false;
        for (String s : toCheck) if (request.queryParams(s) == null || request.queryParams(s).isEmpty()) return false;
        return true;
    }

    public static boolean checkIfAllQueryParamsArePresent(Set<String> params, String... toCheck) {
        return params.containsAll(Arrays.asList(toCheck));
    }
}
