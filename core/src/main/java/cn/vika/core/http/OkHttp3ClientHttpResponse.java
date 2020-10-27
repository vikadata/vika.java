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

import cn.vika.core.utils.Assert;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shawn Deng
 * @date 2020-10-27 12:10:43
 */
public class OkHttp3ClientHttpResponse implements ClientHttpResponse {

    private final Response response;

    private volatile HttpHeader headers;


    public OkHttp3ClientHttpResponse(Response response) {
        Assert.notNull(response, "Response must not be null");
        this.response = response;
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return this.response.code();
    }

    @Override
    public String getStatusText() throws IOException {
        return this.response.message();
    }

    @Override
    public HttpHeader getHeaders() {
        HttpHeader headers = this.headers;
        if (headers == null) {
            headers = new HttpHeader();
            for (String headerName : this.response.headers().names()) {
                for (String headerValue : this.response.headers(headerName)) {
                    headers.put(headerName, headerValue);
                }
            }
            this.headers = headers;
        }
        return headers;
    }

    @Override
    public InputStream getBody() throws IOException {
        ResponseBody body = this.response.body();
        return (body != null ? body.byteStream() : new ByteArrayInputStream(new byte[0]));
    }

    @Override
    public void close() {
        ResponseBody body = this.response.body();
        if (body != null) {
            body.close();
        }
    }
}
