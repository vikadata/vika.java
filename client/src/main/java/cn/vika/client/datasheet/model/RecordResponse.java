package cn.vika.client.datasheet.model;

import java.util.HashMap;

import cn.vika.api.model.AbstractModel;

/**
 * create record response
 *
 * @author Zoe Zheng
 * @date 2020-12-17 11:22:01
 */
public class RecordResponse extends AbstractModel {

    private RecordInfo[] records;

    public RecordInfo[] getRecords() {
        return records;
    }

    public void setRecords(RecordInfo[] records) {
        this.records = records;
    }

    /**
     * request query param to map
     *
     * @param map
     */
    @Override
    public void toMap(HashMap<String, String> map, String prefix) {}
}
