package cn.vika.client.api.model.field.property;

import java.util.List;

import cn.vika.client.api.model.field.property.option.CollectTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
public class LastModifiedTimeFieldProperty extends CreatedTimeFieldProperty {

    private CollectTypeEnum collectType;

    @JsonInclude(Include.NON_EMPTY)
    private List<String> fieldIdCollection;

    public CollectTypeEnum getCollectType() {
        return collectType;
    }

    public void setCollectType(CollectTypeEnum collectType) {
        this.collectType = collectType;
    }

    public List<String> getFieldIdCollection() {
        return fieldIdCollection;
    }

    public void setFieldIdCollection(List<String> fieldIdCollection) {
        this.fieldIdCollection = fieldIdCollection;
    }
}
