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

import java.io.File;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.AttachmentInfo;
import cn.vika.client.api.model.HttpResult;
import cn.vika.core.http.FormDataMap;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.http.HttpMediaType;
import cn.vika.core.http.ResourceLoader;
import cn.vika.core.utils.AssertUtil;

/**
 * api method for datasheet attachment field operation
 *
 * @author Zoe Zheng
 * @date 2020-12-17 16:25:42
 */
public class AttachmentApi extends AbstractApi {

    private static final String PATH = "/datasheets/%s/attachments";

    public AttachmentApi(ApiHttpClient apiHttpClient) {
        super(apiHttpClient);
    }

    public AttachmentInfo upload(String datasheetId, ResourceLoader loader) throws ApiException {
        HttpHeader httpHeader = HttpHeader.EMPTY;
        httpHeader.setContentType(HttpMediaType.MULTIPART_FORM_DATA);
        FormDataMap formDataMap = new FormDataMap();
        formDataMap.put("file", loader);
        HttpResult<AttachmentInfo> result = getDefaultHttpClient().post(String.format(PATH, datasheetId), httpHeader, formDataMap, new GenericTypeReference<HttpResult<AttachmentInfo>>() {});
        return result.getData();
    }

    public AttachmentInfo upload(String datasheetId, File file) throws ApiException {
        AssertUtil.notNull(file, "file can not be null");
        FormDataMap formDataMap = new FormDataMap();
        formDataMap.put("file", file);
        return upload(datasheetId, formDataMap);
    }

    public AttachmentInfo upload(String datasheetId, FormDataMap formData) throws ApiException {
        HttpHeader httpHeader = HttpHeader.EMPTY;
        httpHeader.setContentType(HttpMediaType.MULTIPART_FORM_DATA);
        HttpResult<AttachmentInfo> result = getDefaultHttpClient().post(String.format(PATH, datasheetId), httpHeader, formData, new GenericTypeReference<HttpResult<AttachmentInfo>>() {});
        return result.getData();
    }
}
