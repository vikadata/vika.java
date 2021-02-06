package cn.vika.client.datasheet.model;

import java.util.Map;

/**
 * Record In Row Like Map structure
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:10:31
 */
public class RecordDetail {

    /**
     * record id
     */
    private String recordId;

    /**
     * record create timestamp
     */
    private Long createdAt;

    private Long updatedAt;

    /**
     * record fields
     */
    private Map<String, Object> fields;

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
