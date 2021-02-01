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

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.net.URI;

/**
 * {@link AbstractClientHttpRequest} implementation based on OkHttp.
 *
 * @author Shawn Deng
 * @date 2020-10-26 18:02:40
 */
public class OkHttpClientRequest extends AbstractClientHttpRequest {

    private final OkHttpClient client;

    private final URI uri;

    private final HttpMethod method;

    public OkHttpClientRequest(OkHttpClient client, URI uri, HttpMethod method) {
        this.client = client;
        this.uri = uri;
        this.method = method;
    }

    @Override
    protected ClientHttpResponse executeInternal(HttpHeader headers, byte[] content) throws IOException {
        // Create Okhttp Request
        Request request = OkHttpClientHttpRequestFactory.buildRequest(this.uri, this.method, headers, content);
        return new OkHttpClientHttpResponse(this.client.newCall(request).execute());
    }
}
