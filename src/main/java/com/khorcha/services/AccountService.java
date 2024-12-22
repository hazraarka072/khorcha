package com.khorcha.services;

import com.khorcha.models.Account;
import com.khorcha.dto.RegistrationAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    void registerAccount(RegistrationAccount registrationAccount);

    void updateAccount(RegistrationAccount registrationAccount);

    void deleteAccount(String accountName);

    BigDecimal getAllAccountBalance();

    List<Account> getAllAccounts();

    Optional<Account> getAccount(String accountName);
}
