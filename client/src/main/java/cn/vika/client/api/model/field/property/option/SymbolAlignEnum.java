package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum SymbolAlignEnum {
    /**
     *
     */
    Default("Default"),
    /**
     *
     */
    Left("Left"),
    /**
     *
     */
    Right("Right");
    private final String value;

    SymbolAlignEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
