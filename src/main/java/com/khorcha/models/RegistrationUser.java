package com.khorcha.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Introspected
@Serdeable
@Data
public class RegistrationUser {
    private String username;
    private String password;
    private String email;
}
