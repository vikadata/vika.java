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

package cn.vika.client.api.datasheet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.AbstractModel;
import cn.vika.client.api.model.ApiQueryParam;
import cn.vika.client.api.model.HttpResult;
import cn.vika.client.api.model.PageDetail;
import cn.vika.client.api.model.Pager;
import cn.vika.client.api.models.Record;
import cn.vika.client.api.models.RecordDetail;
import cn.vika.core.exception.JsonConvertException;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.utils.MapUtil;
import cn.vika.core.utils.StringUtil;

import static cn.vika.client.api.exception.ApiException.DEFAULT_CODE;


/**
 * the api for operate records
 *
 * @author Zoe Zheng
 * @date 2020/12/15 5:14 下午
 */
public class RecordApi extends AbstractApi {

    private static final String PATH = "/datasheets/%s/records";

    /**
     * datasheetId
     */
    public RecordApi(ApiHttpClient apiHttpClient) {
        super(apiHttpClient);
    }

    public List<RecordDetail> getRecords(String datasheetId) throws ApiException {
        return getRecords(datasheetId, getDefaultPerPage()).all();
    }

    public Stream<RecordDetail> getRecordsAsStream(String datasheetId) throws ApiException {
        return getRecords(datasheetId, getDefaultPerPage()).stream();
    }

    public List<RecordDetail> getRecords(String datasheetId, int page, int itemsPerPage) throws ApiException {
        ApiQueryParam queryParam = new ApiQueryParam(page, itemsPerPage);
        Map<String, String> uriVariables = queryParam.toMap();
        GenericTypeReference<HttpResult<PageDetail<RecordDetail>>> reference = new GenericTypeReference<HttpResult<PageDetail<RecordDetail>>>() {};
        String uri = String.format(PATH, datasheetId) + MapUtil.extractKeyToVariables(uriVariables);
        HttpResult<PageDetail<RecordDetail>> result = getDefaultHttpClient().get(uri, HttpHeader.EMPTY, reference, uriVariables);
        return result.getData().getRecords();
    }

    public Pager<RecordDetail> getRecords(String datasheetId, int itemsPerPage) throws ApiException {
        return new Pager<>(this, String.format(PATH, datasheetId), itemsPerPage, RecordDetail.class);
    }

    public Pager<RecordDetail> getRecords(String datasheetId, ApiQueryParam queryParam) throws ApiException {
        return new Pager<>(this, String.format(PATH, datasheetId), queryParam, RecordDetail.class);
    }

    public RecordDetail addRecords(String datasheetId, Record record) throws ApiException {
        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must be not null");
        }
        if (record == null) {
            return null;
        }
        return getDefaultHttpClient().post(String.format(PATH, datasheetId), HttpHeader.EMPTY, record, RecordDetail.class);
    }

    public RecordDetail updateRecord(String datasheetId, Record record) throws ApiException {
        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must be not null");
        }
        if (record == null) {
            throw new RuntimeException("Record instance cannot be null.");
        }
        return getDefaultHttpClient().patch(String.format(PATH, datasheetId), HttpHeader.EMPTY, record, RecordDetail.class);
    }

    public void deleteRecord(String datasheetId, String recordId) throws ApiException {
        deleteRecords(datasheetId, Collections.singletonList(recordId));
    }

    public void deleteRecords(String datasheetId, List<String> recordIds) throws ApiException {
        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must be not null");
        }
        if (recordIds != null && !recordIds.isEmpty()) {
            throw new ApiException("record id array must be not null or empty");
        }
        getDefaultHttpClient().delete(String.format(PATH, datasheetId), HttpHeader.EMPTY, Void.class);
    }

    @Deprecated
    public <T> T modifyRecords(String datasheetId, AbstractModel model, GenericTypeReference<HttpResult<T>> responseType)
            throws ApiException {
        HttpResult<T> result;
        try {
            result = getDefaultHttpClient().patch(basePath(datasheetId), HttpHeader.EMPTY, model, responseType);
            if (result.isSuccess()) {
                return result.getData();
            }
        }
        catch (JsonConvertException e) {
            throw new ApiException(DEFAULT_CODE, e.getMessage());
        }
        throw new ApiException(result.getCode(), result.getMessage());
    }

    @Deprecated
    public boolean deleteRecords(String datasheetId, AbstractModel model) throws ApiException {
        GenericTypeReference<HttpResult<Boolean>> responseType = new GenericTypeReference<HttpResult<Boolean>>() {};
        HashMap<String, String> params = new HashMap<>(10);
        model.toMap(params, "");
        HttpResult<Boolean> result = getDefaultHttpClient().delete(basePath(datasheetId) + model.toTemplateUri(params),
                HttpHeader.EMPTY, responseType, params);
        if (result.isSuccess()) {
            return result.isSuccess();
        }
        throw new ApiException(result.getCode(), result.getMessage());
    }

    @Override
    protected String basePath(String datasheetId) {
        return String.format(PATH, datasheetId);
    }

}
