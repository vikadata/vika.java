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

import java.net.MalformedURLException;
import java.net.URI;

import cn.vika.core.utils.AssertUtil;
import cn.vika.core.utils.StringUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * implementation base Request Factory
 *
 * @author Shawn Deng
 * @date 2020-10-27 12:08:00
 */
public class OkHttpClientHttpRequestFactory implements ClientHttpRequestFactory {

    private final OkHttpClient client;

    public OkHttpClientHttpRequestFactory() {
        this.client = new OkHttpClient();
    }

    public OkHttpClientHttpRequestFactory(OkHttpClient client) {
        AssertUtil.notNull(client, "OkHttpClient must not be null");
        this.client = client;
    }

    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) {
        return new OkHttpClientRequest(this.client, uri, httpMethod);
    }

    /**
     * build request instance by necessary input param
     *
     * @param uri        connect url
     * @param httpMethod request method way
     * @param headers    request header
     * @param content    request content
     * @return Okhttp Request instance
     * @throws MalformedURLException throw if uri error
     */
    static Request buildRequest(URI uri, HttpMethod httpMethod, HttpHeader headers, byte[] content) throws MalformedURLException {
        // get request content type
        MediaType contentType = getContentType(headers);
        // create Request Body by OkHttp
        RequestBody body = content.length > 0 ||
            okhttp3.internal.http.HttpMethod.requiresRequestBody(httpMethod.name()) ?
            RequestBody.create(content, contentType) : null;
        // Create Okhttp Request
        Request.Builder builder = new Request.Builder().url(uri.toURL()).method(httpMethod.name(), body);
        // Add Header
        headers.forEach((headerName, headerValues) -> {
            for (String headerValue : headerValues) {
                builder.addHeader(headerName, headerValue);
            }
        });
        return builder.build();
    }

    private static MediaType getContentType(HttpHeader header) {
        String rawContentType = header.getFirstValue(HttpHeader.CONTENT_TYPE);
        return StringUtil.hasText(rawContentType) ? okhttp3.MediaType.parse(rawContentType) : null;
    }
}
