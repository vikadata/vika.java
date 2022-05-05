package cn.vika.client.api.model.field.property;

import java.util.List;

import cn.vika.client.api.model.field.property.option.UserOption;

/**
 * @author tao
 */
public class CreatedByFieldProperty extends BaseFieldProperty {

    private List<UserOption> options;

    public List<UserOption> getOptions() {
        return options;
    }

    public void setOptions(List<UserOption> options) {
        this.options = options;
    }
}
