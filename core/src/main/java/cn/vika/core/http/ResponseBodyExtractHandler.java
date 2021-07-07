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
