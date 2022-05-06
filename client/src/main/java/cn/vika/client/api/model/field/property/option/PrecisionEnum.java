package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum PrecisionEnum {
    /**
     * field's value is integer
     * example: 1
     */
    POINT0(0),
    /**
     * digital precision: to one decimal place
     * example: 1.1
     */
    POINT1(1),
    /**
     * digital precision: to two decimal place
     * example: 1.11
     */
    POINT2(2),
    /**
     * digital precision: to three decimal place
     * example: 1.111
     */
    POINT3(3),
    /**
     * digital precision: to four decimal place
     * example: 1.1111
     */
    POINT4(4);

    private final int value;

    PrecisionEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
