package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum FormatType {
    /**
     *
     */
    DateTime("DateTime"),
    /**
     *
     */
    Number("Number"),
    /**
     *
     */
    Percent("Percent"),
    /**
     *
     */
    Currency("Currency"),
    ;

    private final String value;

    FormatType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
