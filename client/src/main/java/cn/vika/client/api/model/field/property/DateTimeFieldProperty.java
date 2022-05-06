package cn.vika.client.api.model.field.property;

import cn.vika.client.api.model.field.property.option.DateFormatEnum;
import cn.vika.client.api.model.field.property.option.TimeFormatEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
public class DateTimeFieldProperty extends BaseFieldProperty {

    private DateFormatEnum dateFormat;

    @JsonInclude(Include.NON_NULL)
    private TimeFormatEnum timeFormat;

    @JsonInclude(Include.NON_NULL)
    private Boolean autoFill;

    @JsonInclude(Include.NON_NULL)
    private Boolean includeTime;

    public DateFormatEnum getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormatEnum dateFormat) {
        this.dateFormat = dateFormat;
    }

    public TimeFormatEnum getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(TimeFormatEnum timeFormat) {
        this.timeFormat = timeFormat;
    }

    public Boolean isAutoFill() {
        return autoFill;
    }

    public void setAutoFill(Boolean autoFill) {
        this.autoFill = autoFill;
    }

    public Boolean isIncludeTime() {
        return includeTime;
    }

    public void setIncludeTime(Boolean includeTime) {
        this.includeTime = includeTime;
    }
}
