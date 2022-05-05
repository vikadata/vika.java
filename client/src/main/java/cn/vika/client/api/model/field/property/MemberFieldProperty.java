package cn.vika.client.api.model.field.property;

import java.util.List;

import cn.vika.client.api.model.field.property.option.MemberOption;

/**
 * @author tao
 */
public class MemberFieldProperty extends BaseFieldProperty {

    private List<MemberOption> options;

    public List<MemberOption> getOptions() {
        return options;
    }

    public void setOptions(List<MemberOption> options) {
        this.options = options;
    }

}
