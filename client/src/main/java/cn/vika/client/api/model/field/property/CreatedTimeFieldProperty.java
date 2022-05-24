package cn.vika.client.api.model.field.property;

import cn.vika.client.api.model.field.property.option.DateFormatEnum;
import cn.vika.client.api.model.field.property.option.TimeFormatEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author tao
 */
public class CreatedTimeFieldProperty extends DateTimeFieldProperty{

    private DateFormatEnum dateFormat;

    @JsonInclude(Include.NON_NULL)
    private TimeFormatEnum timeFormat;

    @JsonInclude(Include.NON_NULL)
    private Boolean includeTime;

    @Override
    public DateFormatEnum getDateFormat() {
        return dateFormat;
    }

    @Override
    public void setDateFormat(DateFormatEnum dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public TimeFormatEnum getTimeFormat() {
        return timeFormat;
    }

    @Override
    public void setTimeFormat(TimeFormatEnum timeFormat) {
        this.timeFormat = timeFormat;
    }

    public Boolean getIncludeTime() {
        return includeTime;
    }

    @Override
    public void setIncludeTime(Boolean includeTime) {
        this.includeTime = includeTime;
    }
}
