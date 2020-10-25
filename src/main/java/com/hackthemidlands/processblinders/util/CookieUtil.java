package com.hackthemidlands.processblinders.util;

import com.hackthemidlands.processblinders.api.User;
import spark.Request;
import spark.Response;

public class CookieUtil {

    public static void setCookie(Response response, User user) {
        response.cookie("/", "email", user.getEmail(), -1, false);
    }

    public static boolean hasCookie(Request request) {
        return request.cookie("email") != null;
    }

    public static String getCookie(Request request) {
        return request.cookie("email");
    }

}
