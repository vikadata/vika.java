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
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.ApiQueryParam;
import cn.vika.client.api.model.HttpResult;
import cn.vika.client.api.model.PageDetail;
import cn.vika.client.api.model.Pager;
import cn.vika.client.api.models.CreateRecordRequest;
import cn.vika.client.api.models.RecordResult;
import cn.vika.client.api.models.RecordResultList;
import cn.vika.client.api.models.UpdateRecordRequest;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.utils.MapUtil;
import cn.vika.core.utils.StringUtil;


/**
 * the api for operate records
 *
 * @author Zoe Zheng
 * @date 2020/12/15 5:14 下午
 */
public class RecordApi extends AbstractApi {

    private static final String PATH = "/datasheets/%s/records";

    public RecordApi(ApiHttpClient apiHttpClient) {
        super(apiHttpClient);
    }

    public List<RecordResult> getRecords(String datasheetId) throws ApiException {
        return getRecords(datasheetId, getDefaultPerPage()).all();
    }

    public Stream<RecordResult> getRecordsAsStream(String datasheetId) throws ApiException {
        return getRecords(datasheetId, getDefaultPerPage()).stream();
    }

    public List<RecordResult> getRecords(String datasheetId, int page, int itemsPerPage) throws ApiException {
        if (page < 0 || itemsPerPage < 0) {
            throw new ApiException("page or itemsPerPage don't set right");
        }
        ApiQueryParam queryParam = new ApiQueryParam(page, itemsPerPage);
        Map<String, String> uriVariables = queryParam.toMap();
        GenericTypeReference<HttpResult<PageDetail<RecordResult>>> reference = new GenericTypeReference<HttpResult<PageDetail<RecordResult>>>() {};
        String uri = String.format(PATH, datasheetId) + MapUtil.extractKeyToVariables(uriVariables);
        HttpResult<PageDetail<RecordResult>> result = getDefaultHttpClient().get(uri, HttpHeader.EMPTY, reference, uriVariables);
        return result.getData().getRecords();
    }

    public Pager<RecordResult> getRecords(String datasheetId, int itemsPerPage) throws ApiException {
        return new Pager<>(this, String.format(PATH, datasheetId), itemsPerPage, RecordResult.class);
    }

    public Pager<RecordResult> getRecords(String datasheetId, ApiQueryParam queryParam) throws ApiException {
        return new Pager<>(this, String.format(PATH, datasheetId), queryParam, RecordResult.class);
    }

    public List<RecordResult> addRecords(String datasheetId, CreateRecordRequest record) throws ApiException {
        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must be not null");
        }
        if (record == null) {
            return null;
        }
        if (record.getRecords() == null) {
            return null;
        }
        if (record.getRecords().isEmpty()) {
            return null;
        }
        if (record.getRecords().size() > 10) {
            throw new ApiException("record only can add 10 every request");
        }
        HttpResult<RecordResultList> result = getDefaultHttpClient().post(String.format(PATH, datasheetId), HttpHeader.EMPTY, record, new GenericTypeReference<HttpResult<RecordResultList>>() {});
        return result.getData().getRecords();
    }

    public List<RecordResult> updateRecords(String datasheetId, UpdateRecordRequest record) throws ApiException {
        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must be not null");
        }
        if (record == null) {
            throw new RuntimeException("Record instance cannot be null.");
        }
        HttpResult<RecordResultList> result = getDefaultHttpClient().patch(String.format(PATH, datasheetId), HttpHeader.EMPTY, record, new GenericTypeReference<HttpResult<RecordResultList>>() {});
        return result.getData().getRecords();
    }

    public void deleteRecord(String datasheetId, String recordId) throws ApiException {
        deleteRecords(datasheetId, Collections.singletonList(recordId));
    }

    public void deleteRecords(String datasheetId, List<String> recordIds) throws ApiException {
        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must be not null");
        }
        if (recordIds == null) {
            throw new ApiException("record id array must be not null");
        }
        if (recordIds.isEmpty()) {
            throw new ApiException("record id array must be not empty");
        }
        Map<String, String> uriVariables = MapUtil.listToUriVariableMap("recordIds", recordIds);
        String uri = String.format(PATH, datasheetId) + MapUtil.extractKeyToVariables(uriVariables);
        getDefaultHttpClient().delete(uri, HttpHeader.EMPTY, Void.class, uriVariables);
    }
}
