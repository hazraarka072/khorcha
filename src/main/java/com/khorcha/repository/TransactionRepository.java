package com.khorcha.repository;

import com.khorcha.models.Transaction;

public interface TransactionRepository {
    void addTransaction(Transaction transaction);
}
