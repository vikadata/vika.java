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
}
