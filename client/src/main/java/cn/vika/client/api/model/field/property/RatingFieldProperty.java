package cn.vika.client.api.model.field.property;

/**
 * @author tao
 */
public class RatingFieldProperty extends BaseFieldProperty {

    private String icon;

    private int max;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
