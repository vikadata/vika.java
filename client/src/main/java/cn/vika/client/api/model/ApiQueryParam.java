/*
 * Copyright (c) 2021 vikadata, https://vika.cn <support@vikadata.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.vika.client.api.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.vika.client.api.util.UrlEncoder;

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
        return withParam(FILTER_BY_FORMULA, UrlEncoder.encodeURIComponent(formula));
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
