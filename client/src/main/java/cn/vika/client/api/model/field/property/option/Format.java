package cn.vika.client.api.model.field.property.option;

/**
 * @author tao
 */
public class Format<T extends TypeFormat> {

    private FormatType type;

    private T format;

    public FormatType getType() {
        return type;
    }

    public void setType(FormatType type) {
        this.type = type;
    }

    public T getFormat() {
        return format;
    }

    public void setFormat(T format) {
        this.format = format;
    }
}
