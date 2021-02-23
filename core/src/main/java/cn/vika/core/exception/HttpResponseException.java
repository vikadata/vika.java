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
}
