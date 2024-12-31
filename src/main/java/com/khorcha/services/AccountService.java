package com.khorcha.services;

import com.khorcha.models.Account;
import com.khorcha.dto.RegistrationAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    void registerAccount(String email, RegistrationAccount registrationAccount);

    void updateAccount(String email, RegistrationAccount registrationAccount);

    void updateAccount(Account account);

    void deleteAccount(String email, String accountName);

    BigDecimal getAllAccountBalance(String email);

    List<Account> getAllAccounts(String email);

    Optional<Account> getAccount(String email, String accountName);
}
