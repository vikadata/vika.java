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

/**
 * Http Request Header Name Constants
 *
 * @author Shawn Deng
 * @date 2020-10-26 17:53:01
 */
public interface HttpHeaderConstants {

    /**
     * SDK Client Version
     */
    String CLIENT_VERSION_HEADER = "Client-Version";

    /**
     * The HTTP {@code User-Agent} header field name.
     */
    String USER_AGENT = "User-Agent";

    /**
     * The HTTP {@code Content-Type} header field name.
     */
    String CONTENT_TYPE = "Content-Type";

    /**
     * The HTTP {@code Content-Length} header field name.
     */
    String CONTENT_LENGTH = "Content-Length";

    /**
     * The HTTP {@code Content-Encoding} header field name.
     */
    String CONTENT_ENCODING = "Content-Encoding";

    /**
     * The HTTP {@code Accept} header field name.
     */
    String ACCEPT = "Accept";

    /**
     * The HTTP {@code Accept-Charset} header field name.
     */
    String ACCEPT_CHARSET = "Accept-Charset";

    /**
     * The HTTP {@code Accept-Encoding} header field name.
     */
    String ACCEPT_ENCODING = "Accept-Encoding";

    /**
     * The HTTP {@code Accept-Language} header field name
     */
    String ACCEPT_LANGUAGE = "Accept-Language";

    /**
     * The HTTP {@code Authorization} header field name.
     */
    String AUTHORIZATION = "Authorization";
}
