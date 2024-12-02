package com.khorcha.controllers;
import com.khorcha.models.RegistrationUser;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    public static  final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);
    @Get
    public Map<String, Object> index() {
        return Collections.singletonMap("message", "Hello World");
    }

    @Post("/register")
    public Map<String, Object> registerUser(@Body RegistrationUser userRegistrationData){
        LOG.info("Received request for user {}",userRegistrationData);

        return Collections.singletonMap("message", "Done");
    }
}

