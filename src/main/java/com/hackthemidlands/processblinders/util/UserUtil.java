package com.hackthemidlands.processblinders.util;

import com.hackthemidlands.processblinders.api.User;
import lombok.Getter;
import org.eclipse.jetty.websocket.servlet.UpgradeHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserUtil {

    @Getter
    private static final Map<String, User> loggedInUsers = new HashMap<>();

    @Getter
    private static final List<User> allValidUsers = new ArrayList<>();


    public static boolean addNewUserToDatabase(User user) {
        if (findUserFromDatabase(user.getEmail()) != null) {
            return false;
        }
        allValidUsers.add(user);
        return true;
    }

    public static void updateUser(User user){
        allValidUsers.forEach(i -> {
            if(i.getId() == user.getId()) {
                i.setFirstName(user.getFirstName());
                i.setLastName(user.getLastName());
                i.setEmail(user.getEmail());
                i.setPassword(user.getPassword());
                i.setPostcode(user.getPostcode());
                i.setVolunteer(user.isVolunteer());
            }
        });
    }

    public static User findUserFromDatabase(String email) {
        return allValidUsers.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    public static boolean isLoggedIn(User user) {
        return loggedInUsers.containsValue(user);
    }
}
