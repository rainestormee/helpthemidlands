package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.User;
import spark.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import static com.hackthemidlands.processblinders.util.CookieUtil.setCookie;
import static com.hackthemidlands.processblinders.util.UserUtil.addNewUserToDatabase;

public class RegisterPage implements TemplateViewRoute {

    // HTTP GET

    public Route post = (Request request, Response response) -> {
        Set<String> params = request.queryParams();
        if (!params.containsAll(Arrays.asList("fname", "lname", "validate", "email", "password"))) {
            // it means we do not have all of the complete form data, so we can send them back to the login page
            response.redirect("/register");
            return "";
        }
        boolean volunteer = request.queryParams("validate").equalsIgnoreCase("I want to volunteer");

        User u = User.builder()
                .firstName(request.queryParams("fname"))
                .lastName(request.queryParams("lname"))
                .isVolunteer(volunteer)
                .email(request.queryParams("email"))
                .password(request.queryParams("password"))
                .build();
        addNewUserToDatabase(u);
        setCookie(response, u);// logs in the user
        if (volunteer) {
            response.redirect("/volunteerPage");
            return "Welcome new Volunteer!";
        }
        else{
            response.redirect("/userPage");
            return "Welcome new client!";
        }
    };

    // HTTP POST

    @Override
    public ModelAndView handle(Request request, Response response) {
        return new ModelAndView(new HashMap<>(), "register");
    }
}
