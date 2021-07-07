/*
 * Copyright (c) 2021 vikadata, https://vika.cn <support@vikadata.com>
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

package cn.vika.core.exception;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import cn.vika.core.http.HttpHeader;

/**
 * response exception contain response body content
 * @author Shawn Deng
 * @date 2021-02-06 14:27:30
 */
public class HttpResponseException extends HttpClientException {

    private static final long serialVersionUID = -7458140335323974708L;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final int rawStatusCode;

    private final String statusText;

    private final HttpHeader responseHeaders;

    private final byte[] responseBody;

    public HttpResponseException(String message, int rawStatusCode, String statusText, HttpHeader responseHeaders, byte[] responseBody) {
        super(message);
        this.rawStatusCode = rawStatusCode;
        this.statusText = statusText;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    /**
     * Return the raw HTTP status code value.
     */
    public int getRawStatusCode() {
        return this.rawStatusCode;
    }

    /**
     * Return the HTTP status text.
     */
    public String getStatusText() {
        return this.statusText;
    }

    /**
     * Return the HTTP response headers.
     */
    public HttpHeader getResponseHeaders() {
        return this.responseHeaders;
    }

    /**
     * Return the response body as a byte array.
     */
    public byte[] getResponseBodyAsByteArray() {
        return this.responseBody;
    }

    /**
     * Return the response body converted to String. The charset used is that
     * of the response "Content-Type" or otherwise {@code "UTF-8"}.
     */
    public String getResponseBodyAsString() {
        return new String(this.responseBody, DEFAULT_CHARSET);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("code:").append(rawStatusCode).append(",");
        sb.append("statusText:").append(statusText).append(",");
        responseHeaders.forEach((k, v) -> sb.append(k).append(":").append(v.toString()).append(","));
        sb.append("body:").append(new String(responseBody, StandardCharsets.UTF_8)).append(",");
        return sb.toString();
    }
}
