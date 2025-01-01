package com.khorcha.services.impl;

import com.khorcha.dto.TransactionRequest;
import com.khorcha.models.Account;
import com.khorcha.models.Transaction;
import com.khorcha.repository.TransactionRepository;
import com.khorcha.services.AccountService;
import com.khorcha.services.TransactionService;
import java.math.BigDecimal;
import java.util.Optional;

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

        return transaction.getId();
    }

    private Transaction getTransactionFromTransactionRequest(String email, String account, TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setTime(transactionRequest.getTime());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setEmail(email);
        transaction.setAccount(account);
        String id = email + "-" + account;
        transaction.setId(id);
        return transaction;
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
