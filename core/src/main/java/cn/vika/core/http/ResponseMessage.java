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
            return ((HttpStatus) this.status).code();
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
            builder.append(((HttpStatus) this.status).getStatusText());
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
