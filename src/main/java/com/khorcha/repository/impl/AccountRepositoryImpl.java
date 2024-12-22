package com.khorcha.repository.impl;

import com.khorcha.models.Account;
import com.khorcha.repository.AccountRepository;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;
import java.util.Optional;

@Singleton
public class AccountRepositoryImpl implements AccountRepository {

    private final DynamoDbTable<Account> accountTable;

    public AccountRepositoryImpl(DynamoDbClient dynamoDbClient, @Property(name = "dynamodb.table.accounts") String accountsTableName) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.accountTable = enhancedClient.table(accountsTableName, TableSchema.fromBean(Account.class));
    }

    public void saveAccount(Account account) {
        try {
            accountTable.putItem(account);
        } catch (DynamoDbException e) {
            throw new RuntimeException("Failed to save account ", e);
        }
    }

    public List<Account> getAccounts(String email) {
        try {
            QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                    .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(email).build()))
                    .build();
            return accountTable.query(queryRequest).items().stream().toList();
        } catch (DynamoDbException e) {
            throw new RuntimeException("Failed to fetch accounts", e);
        }
    }

    public Optional<Account> getAccount(String email, String accountName) {
        Key key = Key.builder()
                .partitionValue(email)
                .sortValue(accountName)
                .build();

        Account account = accountTable.getItem(r -> r.key(key));

        return Optional.ofNullable(account);
    }

    public void deleteAccount(String email, String accountName) {
        try {
            Key key = Key.builder()
                    .partitionValue(email)
                    .sortValue(accountName)
                    .build();

            accountTable.deleteItem(key);
        }
        catch (DynamoDbException e) {
            throw new RuntimeException("Failed to delete account ", e);
        }
    }
}
