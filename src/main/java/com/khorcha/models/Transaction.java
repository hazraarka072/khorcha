package com.khorcha.models;

import com.khorcha.constants.TransactionType;
import com.khorcha.utils.JodaDateTimeConverter;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import org.joda.time.DateTime;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

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

    @DynamoDbConvertedBy(JodaDateTimeConverter.class)
    @DynamoDbSortKey
    public DateTime getTime() { return this.time; }

    @DynamoDbSecondaryPartitionKey(indexNames = "transactionType")
    public TransactionType getTransactionType() { return this.transactionType; }
}
