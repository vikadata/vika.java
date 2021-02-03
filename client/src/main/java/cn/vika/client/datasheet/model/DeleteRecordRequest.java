package cn.vika.client.datasheet.model;

import java.util.HashMap;

import cn.vika.api.model.AbstractModel;

/**
 * create record request params
 *
 * @author Zoe Zheng
 * @date 2020-12-17 11:22:01
 */
public class DeleteRecordRequest extends AbstractModel {

    private String[] recordIds;

    public String[] getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String[] recordIds) {
        this.recordIds = recordIds;
    }

    /**
     * request query param to map
     *
     * @param map
     */
    @Override
    public void toMap(HashMap<String, String> map, String prefix) {
        setParamArraySimple(map, prefix + "recordIds", recordIds);
    }
}
