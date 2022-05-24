package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum SymbolAlignEnum {
    /**
     * the monetary unit is fixed to the left.
     */
    Default("Default"),
    /**
     * the monetary unit is fixed to the left.
     */
    Left("Left"),
    /**
     * the monetary unit is fixed to the right.
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
