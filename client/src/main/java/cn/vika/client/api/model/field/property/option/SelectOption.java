package cn.vika.client.api.model.field.property.option;

/**
 * @author tao
 */
public class SelectOption {

    private String id;

    private String name;

    private SelectOptionColor color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SelectOptionColor getColor() {
        return color;
    }

    public void setColor(SelectOptionColor color) {
        this.color = color;
    }
}
