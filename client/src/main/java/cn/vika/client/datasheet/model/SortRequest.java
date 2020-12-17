package cn.vika.client.datasheet.model;

import java.util.HashMap;

import cn.vika.api.model.AbstractModel;

/**
 * query record with sort param
 *
 * @author Zoe Zheng
 * @date 2020-12-17 11:22:01
 */
public class SortRequest extends AbstractModel {

    private Order order;

    public String field;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    /**
     * request query param to map
     *
     * @param map param
     * @param prefix used for object
     */
    @Override
    public void toMap(HashMap<String, String> map, String prefix) {
        setParamSimple(map, prefix + "order", order.name().toLowerCase());
        setParamSimple(map, prefix + "field", field);
    }
}
