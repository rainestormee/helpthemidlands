package com.hackthemidlands.processblinders.api;

import lombok.Data;

@Data
public class User {

    private String firstName;
    private String lastName;

    private boolean isVolunteer;

    private String email;
    private String password;

    public User() {
        this.firstName = "Default";
        this.lastName = "Lastname";

        this.email = "test@test.test";
        this.password = "password123";
    }


}
