package com.khorcha.services.impl;

import com.khorcha.models.RegistrationUser;
import com.khorcha.models.Users;
import com.khorcha.repository.UsersRepository;
import com.khorcha.services.UsersService;
import com.khorcha.utils.Password;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void registerUser(RegistrationUser registrationUser) {
        if (usersRepository.getUserByUsername(registrationUser.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User with username " + registrationUser.getUsername() + " already exists.");
        }

        // Transform RegistrationUser into Users
        Users user = new Users();
        user.setUsername(registrationUser.getUsername());
        user.setPasswordHash(Password.hashPassword(registrationUser.getPassword())); // Hash the password
        user.setEmail(registrationUser.getEmail());
        user.setStatus("ACTIVE");

        usersRepository.saveUser(user);
    }

    @Override
    public void updateUser(RegistrationUser registrationUser) {
        /*
        if (usersRepository.getUserByUsername(registrationUser.getUsername()).isEmpty()) {
            throw new IllegalArgumentException("User with username " + user.getUsername() + " does not exist.");
        }
        usersRepository.saveUser(user);
         */
    }

    @Override
    public void deleteUser(String username) {
        usersRepository.deleteUser(username);
        // needs to add logic for clean up of other details
    }
}
