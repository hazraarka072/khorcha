package com.khorcha.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable.Deserializable
public class RegistrationUser {

    private String username;
    private String password;

    // No-arg constructor (optional for Micronaut)
    public RegistrationUser() {}

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        System.out.println("password:: "+password);
        this.password = password;
    }
}
