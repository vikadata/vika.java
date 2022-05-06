package cn.vika.client.api.model.field.property;

import java.util.List;

import cn.vika.client.api.model.field.property.option.SelectOption;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
public class SingleSelectFieldProperty extends BaseFieldProperty {

    private List<SelectOption> options;

    @JsonInclude(Include.NON_NULL)
    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<SelectOption> getOptions() {
        return options;
    }

    public void setOptions(List<SelectOption> options) {
        this.options = options;
    }
}
