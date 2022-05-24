package cn.vika.client.api.model.field.property;

import java.util.List;

import cn.vika.client.api.model.field.property.option.CollectTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
public class LastModifiedByFieldProperty extends BaseFieldProperty {

    private CollectTypeEnum collectType;

    @JsonInclude(Include.NON_EMPTY)
    private List<String> fieldsIdCollection;

    public CollectTypeEnum getCollectType() {
        return collectType;
    }

    public void setCollectType(CollectTypeEnum collectType) {
        this.collectType = collectType;
    }

    public List<String> getFieldsIdCollection() {
        return fieldsIdCollection;
    }

    public void setFieldsIdCollection(List<String> fieldsIdCollection) {
        this.fieldsIdCollection = fieldsIdCollection;
    }
}
