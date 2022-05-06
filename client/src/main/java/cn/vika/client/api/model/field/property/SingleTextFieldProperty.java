package cn.vika.client.api.model.field.property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author tao
 */
public class SingleTextFieldProperty extends BaseFieldProperty {

    @JsonInclude(Include.NON_NULL)
    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
