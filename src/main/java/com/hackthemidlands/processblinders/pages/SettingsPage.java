package com.hackthemidlands.processblinders.pages;

import com.hackthemidlands.processblinders.api.User;
import com.hackthemidlands.processblinders.util.RequestUtil;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.hackthemidlands.processblinders.util.CookieUtil.getCookie;
import static com.hackthemidlands.processblinders.util.CookieUtil.setCookie;
import static com.hackthemidlands.processblinders.util.UserUtil.findUserFromDatabase;
import static com.hackthemidlands.processblinders.util.UserUtil.updateUser;

public class SettingsPage implements TemplateViewRoute {

    public Route post = (Request request, Response response) -> {
        // DO CODE FOR UPDATING USER HERE
        if (!RequestUtil.checkIfAllQueryParamsArePresentAndNotNull(request, "fname", "lname", "postcode", "email")) {
            // it means we do not have all of the complete form data, so we can send them back to the login page
            response.redirect("/settings");
            return "";
        }

        User u = findUserFromDatabase(getCookie(request));
        if (u == null) {
            response.redirect("/login");
            return "";
        }

        String newName = request.queryParams("fname");
        String newSurname = request.queryParams("lname");
        String newEmail = request.queryParams("email");
        String newPostcode = request.queryParams("postcode");

        User u2 = User.builder().firstName(newName).lastName(newSurname).email(newEmail).postcode(newPostcode).isVolunteer(u.isVolunteer()).id(u.getId()).password(u.getPassword()).build();
        updateUser(u2);
        setCookie(response, u2);
        response.redirect("/orders/view");
        return "";
    };

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> models = new HashMap<>();
        User u = findUserFromDatabase(getCookie(request));
        if (u == null) {
            response.redirect("/login");
            return new ModelAndView(new HashMap<>(), null);
        }
        models.put("user", u);

        return new ModelAndView(models, "settings");
    }
}
