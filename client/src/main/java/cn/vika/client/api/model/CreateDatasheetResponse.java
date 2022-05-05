package cn.vika.client.api.model;

import java.util.List;

/**
 * @author tao
 */
public class CreateDatasheetResponse {

    private String id;

    private long createdAt;

    private List<CreateFieldResponse> fields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public List<CreateFieldResponse> getFields() {
        return fields;
    }

    public void setFields(List<CreateFieldResponse> fields) {
        this.fields = fields;
    }

}
