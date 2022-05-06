package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum CollectTypeEnum {

    /**
     * Indicates that the value of any field is changed when it is updated
     */
    ALL(0),
    /**
     *Indicates that the value of specified field is changed when it is updated
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
