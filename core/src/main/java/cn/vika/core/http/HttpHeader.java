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

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static cn.vika.core.http.HttpHeaderConstants.CONTENT_LENGTH;
import static cn.vika.core.http.HttpHeaderConstants.CONTENT_TYPE;

/**
 * A data structure representing HTTP request or response headers,
 * mapping String header names to a list of String values,
 * also offering accessors for common application-level data types.
 *
 * @author Shawn Deng
 * @date 2020-10-26 18:22:23
 */
public class HttpHeader extends LinkedHashMap<String, List<String>> implements Serializable {

    private static final long serialVersionUID = -7828980872691946594L;

    /**
     * create a empty map
     */
    public static final HttpHeader EMPTY = HttpHeader.newInstance();

    /**
     * default construct method
     */
    public HttpHeader() {
        super(8);
    }

    public static HttpHeader newInstance() {
        return new HttpHeader();
    }

    /**
     * Return the first value for the given key.
     *
     * @param key the key
     * @return the first value for the specified key, or {@code null} if none
     */
    public String getFirstValue(String key) {
        return get(key) != null && get(key).size() > 0 ? this.get(key).get(0) : null;
    }

    /**
     * Return the length of the body in bytes, as specified by the {@code Content-Length} header.
     *
     * @return Returns -1 when the content-length is unknown
     */
    public long getContentLength() {
        String value = getFirstValue(CONTENT_LENGTH);
        return (value != null ? Long.parseLong(value) : -1);
    }

    /**
     * Set the length of the body in bytes, as specified by the {@code Content-Length} header.
     */
    public void setContentLength(long contentLength) {
        put(CONTENT_LENGTH, Collections.singletonList(Long.toString(contentLength)));
    }

    public void setContentType(String contentType) {
        put(CONTENT_TYPE, Collections.singletonList(contentType));
    }
}
