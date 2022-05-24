package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum FormatTypeEnum {
    /**
     * Format about DateTime
     * @see cn.vika.client.api.model.field.property.option.DateTimeFormat
     */
    DateTime("DateTime"),
    /**
     * Format about Number
     * @see cn.vika.client.api.model.field.property.option.NumberFormat
     */
    Number("Number"),
    /**
     * Format about Percent
     * @see cn.vika.client.api.model.field.property.option.PercentFormat
     */
    Percent("Percent"),
    /**
     * Format about Currency
     * @see cn.vika.client.api.model.field.property.option.CurrencyFormat
     */
    Currency("Currency");

    private final String value;

    FormatTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
