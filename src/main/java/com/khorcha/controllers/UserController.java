package com.khorcha.controllers;
import com.khorcha.models.RegistrationAccount;
import com.khorcha.services.AccountService;
import com.khorcha.utils.ThreadLocalContext;
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
    AccountService accountService;

    @Post("/account")
    public HttpResponse<String> registerAccount(@Body RegistrationAccount registrationAccount) {
        try {
            accountService.registerAccount(registrationAccount);
            return HttpResponse.ok("Account registered successfully for " + ThreadLocalContext.getValue("email"));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    @Delete("/account/{accountName}")
    public HttpResponse<String> deleteAccount(String accountName) {
        try {
            accountService.deleteAccount(accountName);
            return HttpResponse.ok("Account deleted successfully.");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

}

