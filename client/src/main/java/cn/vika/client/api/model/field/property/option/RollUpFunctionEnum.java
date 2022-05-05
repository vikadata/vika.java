package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum RollUpFunctionEnum {
    /**
     *
     */
    VALUES("VALUES"),
    /**
     *
     */
    AVERAGE("AVERAGE"),
    /**
     *
     */
    COUNT("COUNT"),
    /**
     *
     */
    COUNTA("COUNTA"),
    /**
     *
     */
    COUNTALL("COUNTALL"),
    /**
     *
     */
    SUM("SUM"),
    /**
     *
     */
    MIN("MIN"),
    /**
     *
     */
    MAX("MAX"),
    /**
     *
     */
    AND("AND"),
    /**
     *
     */
    OR("OR"),
    /**
     *
     */
    XOR("XOR"),
    /**
     *
     */
    CONCATENATE("CONCATENATE"),
    /**
     *
     */
    ARRAYJOIN("ARRAYJOIN"),
    /**
     *
     */
    ARRAYUNIQUE("ARRAYUNIQUE"),
    /**
     *
     */
    ARRAYCOMPACT("ARRAYCOMPACT");

    private final String value;

    RollUpFunctionEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
