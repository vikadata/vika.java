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

import cn.vika.core.utils.AssertUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.http.HttpMethod;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * uses <a href="https://square.github.io/okhttp/">OkHttp</a> to create requests.
 *
 * @author Shawn Deng
 * @date 2020-10-26 18:38:11
 */
public class OkHttpRequestBuildFactory {

    private OkHttpClient client;

    private final boolean defaultClient;

    /**
     * Create a factory with a default {@link OkHttpClient} instance.
     */
    public OkHttpRequestBuildFactory() {
        this.client = new OkHttpClient();
        this.defaultClient = true;
    }

    /**
     * Create a factory with the given {@link OkHttpClient} instance.
     *
     * @param client the client to use
     */
    public OkHttpRequestBuildFactory(OkHttpClient client) {
        AssertUtil.notNull(client, "OkHttpClient must not be null");
        this.client = client;
        this.defaultClient = false;
    }

    /**
     * Set the underlying read timeout in milliseconds.
     * A value of 0 specifies an infinite timeout.
     */
    public void setReadTimeout(int readTimeout) {
        this.client = this.client.newBuilder()
            .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
            .build();
    }

    /**
     * Set the underlying write timeout in milliseconds.
     * A value of 0 specifies an infinite timeout.
     */
    public void setWriteTimeout(int writeTimeout) {
        this.client = this.client.newBuilder()
            .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
            .build();
    }

    /**
     * Set the underlying connect timeout in milliseconds.
     * A value of 0 specifies an infinite timeout.
     */
    public void setConnectTimeout(int connectTimeout) {
        this.client = this.client.newBuilder()
            .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
            .build();
    }

    static Request buildRequest(HttpHeader headers, byte[] content, URI uri, HttpMethod method) throws MalformedURLException {

        return null;
    }
}
