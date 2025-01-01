package com.khorcha.services.impl;

import com.khorcha.dto.TransactionRequest;
import com.khorcha.models.Account;
import com.khorcha.models.Transaction;
import com.khorcha.repository.TransactionRepository;
import com.khorcha.services.AccountService;
import com.khorcha.services.TransactionService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public String addTransaction(String email, String accountName, TransactionRequest transactionRequest) {
        Optional<Account> account = this.accountService.getAccount(email, accountName);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("The account does not exist.");
        }
        Transaction transaction = getTransactionFromTransactionRequest(email, accountName, transactionRequest);
        transactionRepository.addTransaction(transaction);
        updateBalanceForTransaction(transaction, account.get());
        log.info("Added transaction for user {}, account {} on {}", email, accountName, transaction.getTime());
        return transaction.getId();
    }

    @Override
    public List<Transaction> getAllTransactions(String email, String account, DateTime startDate, DateTime endDate) {
        String id = getId(email, account);
        log.info("Fetching all transactions for id {} between {} and {}",id,startDate,endDate);
        return transactionRepository.getAllTransactionsBetweenDates(id,startDate, endDate);
    }

    @Override
    public List<Transaction> getTransactions(String email, String account, String transactionType, DateTime startDate, DateTime endDate) {
        String id = getId(email, account);
        log.info("Fetching {} transactions for id {} between {} and {}",transactionType,id,startDate,endDate);
        return transactionRepository.getTransactionsBetweenDates(id, transactionType, startDate, endDate);
    }

    private Transaction getTransactionFromTransactionRequest(String email, String account, TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setTime(transactionRequest.getTime());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setEmail(email);
        transaction.setAccount(account);
        String id = getId(email,account);
        transaction.setId(id);
        return transaction;
    }

    private String getId(String email, String account){
        return email + "-" + account;
    }

    private void updateBalanceForTransaction(Transaction transaction, Account account) {
        BigDecimal transactionAmount = transaction.getAmount();
        BigDecimal currentBalance = account.getCurrentBalance();
        BigDecimal updatedBalance = switch (transaction.getTransactionType()) {
            case CREDIT -> currentBalance.add(transactionAmount);
            case DEBIT -> currentBalance.subtract(transactionAmount);
            default -> throw new IllegalArgumentException("Invalid Transaction Type");
        };
        account.setCurrentBalance(updatedBalance);
        this.accountService.updateAccount(account);
    }


}
