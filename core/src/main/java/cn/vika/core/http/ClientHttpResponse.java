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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shawn Deng
 * @date 2020-10-27 11:57:08
 */
public interface ClientHttpResponse extends Closeable {

    /**
     * Get the HTTP status code of the response.
     *
     * @return the HTTP status as an integer value
     * @throws IOException in case of I/O errors
     */
    int getRawStatusCode() throws IOException;

    /**
     * Get the HTTP status text of the response.
     *
     * @return the HTTP status text
     * @throws IOException in case of I/O errors
     */
    String getStatusText() throws IOException;

    /**
     * Return the headers of this message.
     *
     * @return a corresponding HttpHeaders object (never {@code null})
     */
    HttpHeader getHeaders();

    /**
     * Return the body of the message as an input stream.
     *
     * @return the input stream body (never {@code null})
     * @throws IOException in case of I/O errors
     */
    InputStream getBody() throws IOException;

    /**
     * Close this response, freeing any resources created.
     */
    @Override
    void close();
}
