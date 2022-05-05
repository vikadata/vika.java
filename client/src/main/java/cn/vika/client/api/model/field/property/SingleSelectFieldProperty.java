package cn.vika.client.api.model.field.property;

import java.util.List;

import cn.vika.client.api.model.field.property.option.SelectOption;

/**
 * @author tao
 */
public class SingleSelectFieldProperty extends BaseFieldProperty {

    private List<SelectOption> options;

    public List<SelectOption> getOptions() {
        return options;
    }

    public void setOptions(List<SelectOption> options) {
        this.options = options;
    }
}
