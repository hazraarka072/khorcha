package com.khorcha.dto;

import com.khorcha.constants.TransactionType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Introspected
@Data
@Serdeable
public class TransactionRequest {
    private String description;
    private TransactionType transactionType;
    private BigDecimal amount;
    private DateTime time;
}
