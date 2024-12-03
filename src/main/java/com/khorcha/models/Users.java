package com.khorcha.models;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Introspected
@Data
@DynamoDbBean
public class Users {
    private String username;
    private String email;
    private String passwordHash;
    private String status;

    @DynamoDbPartitionKey
    public String getUsername() {
        return username;
    }
}
