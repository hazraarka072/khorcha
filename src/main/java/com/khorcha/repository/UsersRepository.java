package com.khorcha.repository;

import com.khorcha.models.Users;
import jakarta.inject.Singleton;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.Optional;

@Singleton
public interface UsersRepository {

    void saveUser(Users user);

    Optional<Users> getUserByUsername(String username);

    void deleteUser(String username);
}
