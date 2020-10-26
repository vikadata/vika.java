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

import okhttp3.internal.http.HttpMethod;

import java.io.IOException;
import java.net.URI;

/**
 * * Base class for {@link cn.vika.core.http.VikaOpenApiClient}
 *
 * @author Shawn Deng
 * @date 2020-10-26 19:06:40
 */
public class BaseClient {

    /**
     * Create a new {@link HttpRequestClient}
     *
     * @param url    the URL to connect to
     * @param method the HTTP method to execute (GET, POST, etc)
     * @return the created request
     * @throws IOException in case of I/O errors
     */
    protected HttpRequestClient createRequest(URI url, HttpMethod method) throws IOException {
        return null;
    }
}
