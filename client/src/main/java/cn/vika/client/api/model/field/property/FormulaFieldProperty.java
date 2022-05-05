package cn.vika.client.api.model.field.property;

import cn.vika.client.api.model.field.property.option.Format;

/**
 * @author tao
 */
public class FormulaFieldProperty extends BaseFieldProperty {

    private String expression;

    private Format<?> format;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Format<?> getFormat() {
        return format;
    }

    public void setFormat(Format<?> format) {
        this.format = format;
    }
}
