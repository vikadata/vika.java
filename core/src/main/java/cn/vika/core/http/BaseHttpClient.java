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

import cn.vika.core.utils.Assert;

import java.io.IOException;
import java.net.URI;

/**
 * @author Shawn Deng
 * @date 2020-10-27 17:59:37
 */
public abstract class BaseHttpClient {

    /**
     * Default implementation OkhttpClient
     */
    private ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();

    /**
     * Set the request factory that this accessor uses for obtaining client request handles.
     * <p>The default is a {@link OkHttp3ClientHttpRequestFactory} based on the OKHttp libraries.
     *
     * @param requestFactory http request client factory
     */
    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        Assert.notNull(requestFactory, "HttpRequestFactory must not be null");
        this.requestFactory = requestFactory;
    }

    /**
     * Return the request factory that this accessor uses for obtaining client request handles.
     */
    public ClientHttpRequestFactory getRequestFactory() {
        return this.requestFactory;
    }

    /**
     * Create a new {@link ClientHttpRequest} via this template's {@link ClientHttpRequestFactory}.
     * @param url the URL to connect to
     * @param method the HTTP method to execute (GET, POST, etc)
     * @return the created request
     * @throws IOException in case of I/O errors
     * @see #getRequestFactory()
     * @see ClientHttpRequestFactory#createRequest(URI, HttpMethod)
     */
    protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
        ClientHttpRequest request = getRequestFactory().createRequest(url, method);
        return request;
    }
}
