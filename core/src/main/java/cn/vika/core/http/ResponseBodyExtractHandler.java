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

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Type;

import cn.vika.core.utils.IoUtil;
import cn.vika.core.utils.JacksonConverter;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-24 16:00:36
 */
public class ResponseBodyExtractHandler<T> implements ResponseHandler<T>{

    private final Type responseType;

    // never close
    private PushbackInputStream pushbackInputStream;

    public ResponseBodyExtractHandler(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public T extractData(ClientHttpResponse response, ResponseBodyHandler handler) throws IOException {
        if (!hasBody(response) || hasEmptyBody(response)) {
            return null;
        }
        InputStream body = getBody(response);
        byte[] content = IoUtil.copyToByteArray(body);
        if (handler != null) {
            handler.handleBody(content);
        }
        return JacksonConverter.toGenericBean(content, responseType);
    }

    private boolean hasBody(ClientHttpResponse response) throws IOException {
        // whether is ok response
        HttpStatus status = HttpStatus.resolve(response.getRawStatusCode());
        if (status != null && (status.is1xxInformational() || status == HttpStatus.NO_CONTENT || status == HttpStatus.NOT_MODIFIED)) {
            return false;
        }
        // whether has content
        return response.getHeaders().getContentLength() != 0;
    }

    public boolean hasEmptyBody(ClientHttpResponse response) throws IOException {
        InputStream body = response.getBody();
        // Per contract body shouldn't be null, but check anyway..
        if (body == null) {
            return true;
        }
        if (body.markSupported()) {
            body.mark(1);
            if (body.read() == -1) {
                return true;
            }
            else {
                body.reset();
                return false;
            }
        }
        else {
            this.pushbackInputStream = new PushbackInputStream(body);
            int b = this.pushbackInputStream.read();
            if (b == -1) {
                return true;
            }
            else {
                this.pushbackInputStream.unread(b);
                return false;
            }
        }
    }

    private InputStream getBody(ClientHttpResponse response) throws IOException {
        return (this.pushbackInputStream != null ? this.pushbackInputStream : response.getBody());
    }
}
