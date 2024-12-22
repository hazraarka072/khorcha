package com.khorcha.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

import java.math.BigDecimal;

@Introspected
@Serdeable
@Data
public class RegistrationAccount {
    private String type;
    private String accountName;
    private BigDecimal currentBalance;
    private String description;
}
