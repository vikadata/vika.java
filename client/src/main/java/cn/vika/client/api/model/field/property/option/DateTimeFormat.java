package cn.vika.client.api.model.field.property.option;

/**
 * @author tao
 */
public class DateTimeFormat extends TypeFormat{

    private String dateFormat;

    private String timeFormat;

    private boolean includeTime;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public boolean isIncludeTime() {
        return includeTime;
    }

    public void setIncludeTime(boolean includeTime) {
        this.includeTime = includeTime;
    }
}
