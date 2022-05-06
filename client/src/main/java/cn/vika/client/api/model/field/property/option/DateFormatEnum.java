package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum DateFormatEnum {
    /**
     *
     */
    DATE_SLASH("YYYY/MM/DD"),
    /**
     *
     */
    DATE("YYYY-MM-DD"),
    /**
     *
     */
    EUROPEAN_DATE_SLASH("DD/MM/YYYY"),
    /**
     *
     */
    YEAR_MOUTH("YYYY-MM"),
    /**
     *
     */
    MOUTH_DAY("MM-DD"),
    /**
     *
     */
    YEAR("YYYY"),
    /**
     *
     */
    MOUNT("MM"),
    /**
     *
     */
    DAY("DD");
    private final String value;

    DateFormatEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
