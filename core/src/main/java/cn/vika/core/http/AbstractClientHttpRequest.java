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


import cn.vika.core.utils.AssertUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Abstract Http Client Object
 *
 * @author Shawn Deng
 * @date 2020-10-26 17:39:20
 */
public abstract class AbstractClientHttpRequest implements ClientHttpRequest {

    private final HttpHeader headers = new HttpHeader();

    private ByteArrayOutputStream bufferedOutput = new ByteArrayOutputStream(1024);

    private boolean executed = false;

    @Override
    public HttpHeader getHeaders() {
        return this.headers;
    }

    @Override
    public OutputStream getBody() throws IOException {
        return this.bufferedOutput;
    }

    @Override
    public ClientHttpResponse execute() throws IOException {
        // asset where request is executing
        assertNotExecuted();
        byte[] bytes = bufferedOutput.toByteArray();
        // letter than 0 mean have no content
        if (headers.getContentLength() < 0) {
            headers.setContentLength(bytes.length);
        }
        ClientHttpResponse result = this.executeInternal(this.headers, bytes);
        this.bufferedOutput = new ByteArrayOutputStream(0);
        this.executed = true;
        return result;
    }

    /**
     * Assert that this request has not been {@linkplain #execute() executed} yet.
     *
     * @throws IllegalStateException if this request has been executed
     */
    protected void assertNotExecuted() {
        AssertUtil.state(!this.executed, "Client already executed");
    }

    /**
     * Abstract template method for the HTTP request.
     *
     * @param headers the HTTP headers
     * @param content request body content
     * @return the response object for the executed request
     * @throws IOException in case of I/O errors
     */
    protected abstract ClientHttpResponse executeInternal(HttpHeader headers, byte[] content) throws IOException;
}
