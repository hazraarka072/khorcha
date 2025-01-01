package com.khorcha.repository.impl;

import com.khorcha.models.Transaction;
import com.khorcha.repository.TransactionRepository;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Singleton
public class TransactionRepositoryImpl implements TransactionRepository {

    private final DynamoDbTable<Transaction> transactionTable;

    public TransactionRepositoryImpl(DynamoDbClient dynamoDbClient, @Property(name = "dynamodb.table.transactions") String transactionsTableName) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.transactionTable = enhancedClient.table(transactionsTableName, TableSchema.fromBean(Transaction.class));

    }

    @Override
    public void addTransaction(Transaction transaction) {
        try {
            this.transactionTable.putItem(transaction);
        } catch (DynamoDbException e) {
            throw new RuntimeException("Failed to add transaction ", e);
        }
    }
}
