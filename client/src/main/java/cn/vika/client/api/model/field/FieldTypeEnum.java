package cn.vika.client.api.model.field;

/**
 * field types
 *
 * @author tao
 */
public enum FieldTypeEnum {
    /**
     *
     */
    SingleText("SingleText"),
    /**
     *
     */
    Text("Text"),
    /**
     *
     */
    SingleSelect("SingleSelect"),
    /**
     *
     */
    MultiSelect("MultiSelect"),
    /**
     *
     */
    Number("Number"),
    /**
     *
     */
    Currency("Currency"),
    /**
     *
     */
    Percent("Percent"),
    /**
     *
     */
    DateTime("DateTime"),
    /**
     *
     */
    Attachment("Attachment"),
    /**
     *
     */
    Member("Member"),
    /**
     *
     */
    Checkbox("Checkbox"),
    /**
     *
     */
    Rating("Rating"),
    /**
     *
     */
    URL("URL"),
    /**
     *
     */
    Phone("Phone"),
    /**
     *
     */
    Email("Email"),
    /**
     *
     */
    MagicLink("MagicLink"),
    /**
     *
     */
    MagicLookUp("MagicLookUp"),
    /**
     *
     */
    Formula("Formula"),
    /**
     *
     */
    AutoNumber("AutoNumber"),
    /**
     *
     */
    CreatedTime("CreatedTime"),
    /**
     *
     */
    LastModifiedTime("LastModifiedTime"),
    /**
     *
     */
    CreatedBy("CreatedBy"),
    /**
     *
     */
    LastModifiedBy("LastModifiedBy");

    private final String fieldType;

    FieldTypeEnum(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldType() {
        return fieldType;
    }
}
