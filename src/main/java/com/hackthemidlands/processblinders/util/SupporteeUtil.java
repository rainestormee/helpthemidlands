package com.hackthemidlands.processblinders.util;

import com.hackthemidlands.processblinders.api.Supportee;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupporteeUtil {

    @Getter
    private static final Map<String, Supportee> loggedInSupportees = new HashMap<>();

    @Getter
    private static final List<Supportee> allValidSupportees = new ArrayList<>();


    public static boolean addNewSupporteeToDatabase(Supportee supportee) {
        if (findSupporteeFromDatabase(supportee.getEmail()) != null) {
            return false;
        }
        allValidSupportees.add(supportee);
        return true;
    }

    public static Supportee findSupporteeFromDatabase(String email) {
        return allValidSupportees.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    public static boolean isLoggedIn(Supportee supportee) {
        return loggedInSupportees.containsValue(supportee);
    }

    public static Supportee getSupporteeFromEmail(String email) {
        return findSupporteeFromDatabase(email);
    }
}
