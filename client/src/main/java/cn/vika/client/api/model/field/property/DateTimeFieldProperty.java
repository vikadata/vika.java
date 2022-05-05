package cn.vika.client.api.model.field.property;

/**
 * @author tao
 */
public class DateTimeFieldProperty extends BaseFieldProperty {

    private String format;

    private boolean autoFill = false;

    private boolean bool = false;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isAutoFill() {
        return autoFill;
    }

    public void setAutoFill(boolean autoFill) {
        this.autoFill = autoFill;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
