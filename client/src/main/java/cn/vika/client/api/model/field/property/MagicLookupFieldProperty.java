package cn.vika.client.api.model.field.property;

import cn.vika.client.api.model.field.property.option.Format;
import cn.vika.client.api.model.field.property.option.RollUpFunctionEnum;
import cn.vika.client.api.model.field.property.option.TypeFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
public class MagicLookupFieldProperty extends BaseFieldProperty {

    private String relatedLinkFieldId;

    private String targetFieldId;

    @JsonInclude(Include.NON_NULL)
    private RollUpFunctionEnum rollupFunction;

    @JsonInclude(Include.NON_NULL)
    private Format<?> format;

    public String getRelatedLinkFieldId() {
        return relatedLinkFieldId;
    }

    public void setRelatedLinkFieldId(String relatedLinkFieldId) {
        this.relatedLinkFieldId = relatedLinkFieldId;
    }

    public String getTargetFieldId() {
        return targetFieldId;
    }

    public void setTargetFieldId(String targetFieldId) {
        this.targetFieldId = targetFieldId;
    }

    public RollUpFunctionEnum getRollupFunction() {
        return rollupFunction;
    }

    public void setRollupFunction(RollUpFunctionEnum rollupFunction) {
        this.rollupFunction = rollupFunction;
    }

    public Format<?> getFormat() {
        return format;
    }

    public <T extends TypeFormat> void setFormat(Format<T> format) {
        this.format = format;
    }
}
