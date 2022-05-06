package cn.vika.client.api.model.field.property;

import cn.vika.client.api.model.field.property.option.PrecisionEnum;
import cn.vika.client.api.model.field.property.option.SymbolAlignEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
@JsonInclude(Include.NON_EMPTY)
public class CurrencyFieldProperty extends BaseFieldProperty {

    @JsonInclude(Include.NON_NULL)
    private String defaultValue;

    @JsonInclude(Include.NON_NULL)
    private PrecisionEnum precision;

    @JsonInclude(Include.NON_NULL)
    private String symbol;

    @JsonInclude(Include.NON_NULL)
    private SymbolAlignEnum symbolAlign;

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public SymbolAlignEnum getSymbolAlign() {
        return symbolAlign;
    }

    public void setSymbolAlign(SymbolAlignEnum symbolAlign) {
        this.symbolAlign = symbolAlign;
    }
}
