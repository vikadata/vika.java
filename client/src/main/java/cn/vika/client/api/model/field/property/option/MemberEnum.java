package cn.vika.client.api.model.field.property.option;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author tao
 */
public enum MemberEnum {
    /**
     *
     */
    Member("member"),

    /**
     *
     */
    Team("Team");

    private final String value;

    MemberEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
