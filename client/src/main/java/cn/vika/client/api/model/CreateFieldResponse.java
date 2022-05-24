package cn.vika.client.api.model;

/**
 * @author tao
 */
public class CreateFieldResponse {

    private String id;

    private String name;

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

    @Override
    public String toString() {
        return "CreateFieldResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
