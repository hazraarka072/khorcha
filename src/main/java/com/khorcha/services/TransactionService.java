package com.khorcha.services;

import com.khorcha.dto.TransactionRequest;

public interface TransactionService {

    String addTransaction(String email, String account, TransactionRequest transactionRequest);
}
