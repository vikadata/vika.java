/*
 * MIT License
 *
 * Copyright (c) 2020 vika
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

package cn.vika.core.http;

import com.sun.tools.javac.util.Assert;
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
     * @param client the client to use
     */
    public OkHttpRequestBuildFactory(OkHttpClient client) {
        Assert.checkNonNull(client, "OkHttpClient must not be null");
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
