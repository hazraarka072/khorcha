package com.khorcha.repository;

import com.khorcha.models.Transaction;
import org.joda.time.DateTime;

import java.util.List;

public interface TransactionRepository {
    void addTransaction(Transaction transaction);
    List<Transaction> getAllTransactionsBetweenDates(String id, DateTime startDate, DateTime endDate);
    List<Transaction> getTransactionsBetweenDates(String id, String transactionType, DateTime startDate, DateTime endDate);
}
