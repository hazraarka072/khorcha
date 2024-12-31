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

@Controller("/users/{email}/accounts")
@Secured(IS_AUTHENTICATED)
public class AccountController {
    public static  final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Inject
    AccountService accountService;

    @Post
    public HttpResponse<String> registerAccount(@PathVariable String email, @Body RegistrationAccount registrationAccount) {
        try {
            accountService.registerAccount(email, registrationAccount);
            return HttpResponse.ok("Account registered successfully for " + ThreadLocalContext.getValue("email"));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    @Delete("/{accountName}")
    public HttpResponse<String> deleteAccount(@PathVariable String email, String accountName) {
        try {
            accountService.deleteAccount(email,accountName);
            return HttpResponse.ok("Account deleted successfully.");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    @Get("/balance")
    public HttpResponse<BalanceResponse> getBalance(@PathVariable String email) {
        BigDecimal accBal = accountService.getAllAccountBalance(email);
        return HttpResponse.ok(new BalanceResponse(accBal));
    }

    @Get("/details")
    public HttpResponse<List<Account>> getDetails(@PathVariable String email, @Nullable @QueryValue String accountName) {
        if (accountName == null) {
            return HttpResponse.ok(accountService.getAllAccounts(email));
        } else {
            Optional<Account> account = accountService.getAccount(email, accountName);
            if(account.isPresent()) {
                return HttpResponse.ok(List.of(account.get()));
            }
            else {
                return HttpResponse.notFound(List.of());
            }
        }
    }

    @Put("{accountName}")
    public HttpResponse<String> updateAccount(@PathVariable String email, @Body RegistrationAccount registrationAccount) {
        try {
            accountService.updateAccount(email,registrationAccount);
            return HttpResponse.ok("Account updated successfully.");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }
}

