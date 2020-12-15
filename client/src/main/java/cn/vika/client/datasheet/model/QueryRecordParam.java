package cn.vika.client.datasheet.model;

import java.util.HashMap;

import cn.vika.api.model.AbstractModel;

/**
 * test
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:14:31
 */
public class QueryRecordParam extends AbstractModel {

    private String[] recordIds;
    private String[] fields;
    private String[] sort;
    private String viewId;
    private String filterByFormula;
    private String cellFormat;
    private String fieldKey;
    private Integer pageNum;
    private Integer pageSize;
    private Integer maxRecords;

    public String[] getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String[] recordIds) {
        this.recordIds = recordIds;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String[] getSort() {
        return sort;
    }

    public void setSort(String[] sort) {
        this.sort = sort;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getFilterByFormula() {
        return filterByFormula;
    }

    public void setFilterByFormula(String filterByFormula) {
        this.filterByFormula = filterByFormula;
    }

    public String getCellFormat() {
        return cellFormat;
    }

    public void setCellFormat(String cellFormat) {
        this.cellFormat = cellFormat;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(Integer maxRecords) {
        this.maxRecords = maxRecords;
    }

    @Override
    public void toMap(HashMap<String, String> map) {
        setParamArraySimple(map, "recordIds", recordIds);
        setParamSimple(map, "viewId", viewId);
        setParamArraySimple(map, "fields", fields);
        setParamSimple(map, "filterByFormula", filterByFormula);
        setParamSimple(map, "cellFormat", cellFormat);
        setParamSimple(map, "fieldKey", fieldKey);
        setParamArraySimple(map, "sort", sort);
        setParamSimple(map, "pageNum", pageNum);
        setParamSimple(map, "pageSize", pageSize);
        setParamSimple(map, "maxRecords", maxRecords);
    }
}
