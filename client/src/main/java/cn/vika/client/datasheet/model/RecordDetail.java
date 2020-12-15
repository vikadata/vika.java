package cn.vika.client.datasheet.model;

import java.util.Map;

/**
 * record detail
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
     * record fields
     */
    private Map<String, Object> fields;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

}
