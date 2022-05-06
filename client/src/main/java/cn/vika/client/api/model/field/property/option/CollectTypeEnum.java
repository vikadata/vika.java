package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum CollectTypeEnum {

    /**
     *
     */
    ALL(0),
    /**
     *
     */
    SPECIFIED(1);

    private final int value;

    CollectTypeEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
