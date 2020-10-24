package com.hackthemidlands.processblinders.util;

import com.hackthemidlands.processblinders.api.Volunteer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolunteerUtil {

    @Getter
    private static final Map<String, Volunteer> loggedInVolunteers = new HashMap<>();

    @Getter
    private static final List<Volunteer> allValidVolunteers = new ArrayList<>();


    public static boolean addNewVolunteerToDatabase(Volunteer volunteer) {
        if (findVolunteerFromDatabase(volunteer.getEmail()) != null) {
            return false;
        }
        allValidVolunteers.add(volunteer);
        return true;
    }

    public static Volunteer findVolunteerFromDatabase(String email) {
        return allValidVolunteers.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    public static boolean isLoggedIn(Volunteer volunteer) {
        return loggedInVolunteers.containsValue(volunteer);
    }

    public static Volunteer getVolunteerFromEmail(String email) {
        return findVolunteerFromDatabase(email);
    }
}
