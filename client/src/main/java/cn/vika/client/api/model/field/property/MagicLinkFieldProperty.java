package cn.vika.client.api.model.field.property;

/**
 * @author tao
 */
public class MagicLinkFieldProperty extends BaseFieldProperty {

    private String foreignDatasheetId;

    private String brotherFieldId = "";

    public String getForeignDatasheetId() {
        return foreignDatasheetId;
    }

    public void setForeignDatasheetId(String foreignDatasheetId) {
        this.foreignDatasheetId = foreignDatasheetId;
    }

    public String getBrotherFieldId() {
        return brotherFieldId;
    }

    public void setBrotherFieldId(String brotherFieldId) {
        this.brotherFieldId = brotherFieldId;
    }
}
