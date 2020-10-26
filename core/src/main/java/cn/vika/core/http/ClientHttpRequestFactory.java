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
 * Factory for {@link HttpRequestClient} objects.
 * Requests are created by the {@link #createRequest(URI, HttpMethod)} method.
 *
 * @author Shawn Deng
 * @date 2020-10-26 19:18:02
 */
public interface ClientHttpRequestFactory {

    /**
     * Create a new {@link HttpRequestClient} for the specified URI and HTTP method.
     * <p>The returned request can be written to, and then executed by calling
     * {@link HttpRequestClient#execute()}.
     *
     * @param uri        the URI to create a request for
     * @param httpMethod the HTTP method to execute
     * @return the created request
     * @throws IOException in case of I/O errors
     */
    HttpRequestClient createRequest(URI uri, HttpMethod httpMethod) throws IOException;
}
