package com.khorcha.models;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.math.BigDecimal;

@Introspected
@Data
@DynamoDbBean
public class Account {
    private String type;
    private String accountName;
    private BigDecimal currentBalance;
    private String description;
    private String email;
    private String status;

    @DynamoDbPartitionKey
    public String getEmail() {
        return email;
    }

    @DynamoDbSortKey
    public String getAccountName(){
        return accountName;
    }
}
