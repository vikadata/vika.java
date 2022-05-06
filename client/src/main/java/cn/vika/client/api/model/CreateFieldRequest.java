package cn.vika.client.api.model;

import cn.vika.client.api.model.field.property.BaseFieldProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
public class CreateFieldRequest <T extends BaseFieldProperty> {

    /**
     * field type
     * @see cn.vika.client.api.model.field.FieldType
     */
    private String type;

    /**
     * field name
     */
    private String name;

    /**
     * field property
     * @see cn.vika.client.api.model.field.property.BaseFieldProperty
     * value: BaseFieldProperty or it's subtype class
     */
    @JsonInclude(Include.NON_EMPTY)
    private T property;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getProperty() {
        return property;
    }

    public void setProperty(T property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "CreateFieldRequest{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", property=" + property +
                '}';
    }
}
