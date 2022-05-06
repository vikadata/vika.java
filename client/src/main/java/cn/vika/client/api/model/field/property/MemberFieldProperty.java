package cn.vika.client.api.model.field.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author tao
 */
public class MemberFieldProperty extends BaseFieldProperty {

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("isMulti")
    private Boolean isMulti;

    @JsonInclude(Include.NON_NULL)
    private Boolean shouldSendMsg;

    @JsonIgnore
    public Boolean getMulti() {
        return isMulti;
    }

    public void setMulti(boolean multi) {
        isMulti = multi;
    }

    public Boolean isShouldSendMsg() {
        return shouldSendMsg;
    }

    public void setShouldSendMsg(boolean shouldSendMsg) {
        this.shouldSendMsg = shouldSendMsg;
    }
}
