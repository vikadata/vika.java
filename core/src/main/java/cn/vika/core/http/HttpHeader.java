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

import java.util.LinkedHashMap;

import static cn.vika.core.constants.HttpHeaderConstants.CONTENT_LENGTH;

/**
 * A data structure representing HTTP request or response headers,
 * mapping String header names to a list of String values,
 * also offering accessors for common application-level data types.
 *
 * @author Shawn Deng
 * @date 2020-10-26 18:22:23
 */
public class HttpHeader extends LinkedHashMap<String, String> {

    public HttpHeader() {
        super(8);
    }

    /**
     * Return the length of the body in bytes, as specified by the {@code Content-Length} header.
     *
     * @return Returns -1 when the content-length is unknown
     */
    public long getContentLength() {
        String value = get(CONTENT_LENGTH);
        return (value != null ? Long.parseLong(value) : -1);
    }

    /**
     * Set the length of the body in bytes, as specified by the {@code Content-Length} header.
     */
    public void setContentLength(long contentLength) {
        put(CONTENT_LENGTH, Long.toString(contentLength));
    }
}
