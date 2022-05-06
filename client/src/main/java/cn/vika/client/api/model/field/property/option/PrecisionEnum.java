package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum PrecisionEnum {
    /**
     *
     */
    POINT0(0),
    /**
     *
     */
    POINT1(1),
    /**
     *
     */
    POINT2(2),
    /**
     *
     */
    POINT3(3),
    /**
     *
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
