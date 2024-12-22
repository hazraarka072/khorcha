package com.khorcha.services;

import com.khorcha.models.RegistrationAccount;

public interface AccountService {

    void registerAccount(RegistrationAccount registrationAccount);

    void updateAccount(RegistrationAccount registrationAccount);

    void deleteAccount(String accountName);
}
