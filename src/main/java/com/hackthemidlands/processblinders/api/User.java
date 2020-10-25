package com.hackthemidlands.processblinders.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    public static int maxId = -1;
    private final int id;
    private String firstName;
    private String lastName;

    private String email;
    private String password;
    private String postcode;

    private boolean isVolunteer;

    public static User dummyVolunteer() {
        return dummyVolunteer(0);
    }

    public static User dummyUser() {
        return dummyUser(0);
    }


    public static User dummyVolunteer(int i) {
        maxId++;
        return new UserBuilder()
                .email("volunteer" + i + "@v.co")
                .isVolunteer(true)
                .firstName("Volunteer")
                .lastName(i + "")
                .password("password" + i)
<<<<<<< HEAD
                .id(maxId)
=======
                .postcode("PO59 C0D" + i)
>>>>>>> cabed93b6a294ec13e96d4905e56bc8000a96447
                .build();
    }

    public static User dummyUser(int i) {
        maxId++;
        return new UserBuilder()
                .email("user" + i + "@u.co")
                .isVolunteer(false)
                .firstName("User")
                .lastName(i + "")
                .postcode("P057 C0D" + i)
                .password("pass" + i)
                .id(maxId)
                .build();
    }
}
