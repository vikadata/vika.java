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

package cn.vika.client.api;

import java.io.File;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.Attachment;
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

    public Attachment upload(String datasheetId, ResourceLoader loader) throws ApiException {
        HttpHeader httpHeader = new HttpHeader();
        httpHeader.setContentType(HttpMediaType.MULTIPART_FORM_DATA);
        FormDataMap formDataMap = new FormDataMap();
        formDataMap.put("file", loader);
        HttpResult<Attachment> result = getDefaultHttpClient().post(String.format(PATH, datasheetId), httpHeader, formDataMap, new GenericTypeReference<HttpResult<Attachment>>() {});
        return result.getData();
    }

    public Attachment upload(String datasheetId, File file) throws ApiException {
        AssertUtil.notNull(file, "file can not be null");
        FormDataMap formDataMap = new FormDataMap();
        formDataMap.put("file", file);
        return upload(datasheetId, formDataMap);
    }

    public Attachment upload(String datasheetId, FormDataMap formData) throws ApiException {
        HttpHeader httpHeader = new HttpHeader();
        httpHeader.setContentType(HttpMediaType.MULTIPART_FORM_DATA);
        HttpResult<Attachment> result = getDefaultHttpClient().post(String.format(PATH, datasheetId), httpHeader, formData, new GenericTypeReference<HttpResult<Attachment>>() {});
        return result.getData();
    }
}
