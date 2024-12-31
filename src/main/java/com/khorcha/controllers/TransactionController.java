package com.khorcha.controllers;

import com.khorcha.dto.TransactionRequest;
import com.khorcha.services.TransactionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/users/{email}/accounts/{account}/transactions")
@Secured(IS_AUTHENTICATED)
public class TransactionController {
    public static  final Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    @Inject
    TransactionService transactionService;

    @Put
    public HttpResponse<String> addTransaction(@PathVariable String email, @PathVariable String account, @Body TransactionRequest transactionRequest){
        try {
            int id = transactionService.addTransaction(email, account, transactionRequest);
            return HttpResponse.ok(String.format("Transaction %d added successfully.", id));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }
}

