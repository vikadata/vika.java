package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum RollUpFunctionEnum {
    /**
     * raw reference:
     * reference the data for the selected columns as-is from the associated datasheet.
     */
    VALUES("VALUES"),
    /**
     * calculate the arithmetic average of the raw reference.
     */
    AVERAGE("AVERAGE"),
    /**
     * count a non-empty number type for the raw reference.
     */
    COUNT("COUNT"),
    /**
     * count the number of non-null values for the raw reference.
     * this function evaluates both numerical and textual values.
     */
    COUNTA("COUNTA"),
    /**
     * count the number of records in the raw reference.
     * this method counts all values, including blank records.
     */
    COUNTALL("COUNTALL"),
    /**
     * sum of values for the raw referenced.
     */
    SUM("SUM"),
    /**
     * the minimum value in the raw reference's values.
     */
    MIN("MIN"),
    /**
     * the maximum value in the raw reference's values.
     */
    MAX("MAX"),
    /**
     * if the raw reference's values all are not empty, return true.
     */
    AND("AND"),
    /**
     * if there are at least one not-empty value in the raw reference's values, return true.
     */
    OR("OR"),
    /**
     * if there are an odd number of values are not empty in the raw reference's values, return true.
     */
    XOR("XOR"),
    /**
     *  the raw reference's values are joined into text.
     */
    CONCATENATE("CONCATENATE"),
    /**
     * the raw reference's values are joined into a string with comma.
     */
    ARRAYJOIN("ARRAYJOIN"),
    /**
     * deduplicate the raw reference's values.
     */
    ARRAYUNIQUE("ARRAYUNIQUE"),
    /**
     * delete the empty strings and empty values in the raw reference's values.
     * but keep the "false" values and the string with whitespace character values.
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
