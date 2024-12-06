package com.khorcha.services;

import com.khorcha.models.RegistrationUser;
import com.khorcha.models.Users;

import java.util.Optional;

public interface UsersService {

    void registerUser(RegistrationUser registrationUser);

    void updateUser(RegistrationUser registrationUser);

    void deleteUser(String username);
}
