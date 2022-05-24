package cn.vika.client.api.model.field.property;

import cn.vika.client.api.model.field.property.option.PrecisionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
public class NumberFieldProperty extends BaseFieldProperty {

    @JsonInclude(Include.NON_NULL)
    private String defaultValue;

    private PrecisionEnum precision;

    @JsonInclude(Include.NON_NULL)
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public PrecisionEnum getPrecision() {
        return precision;
    }

    public void setPrecision(PrecisionEnum precision) {
        this.precision = precision;
    }
}
