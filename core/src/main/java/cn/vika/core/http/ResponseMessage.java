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

import cn.vika.core.utils.ObjectUtil;

/**
 * Http Response Message
 *
 * @author Shawn Deng
 * @date 2020-11-12 11:08:52
 */
public class ResponseMessage<T> {

    private final Object status;

    private final HttpHeader headers;

    private final T body;

    public ResponseMessage(Object status, HttpHeader headers, T body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    /**
     * Return the HTTP status code of the response.
     *
     * @return the HTTP status as an HttpStatus enum entry
     */
    public HttpStatus getStatusCode() {
        if (this.status instanceof HttpStatus) {
            return (HttpStatus) this.status;
        } else {
            return HttpStatus.valueOf((Integer) this.status);
        }
    }

    /**
     * Return the HTTP status code of the response.
     *
     * @return the HTTP status as an int value
     * @since 4.3
     */
    public int getStatusCodeValue() {
        if (this.status instanceof HttpStatus) {
            return ((HttpStatus) this.status).value();
        } else {
            return (Integer) this.status;
        }
    }

    /**
     * Returns the headers of this entity.
     */
    public HttpHeader getHeaders() {
        return this.headers;
    }

    public T getBody() {
        return this.body;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!super.equals(other)) {
            return false;
        }
        ResponseMessage<?> otherEntity = (ResponseMessage<?>) other;
        return ObjectUtil.nullSafeEquals(this.status, otherEntity.status);
    }

    @Override
    public int hashCode() {
        return (29 * super.hashCode() + ObjectUtil.nullSafeHashCode(this.status));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<");
        builder.append(this.status.toString());
        if (this.status instanceof HttpStatus) {
            builder.append(' ');
            builder.append(((HttpStatus) this.status).getReasonPhrase());
        }
        builder.append(',');
        T body = getBody();
        HttpHeader headers = getHeaders();
        if (body != null) {
            builder.append(body);
            builder.append(',');
        }
        builder.append(headers);
        builder.append('>');
        return builder.toString();
    }
}
