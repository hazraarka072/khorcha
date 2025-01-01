package com.khorcha.services;

import com.khorcha.dto.TransactionRequest;
import com.khorcha.models.Transaction;
import org.joda.time.DateTime;

import java.util.List;

public interface TransactionService {

    String addTransaction(String email, String account, TransactionRequest transactionRequest);
    List<Transaction> getAllTransactions(String email, String account, DateTime startDate, DateTime endDate);
    List<Transaction> getTransactions(String email, String account, String transactionType, DateTime startDate, DateTime endDate);
}
