package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum DateFormatEnum {
    /**
     * Date's format "2022/01/30"
     */
    DATE_SLASH("YYYY/MM/DD"),
    /**
     * Date's format "2022-01-30"
     */
    DATE("YYYY-MM-DD"),
    /**
     * Date's format "01/30/2022"
     */
    EUROPEAN_DATE_SLASH("DD/MM/YYYY"),
    /**
     * Date's format "2022-01"
     */
    YEAR_MOUTH("YYYY-MM"),
    /**
     * Date's format "01-30"
     */
    MOUTH_DAY("MM-DD"),
    /**
     * Date's format "2020"
     */
    YEAR("YYYY"),
    /**
     * Date's format "01"
     */
    MOUNT("MM"),
    /**
     * Date's format "30"
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
