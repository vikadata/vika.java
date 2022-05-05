package cn.vika.client.api.model.field.property.option;

/**
 * @author tao
 */
public class MemberOption {

    private String id;

    private String name;

    private MemberEnum type;

    private String avatar;

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

    public MemberEnum getType() {
        return type;
    }

    public void setType(MemberEnum type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
