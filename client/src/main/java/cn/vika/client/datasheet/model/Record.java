package cn.vika.client.datasheet.model;

/**
 * create record request params
 *
 * @author Zoe Zheng
 * @date 2020-12-17 11:22:01
 */
public class Record {

    private RecordDetail[] records;

    private String fieldKey;

    public RecordDetail[] getRecords() {
        return records;
    }

    public void setRecords(RecordDetail[] records) {
        this.records = records;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }
}
