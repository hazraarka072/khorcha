package com.khorcha.repository.impl;

import com.khorcha.models.Users;
import com.khorcha.repository.UsersRepository;
import jakarta.inject.Singleton;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.Optional;

@Singleton
public class UsersRepositoryImpl implements UsersRepository {

    private final DynamoDbTable<Users> usersTable;

    public UsersRepositoryImpl(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        this.usersTable = enhancedClient.table("Users", TableSchema.fromBean(Users.class));
    }

    public void saveUser(Users user) {
        try {
            usersTable.putItem(user);
        } catch (DynamoDbException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    public Optional<Users> getUserByUsername(String username) {
        try {
            Users user = usersTable.getItem(r -> r.key(k -> k.partitionValue(username)));
            return Optional.ofNullable(user);
        } catch (DynamoDbException e) {
            throw new RuntimeException("Failed to fetch user", e);
        }
    }

    public void deleteUser(String username) {
        try {
            usersTable.deleteItem(r -> r.key(k -> k.partitionValue(username)));
        } catch (DynamoDbException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}
