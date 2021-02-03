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

import java.nio.charset.StandardCharsets;

import cn.vika.core.utils.StringUtil;

/**
 * Http Media type.
 *
 * @author Shawn Deng
 * @date 2020-11-07 00:32:45
 */
public final class HttpMediaType {

    public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    public static final String APPLICATION_JSON = "application/json";

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static final String TEXT_HTML = "text/html";

    public static final String TEXT_PLAIN = "text/plain";

    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

    private HttpMediaType(String type, String charset) {
        this.type = type;
        this.charset = charset;
    }

    /**
     * content type.
     */
    private final String type;

    /**
     * content type charset.
     */
    private final String charset;

    public static HttpMediaType valueOf(String contentType) {
        if (StringUtil.isEmpty(contentType)) {
            throw new IllegalArgumentException("MediaType must not be empty");
        }
        String[] values = contentType.split(";");
        String charset = StandardCharsets.UTF_8.name();
        for (String value : values) {
            if (value.startsWith("charset=")) {
                charset = value.substring("charset=".length());
            }
        }
        return new HttpMediaType(values[0], charset);
    }

    public static HttpMediaType valueOf(String contentType, String charset) {
        if (StringUtil.isEmpty(contentType)) {
            throw new IllegalArgumentException("HttpMediaType must not be empty");
        }
        String[] values = contentType.split(";");
        return new HttpMediaType(values[0], StringUtil.isEmpty(charset) ? StandardCharsets.UTF_8.name() : charset);
    }

    public String getType() {
        return type;
    }

    public String getCharset() {
        return charset;
    }
}
