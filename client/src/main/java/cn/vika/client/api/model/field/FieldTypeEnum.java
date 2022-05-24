package cn.vika.client.api.model.field;

/**
 * the enumerations are field types' values to create field.
 *
 * @author tao
 */
public enum FieldTypeEnum {
    /**
     * the request body field type's value to create vika SingleText field.
     */
    SingleText("SingleText"),
    /**
     * the request body field type's value to create vika Text field.
     */
    Text("Text"),
    /**
     * the request body field type's value to create vika SingleSelect field.
     */
    SingleSelect("SingleSelect"),
    /**
     * the request body field type's value to create vika MultiSelect field.
     */
    MultiSelect("MultiSelect"),
    /**
     * the request body field type's value to create vika Number field.
     */
    Number("Number"),
    /**
     * the request body field type's value to create vika Currency field.
     */
    Currency("Currency"),
    /**
     * the request body field type's value to create vika Percent field.
     */
    Percent("Percent"),
    /**
     * the request body field type's value to create vika DateTime field.
     */
    DateTime("DateTime"),
    /**
     * the request body field type's value to create vika Attachment field.
     */
    Attachment("Attachment"),
    /**
     * the request body field type's value to create vika Member field.
     */
    Member("Member"),
    /**
     * the request body field type's value to create vika Checkbox field.
     */
    Checkbox("Checkbox"),
    /**
     * the request body field type's value to create vika Rating field.
     */
    Rating("Rating"),
    /**
     * the request body field type's value to create vika URL field.
     */
    URL("URL"),
    /**
     * the request body field type's value to create vika Phone field.
     */
    Phone("Phone"),
    /**
     * the request body field type's value to create vika Email field.
     */
    Email("Email"),
    /**
     * the request body field type's value to create vika MagicLink field.
     */
    MagicLink("MagicLink"),
    /**
     * the request body field type's value to create vika MagicLookUp field.
     */
    MagicLookUp("MagicLookUp"),
    /**
     * the request body field type's value to create vika Formula field.
     */
    Formula("Formula"),
    /**
     * the request body field type's value to create vika AutoNumber field.
     */
    AutoNumber("AutoNumber"),
    /**
     * the request body field type's value to create vika CreatedTime field.
     */
    CreatedTime("CreatedTime"),
    /**
     * the request body field type's value of create vika LastModifiedTime field.
     */
    LastModifiedTime("LastModifiedTime"),
    /**
     * the request body field type's value to create vika CreatedBy field.
     */
    CreatedBy("CreatedBy"),
    /**
     * the request body field type's value to create vika LastModifiedBy field.
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
