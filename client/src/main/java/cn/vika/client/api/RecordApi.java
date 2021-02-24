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

package cn.vika.client.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.ApiQueryParam;
import cn.vika.client.api.model.CreateRecordRequest;
import cn.vika.client.api.model.HttpResult;
import cn.vika.client.api.model.Pager;
import cn.vika.client.api.model.PagerInfo;
import cn.vika.client.api.model.Record;
import cn.vika.client.api.model.Records;
import cn.vika.client.api.model.UpdateRecordRequest;
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

    public Stream<Record> getRecordsAsStream(String datasheetId) throws ApiException {
        return getRecords(datasheetId, getDefaultPerPage()).stream();
    }

    public List<Record> getRecords(String datasheetId, int page, int itemsPerPage) throws ApiException {
        if (page < 0 || itemsPerPage < 0) {
            throw new ApiException("page or itemsPerPage don't set right");
        }
        ApiQueryParam queryParam = new ApiQueryParam(page, itemsPerPage);
        Map<String, String> uriVariables = queryParam.toMap();
        GenericTypeReference<HttpResult<PagerInfo<Record>>> reference = new GenericTypeReference<HttpResult<PagerInfo<Record>>>() {};
        String uri = String.format(PATH, datasheetId) + MapUtil.extractKeyToVariables(uriVariables);
        HttpResult<PagerInfo<Record>> result = getDefaultHttpClient().get(uri, new HttpHeader(), reference, uriVariables);
        return result.getData().getRecords();
    }

    public Pager<Record> getRecords(String datasheetId) throws ApiException {
        return new Pager<>(this, String.format(PATH, datasheetId), getDefaultPerPage(), Record.class);
    }

    public Pager<Record> getRecords(String datasheetId, int itemsPerPage) throws ApiException {
        return new Pager<>(this, String.format(PATH, datasheetId), itemsPerPage, Record.class);
    }

    public Pager<Record> getRecords(String datasheetId, ApiQueryParam queryParam) throws ApiException {
        return new Pager<>(this, String.format(PATH, datasheetId), queryParam, Record.class);
    }

    public List<Record> addRecords(String datasheetId, CreateRecordRequest record) throws ApiException {
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
        HttpResult<Records> result = getDefaultHttpClient().post(String.format(PATH, datasheetId), new HttpHeader(), record, new GenericTypeReference<HttpResult<Records>>() {});
        return result.getData().getRecords();
    }

    public List<Record> updateRecords(String datasheetId, UpdateRecordRequest record) throws ApiException {
        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must be not null");
        }
        if (record == null) {
            throw new RuntimeException("Record instance cannot be null.");
        }
        HttpResult<Records> result = getDefaultHttpClient().patch(String.format(PATH, datasheetId), new HttpHeader(), record, new GenericTypeReference<HttpResult<Records>>() {});
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
        getDefaultHttpClient().delete(uri, new HttpHeader(), Void.class, uriVariables);
    }
}
