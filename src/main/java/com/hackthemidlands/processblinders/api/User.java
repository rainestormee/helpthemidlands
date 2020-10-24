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
    private String postcode;

    private boolean isVolunteer;

    public static User dummyVolunteer() {
        return dummyVolunteer(0);
    }

    public static User dummyUser(){
        return dummyUser(0);
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

    public static User dummyUser(int i){
        return new UserBuilder()
                .email("user" + i + "@test.test")
                .isVolunteer(false)
                .firstName("User")
                .lastName(i + "")
                .password("pass" + i)
                .build();
    }
}
