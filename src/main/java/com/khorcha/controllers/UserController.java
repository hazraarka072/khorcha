package com.khorcha.controllers;
import com.khorcha.models.RegistrationUser;
import com.khorcha.services.UsersService;
import com.khorcha.utils.ThreadLocalEmail;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller
@Secured(IS_AUTHENTICATED)
public class UserController {
    public static  final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Inject
    UsersService usersService;

    @Post("/register")
    public HttpResponse<String> registerUser(@Body RegistrationUser registrationUser) {
        try {
            usersService.registerUser(registrationUser);
            return HttpResponse.ok("User registered successfully.");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    @Delete("/{username}")
    public HttpResponse<String> deleteUser(String username) {
        try {
            usersService.deleteUser(username);
            return HttpResponse.ok("User deleted successfully.");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    @Get("/username")
    public HttpResponse<String> getUserName() {
        return HttpResponse.ok(ThreadLocalEmail.getEmail());
    }
}

