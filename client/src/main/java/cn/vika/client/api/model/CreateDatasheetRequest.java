package cn.vika.client.api.model;

import java.util.List;

/**
 * @author tao
 */
public class CreateDatasheetRequest {

    private String name;

    private String description;

    private String folderId;

    private String preNodeId;

    private List<CreateFieldRequest<?>> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getPreNodeId() {
        return preNodeId;
    }

    public void setPreNodeId(String preNodeId) {
        this.preNodeId = preNodeId;
    }

    public List<CreateFieldRequest<?>> getFields() {
        return fields;
    }

    public void setFields(List<CreateFieldRequest<?>> fields) {
        this.fields = fields;
    }
}
