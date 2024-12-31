package com.khorcha.models;

import com.khorcha.constants.TransactionType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import org.joda.time.DateTime;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;

@Introspected
@Serdeable
@Data
@DynamoDbBean
public class Transaction {
    private String id;
    private String description;
    private TransactionType transactionType;
    private BigDecimal amount;
    private DateTime time;
    private String email;
    private String account;

    @DynamoDbPartitionKey
    public String getId() {
        return this.id;
    }
}
