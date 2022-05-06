package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum TimeFormatEnum {
    /**
     * 12-hour clock
     */
    HOUR_MINUTE_12("hh:mm"),
    /**
     * 24-hour clock
     */
    HOUR_MINUTE_24("HH:mm");

    private final String value;
    TimeFormatEnum(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
