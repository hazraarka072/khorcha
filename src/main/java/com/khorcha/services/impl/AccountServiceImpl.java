package com.khorcha.services.impl;

import com.khorcha.dto.RegistrationAccount;
import com.khorcha.models.Account;
import com.khorcha.repository.AccountRepository;
import com.khorcha.services.AccountService;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerAccount(String email, RegistrationAccount registrationAccount) {
        if (accountRepository.getAccount(email, registrationAccount.getAccountName()).isPresent()) {
            throw new IllegalArgumentException("Account " + registrationAccount.getAccountName() + " already exists.");
        }

        Account account = getAccountFromregistrationAccount(email, registrationAccount);
        accountRepository.saveAccount(account);
    }

    @Override
    public void updateAccount(String email, RegistrationAccount registrationAccount) {
        if (accountRepository.getAccount(email, registrationAccount.getAccountName()).isEmpty()) {
            throw new IllegalArgumentException("Account " + registrationAccount.getAccountName() + " does not exist.");
        }
        Account account = getAccountFromregistrationAccount(email, registrationAccount);
        accountRepository.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.saveAccount(account);
    }

    private Account getAccountFromregistrationAccount(String email, RegistrationAccount registrationAccount) {
        Account account = new Account();
        account.setType(registrationAccount.getType());
        account.setAccountName(registrationAccount.getAccountName());
        account.setCurrentBalance(registrationAccount.getCurrentBalance());
        account.setDescription(registrationAccount.getDescription());
        account.setEmail(email);
        account.setStatus("ACTIVE");

        return account;
    }

    @Override
    public void deleteAccount(String email, String accountName) {
        accountRepository.deleteAccount(email,accountName);
    }

    @Override
    public List<Account> getAllAccounts(String email) {
        return accountRepository.getAccounts(email);
    }

    @Override
    public Optional<Account> getAccount(String email, String accountName) {
        return accountRepository.getAccount(email, accountName);
    }

    @Override
    public BigDecimal getAllAccountBalance(String email) {
        List<Account> accounts = this.getAllAccounts(email);

        return accounts.stream()
                .map(Account::getCurrentBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
