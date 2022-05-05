package cn.vika.client.api.model.field.property;

/**
 * @author tao
 */
public class NumberFieldProperty extends BaseFieldProperty {

    private String defaultValue;

    private int precision;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
}
