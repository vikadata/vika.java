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

import java.io.IOException;
import java.net.URI;

/**
 * Strategy interface used by the {@link DefaultHttpClient} to determine
 * whether a particular response has an error or not.
 *
 * @author Shawn Deng
 * @date 2020-10-27 19:06:38
 */
public interface ResponseErrorHandler {

    /**
     * Indicate whether the given response has any errors.
     * <p>Implementations will typically inspect the
     * {@link ClientHttpResponse#getRawStatusCode() int} of the response.
     *
     * @param response the response to inspect
     * @return {@code true} if the response indicates an error; {@code false} otherwise
     * @throws IOException in case of I/O errors
     */
    boolean hasError(ClientHttpResponse response) throws IOException;

    /**
     * Handle the error in the given response.
     * <p>This method is only called when {@link #hasError(ClientHttpResponse)}
     * has returned {@code true}.
     *
     * @param response the response with the error
     * @throws IOException in case of I/O errors
     */
    void handleError(ClientHttpResponse response) throws IOException;

    /**
     * Alternative to {@link #handleError(ClientHttpResponse)} with extra
     * information providing access to the request URL and HTTP method.
     *
     * @param url      the request URL
     * @param method   the HTTP method
     * @param response the response with the error
     * @throws IOException in case of I/O errors
     */
    default void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        handleError(response);
    }
}
