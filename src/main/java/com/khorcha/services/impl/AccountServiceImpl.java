package com.khorcha.services.impl;

import com.khorcha.dto.RegistrationAccount;
import com.khorcha.models.Account;
import com.khorcha.repository.AccountRepository;
import com.khorcha.services.AccountService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Singleton
@Slf4j
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
        log.info("Registered account for user {} : {}", email, account.getAccountName());
    }

    @Override
    public void updateAccount(String email, RegistrationAccount registrationAccount) {
        if (accountRepository.getAccount(email, registrationAccount.getAccountName()).isEmpty()) {
            throw new IllegalArgumentException("Account " + registrationAccount.getAccountName() + " does not exist.");
        }
        Account account = getAccountFromregistrationAccount(email, registrationAccount);
        accountRepository.saveAccount(account);
        log.info("Updated account for user {} : {}", email, account.getAccountName());
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.saveAccount(account);
        log.info("Updated account for user {} : {}", account.getEmail(), account.getAccountName());
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
        log.info("Deleted account for user {} : {}", email, accountName);
    }

    @Override
    public List<Account> getAllAccounts(String email) {
        log.info("Fetching all accounts for user {}", email);
        return accountRepository.getAccounts(email);
    }

    @Override
    public Optional<Account> getAccount(String email, String accountName) {
        log.info("Fetching account {} for user {}", accountName, email);
        return accountRepository.getAccount(email, accountName);
    }

    @Override
    public BigDecimal getAllAccountBalance(String email) {
        List<Account> accounts = this.getAllAccounts(email);
        log.info("Fetching total balance for user {}", email);
        return accounts.stream()
                .map(Account::getCurrentBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
