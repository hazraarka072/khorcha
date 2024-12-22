package com.khorcha.controllers;
import com.khorcha.dto.BalanceResponse;
import com.khorcha.models.Account;
import com.khorcha.dto.RegistrationAccount;
import com.khorcha.services.AccountService;
import com.khorcha.utils.ThreadLocalContext;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/account")
@Secured(IS_AUTHENTICATED)
public class AccountController {
    public static  final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Inject
    AccountService accountService;

    @Post
    public HttpResponse<String> registerAccount(@Body RegistrationAccount registrationAccount) {
        try {
            accountService.registerAccount(registrationAccount);
            return HttpResponse.ok("Account registered successfully for " + ThreadLocalContext.getValue("email"));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    @Delete("/{accountName}")
    public HttpResponse<String> deleteAccount(String accountName) {
        try {
            accountService.deleteAccount(accountName);
            return HttpResponse.ok("Account deleted successfully.");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    @Get("/balance")
    public HttpResponse<BalanceResponse> getBalance() {
        BigDecimal accBal = accountService.getAllAccountBalance();
        return HttpResponse.ok(new BalanceResponse(accBal));
    }

    @Get("/details")
    public HttpResponse<List<Account>> getDetails(@Nullable @QueryValue String accountName) {
        if (accountName == null) {
            return HttpResponse.ok(accountService.getAllAccounts());
        } else {
            Optional<Account> account = accountService.getAccount(accountName);
            if(account.isPresent()) {
                return HttpResponse.ok(List.of(account.get()));
            }
            else {
                return HttpResponse.notFound(List.of());
            }
        }
    }
}

