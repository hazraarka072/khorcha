package com.khorcha.controllers;

import com.khorcha.dto.TransactionRequest;
import com.khorcha.models.Transaction;
import com.khorcha.services.TransactionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/users/{email}/accounts/{account}/transactions")
@Secured(IS_AUTHENTICATED)
public class TransactionController {
    public static  final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
    private final DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Inject
    TransactionService transactionService;

    @Put
    public HttpResponse<String> addTransaction(@PathVariable String email,
                                               @PathVariable String account,
                                               @Body TransactionRequest transactionRequest){
        try {
            String id = transactionService.addTransaction(email, account, transactionRequest);
            return HttpResponse.ok(String.format("Transaction %s added successfully.", id));
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    @Get
    public HttpResponse<List<Transaction>> getAllTransactions(@PathVariable String email,
                                                              @PathVariable String account,
                                                              @QueryValue String startDate,
                                                              @QueryValue String endDate) {
        DateTime startDt = DateTime.parse(startDate, dtFormatter);
        DateTime endDt = DateTime.parse(endDate, dtFormatter);

        List<Transaction> transactions = transactionService.getAllTransactions(email,account,startDt,endDt);
        return HttpResponse.ok(transactions);
    }

    @Get("/{transactionType}")
    public HttpResponse<List<Transaction>> getTransactions(@PathVariable String email,
                                                              @PathVariable String account,
                                                              @PathVariable String transactionType,
                                                              @QueryValue String startDate,
                                                              @QueryValue String endDate) {
        DateTime startDt = DateTime.parse(startDate, dtFormatter);
        DateTime endDt = DateTime.parse(endDate, dtFormatter);

        List<Transaction> transactions = transactionService.getTransactions(email,
                account,
                transactionType.toUpperCase(),
                startDt,
                endDt);
        return HttpResponse.ok(transactions);
    }
}

