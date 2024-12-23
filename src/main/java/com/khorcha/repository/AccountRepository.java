package com.khorcha.repository;

import com.khorcha.models.Account;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public interface AccountRepository {

    void saveAccount(Account account);

    void updateAccount(Account account);

    List<Account> getAccounts(String email);

    void deleteAccount(String email, String accountName);

    Optional<Account> getAccount(String email, String accountName);
}
