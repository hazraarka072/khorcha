package com.khorcha.repository.impl;

import com.khorcha.models.Transaction;
import com.khorcha.repository.TransactionRepository;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class TransactionRepositoryImpl implements TransactionRepository {

    private final DynamoDbTable<Transaction> transactionTable;
    private final DateTimeFormatter dtFormatter;

    public TransactionRepositoryImpl(DynamoDbClient dynamoDbClient, @Property(name = "dynamodb.table.transactions") String transactionsTableName) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.transactionTable = enhancedClient.table(transactionsTableName, TableSchema.fromBean(Transaction.class));
        this.dtFormatter = ISODateTimeFormat.dateTime();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        try {
            this.transactionTable.putItem(transaction);
        } catch (DynamoDbException e) {
            throw new RuntimeException("Failed to add transaction ", e);
        }
    }

    public List<Transaction> getAllTransactionsBetweenDates(String id, DateTime startDate, DateTime endDate) {
        QueryConditional queryConditional = QueryConditional.sortBetween(
                Key.builder().partitionValue(id).sortValue(startDate.toString(dtFormatter)).build(),
                Key.builder().partitionValue(id).sortValue(endDate.toString(dtFormatter)).build()
        );

        // Execute the query
        return transactionTable.query(r -> r.queryConditional(queryConditional))
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionsBetweenDates(String id, String transactionType, DateTime startDate, DateTime endDate) {
        QueryConditional queryConditional = QueryConditional.sortBetween(
                Key.builder().partitionValue(id).sortValue(startDate.toString(dtFormatter)).build(),
                Key.builder().partitionValue(id).sortValue(endDate.toString(dtFormatter)).build()
        );
        Expression filterExpression = Expression.builder()
                .expression("transactionType = :transactionType")
                .expressionValues(Map.of(":transactionType", AttributeValue.builder().s(transactionType).build()))
                .build();
        QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .filterExpression(filterExpression)
                .build();
        // Execute the query
        return transactionTable.query(queryRequest)
                .items()
                .stream()
                .collect(Collectors.toList());
    }
}
