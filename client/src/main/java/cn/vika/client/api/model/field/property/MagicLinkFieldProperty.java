package cn.vika.client.api.model.field.property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
public class MagicLinkFieldProperty extends BaseFieldProperty {

    private String foreignDatasheetId;

    @JsonInclude(Include.NON_NULL)
    private String limitToViewId;

    @JsonInclude(Include.NON_NULL)
    private Boolean limitSingleRecord;

    public String getForeignDatasheetId() {
        return foreignDatasheetId;
    }

    public void setForeignDatasheetId(String foreignDatasheetId) {
        this.foreignDatasheetId = foreignDatasheetId;
    }

    public String getLimitToViewId() {
        return limitToViewId;
    }

    public void setLimitToViewId(String limitToViewId) {
        this.limitToViewId = limitToViewId;
    }

    public Boolean getLimitSingleRecord() {
        return limitSingleRecord;
    }

    public void setLimitSingleRecord(Boolean limitSingleRecord) {
        this.limitSingleRecord = limitSingleRecord;
    }
}
