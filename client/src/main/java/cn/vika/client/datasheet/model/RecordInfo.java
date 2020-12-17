package cn.vika.client.datasheet.model;

import java.math.BigInteger;
import java.util.HashMap;

import cn.vika.api.model.AbstractModel;

/**
 * record detail
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:10:31
 */
public class RecordInfo extends AbstractModel {
    /**
     * record id
     */
    private String recordId;
    /**
     * record create timestamp
     */
    private BigInteger createdAt;

    /**
     * record fields
     */
    private HashMap<String, Object> fields;

    public HashMap<String, Object> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, Object> fields) {
        this.fields = fields;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public BigInteger getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(BigInteger createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * request query param to map
     *
     * @param map param map
     */
    @Override
    public void toMap(HashMap<String, String> map, String prefix) {

    }
}
