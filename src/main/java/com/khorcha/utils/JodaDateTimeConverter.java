package com.khorcha.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class JodaDateTimeConverter implements AttributeConverter<DateTime> {

    private static final DateTimeFormatter FORMATTER = ISODateTimeFormat.dateTime();

    @Override
    public AttributeValue transformFrom(DateTime input) {
        // Convert DateTime to a DynamoDB AttributeValue (string format)
        return AttributeValue.builder()
                .s(input != null ? FORMATTER.print(input) : null)
                .build();
    }

    @Override
    public DateTime transformTo(AttributeValue input) {
        // Convert AttributeValue (string format) to DateTime
        return input.s() != null ? FORMATTER.parseDateTime(input.s()) : null;
    }

    @Override
    public AttributeValueType attributeValueType() {
        // Indicate that this converter works with string attributes
        return AttributeValueType.S;
    }

    @Override
    public EnhancedType<DateTime> type() {
        // Specify the type of object this converter works with
        return EnhancedType.of(DateTime.class);
    }
}
