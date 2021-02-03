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

package cn.vika.core.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.vika.core.utils.AssertUtil;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Okhttp Response
 * @author Shawn Deng
 * @date 2020-10-27 12:10:43
 */
public class OkHttpClientHttpResponse implements ClientHttpResponse {

    private final Response response;

    private volatile HttpHeader headers;

    public OkHttpClientHttpResponse(Response response) {
        AssertUtil.notNull(response, "Response must not be null");
        this.response = response;
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return this.response.code();
    }

    @Override
    public String getStatusText() throws IOException {
        return this.response.message();
    }

    @Override
    public HttpHeader getHeaders() {
        HttpHeader headers = this.headers;
        if (headers == null) {
            headers = new HttpHeader();
            for (String headerName : this.response.headers().names()) {
                for (String headerValue : this.response.headers(headerName)) {
                    headers.add(headerName, headerValue);
                }
            }
            this.headers = headers;
        }
        return headers;
    }

    @Override
    public InputStream getBody() throws IOException {
        ResponseBody body = this.response.body();
        return (body != null ? body.byteStream() : new ByteArrayInputStream(new byte[0]));
    }

    @Override
    public void close() {
        ResponseBody body = this.response.body();
        if (body != null) {
            body.close();
        }
    }
}
