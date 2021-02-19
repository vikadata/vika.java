/*
 * Copyright (C) 2021 vikadata
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package cn.vika.client.api.models;

import java.util.HashMap;

import cn.vika.client.api.model.AbstractModel;

/**
 * query record param
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:14:31
 */
public class QueryRecordRequest extends AbstractModel {

    private String[] recordIds;
    private String[] fields;
    private SortRequest[] sort;
    private String viewId;
    private String filterByFormula;
    private String cellFormat;
    private FieldKey fieldKey;
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

    public SortRequest[] getSort() {
        return sort;
    }

    public void setSort(SortRequest[] sort) {
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

    public FieldKey getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(FieldKey fieldKey) {
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
    public void toMap(HashMap<String, String> map, String prefix) {
        setParamArrayObj(map, prefix + "sort", sort);
        setParamArraySimple(map, prefix + "recordIds", recordIds);
        setParamSimple(map, prefix + "viewId", viewId);
        setParamArraySimple(map, prefix + "fields", fields);
        setParamSimple(map, prefix + "filterByFormula", filterByFormula);
        setParamSimple(map, prefix + "cellFormat", cellFormat);
        if (fieldKey != null) {
            setParamSimple(map, prefix + "fieldKey", fieldKey.name().toLowerCase());
        }
        setParamSimple(map, prefix + "pageNum", pageNum);
        setParamSimple(map, prefix + "pageSize", pageSize);
        setParamSimple(map, prefix + "maxRecords", maxRecords);
    }
}
