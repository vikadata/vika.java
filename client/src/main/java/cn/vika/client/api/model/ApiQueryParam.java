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

package cn.vika.client.api.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.vika.client.datasheet.model.CellFormat;
import cn.vika.client.datasheet.model.FieldKey;
import cn.vika.client.datasheet.model.Order;

import static cn.vika.client.api.Constants.CELL_FORMAT;
import static cn.vika.client.api.Constants.FIELDS;
import static cn.vika.client.api.Constants.FIELD_KEY;
import static cn.vika.client.api.Constants.FILTER_BY_FORMULA;
import static cn.vika.client.api.Constants.MAX_RECORDS;
import static cn.vika.client.api.Constants.PAGE_NUM;
import static cn.vika.client.api.Constants.PAGE_SIZE;
import static cn.vika.client.api.Constants.RECORD_IDS;
import static cn.vika.client.api.Constants.SORT;
import static cn.vika.client.api.Constants.VIEW_ID;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-05 20:20:49
 */
public class ApiQueryParam extends HashMap<String, List<String>> {

    private Map<String, Order> orderByMap;

    public static ApiQueryParam EMPTY = new ApiQueryParam();

    public ApiQueryParam() {
        super(16);
        orderByMap = new LinkedHashMap<>(16);
    }

    public ApiQueryParam(HashMap<String, List<String>> map) {
        super(map);
        orderByMap = new LinkedHashMap<>(16);
    }

    public ApiQueryParam(int page, int pageSize) {
        super(16);
        orderByMap = new LinkedHashMap<>(16);
        withParam(PAGE_NUM, Integer.toString(page));
        withParam(PAGE_SIZE, Integer.toString(pageSize));
    }

    public ApiQueryParam withSort(String fieldName, Order order) {
        orderByMap.put(fieldName, order);
        return this;
    }

    public ApiQueryParam withView(String viewId) {
        return withParam(VIEW_ID, viewId);
    }

    public ApiQueryParam withRecordIds(List<String> recordIds) {
        return withParam(RECORD_IDS, recordIds);
    }

    public ApiQueryParam withFields(List<String> fieldNames) {
        return withParam(FIELDS, fieldNames);
    }

    public ApiQueryParam withFilter(String formula) {
        return withParam(FILTER_BY_FORMULA, formula);
    }

    public ApiQueryParam withMaxRecords(int maxRecords) {
        return withParam(MAX_RECORDS, Integer.toString(maxRecords));
    }

    public ApiQueryParam withCellFormat(CellFormat cellFormat) {
        return withParam(CELL_FORMAT, cellFormat.name().toLowerCase());
    }

    public ApiQueryParam withFieldKey(FieldKey fieldKey) {
        return withParam(FIELD_KEY, fieldKey.name().toLowerCase());
    }

    public ApiQueryParam withParam(String key, String value) {
        if (value == null) {
            return this;
        }
        put(key, Collections.singletonList(value));
        return this;
    }

    public ApiQueryParam withParam(String key, List<String> value) {
        if (value == null) {
            return this;
        }
        put(key, value);
        return this;
    }

    public Map<String, String> toMap() {
        Map<String, String> queryMap = new HashMap<>();
        for (Entry<String, List<String>> entry : this.entrySet()) {
            if (entry.getValue().size() == 1) {
                queryMap.put(entry.getKey(), entry.getValue().get(0));
            }
            else if (entry.getValue().size() > 1) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    queryMap.put(entry.getKey() + "." + i, entry.getValue().get(i));
                }
            }
        }
        if (!orderByMap.isEmpty()) {
            int i = 0;
            for (Entry<String, Order> entry : orderByMap.entrySet()) {
                queryMap.put(SORT + "." + i + ".field", entry.getKey());
                queryMap.put(SORT + "." + i + ".order", entry.getValue().name().toLowerCase());
                i++;
            }
        }
        return queryMap;
    }
}
