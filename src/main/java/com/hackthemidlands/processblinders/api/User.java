package com.hackthemidlands.processblinders.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private boolean isVolunteer;

    public User(String firstName, String lastName, String email, String password) {
        this(firstName, lastName, email, password, false);
    }

    public User(String firstName, String lastName, String email, String password, boolean isVolunteer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isVolunteer = true;
    }

    public static User dummyVolunteer() {
        return dummyVolunteer(0);
    }

    public static User dummyVolunteer(int i) {
        return new UserBuilder()
                .email("dummy" + i + "@test.test")
                .isVolunteer(true)
                .firstName("Dummy")
                .lastName(i + "")
                .password("password" + i)
                .build();
    }
}
