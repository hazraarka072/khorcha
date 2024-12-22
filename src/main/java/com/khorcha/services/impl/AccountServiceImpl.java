package com.khorcha.services.impl;

import com.khorcha.models.RegistrationAccount;
import com.khorcha.models.Account;
import com.khorcha.repository.AccountRepository;
import com.khorcha.services.AccountService;
import com.khorcha.utils.Password;
import com.khorcha.utils.ThreadLocalContext;
import jakarta.inject.Singleton;

@Singleton
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerAccount(RegistrationAccount registrationAccount) {
        String email = ThreadLocalContext.getValue("email").toString();
        if (accountRepository.getAccount(email, registrationAccount.getAccountName()).isPresent()) {
            throw new IllegalArgumentException("Account " + registrationAccount.getAccountName() + " already exists.");
        }

        // Transform RegistrationUser into Users
        Account account = new Account();
        account.setType(registrationAccount.getType());
        account.setAccountName(registrationAccount.getAccountName());
        account.setCurrentBalance(registrationAccount.getCurrentBalance());
        account.setDescription(registrationAccount.setDescription());
        account.setEmail(email);
        account.setStatus("ACTIVE");

        accountRepository.saveAccount(account);
    }

    @Override
    public void updateAccount(RegistrationAccount registrationUser) {
        /*
        if (usersRepository.getUserByUsername(registrationUser.getUsername()).isEmpty()) {
            throw new IllegalArgumentException("User with username " + user.getUsername() + " does not exist.");
        }
        usersRepository.saveUser(user);
         */
    }

    @Override
    public void deleteAccount(String accountName) {
        String email = ThreadLocalContext.getValue("email").toString();
        accountRepository.deleteAccount(email,accountName);
    }
}
