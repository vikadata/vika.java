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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import cn.vika.core.exception.ClientErrorException;
import cn.vika.core.exception.ServerErrorException;
import cn.vika.core.exception.UnknownHttpStatusCodeException;
import cn.vika.core.utils.IoUtil;
import cn.vika.core.utils.ObjectUtil;

/**
 * default response error handler
 * @author Shawn Deng
 * @date 2021-02-06 11:16:44
 */
public class DefaultHttpResponseErrorHandler implements HttpResponseErrorHandler {

    @Override
    public void handlerError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
        if (statusCode == null) {
            // throw unknown exception
            byte[] body = getResponseBody(response);
            String message = getErrorMessage(response.getRawStatusCode(), response.getStatusText(), body);
            throw new UnknownHttpStatusCodeException(message,
                    response.getRawStatusCode(), response.getStatusText(),
                    response.getHeaders(), body);
        }
        // otherwise, check if
        handleError(response, statusCode);
    }

    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        String statusText = response.getStatusText();
        HttpHeader headers = response.getHeaders();
        byte[] body = getResponseBody(response);
        String message = getErrorMessage(statusCode.code(), statusText, body);
        switch (statusCode.series()) {
            case CLIENT_ERROR:
                throw new ClientErrorException(message, statusCode, statusText, headers, body);
            case SERVER_ERROR:
                throw new ServerErrorException(message, statusCode, statusText, headers, body);
            default:
                throw new UnknownHttpStatusCodeException(message, statusCode.code(), statusText, headers, body);
        }
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        int rawStatusCode = response.getRawStatusCode();
        HttpStatus status = HttpStatus.resolve(rawStatusCode);
        return (status != null ? hasError(status) : hasError(rawStatusCode));
    }

    protected boolean hasError(HttpStatus statusCode) {
        return statusCode.isError();
    }

    protected boolean hasError(int unknownRawStatusCode) {
        HttpStatus.Series series = HttpStatus.Series.resolve(unknownRawStatusCode);
        return (series == HttpStatus.Series.CLIENT_ERROR || series == HttpStatus.Series.SERVER_ERROR);
    }

    protected byte[] getResponseBody(ClientHttpResponse response) {
        try {
            return IoUtil.readBytes(response.getBody());
        }
        catch (IOException ex) {
            // ignore
        }
        return new byte[0];
    }

    private String getErrorMessage(int rawStatusCode, String statusText, byte[] responseBody) {
        String preface = rawStatusCode + " " + statusText + ": ";
        if (ObjectUtil.isEmpty(responseBody)) {
            return preface + "[no body]";
        }

        int maxChars = 200;

        if (responseBody.length < maxChars * 2) {
            return preface + "[" + new String(responseBody, StandardCharsets.UTF_8) + "]";
        }

        try {
            Reader reader = new InputStreamReader(new ByteArrayInputStream(responseBody), StandardCharsets.UTF_8);
            CharBuffer buffer = CharBuffer.allocate(maxChars);
            reader.read(buffer);
            reader.close();
            buffer.flip();
            return preface + "[" + buffer.toString() + "... (" + responseBody.length + " bytes)]";
        }
        catch (IOException ex) {
            // should never happen
            throw new IllegalStateException(ex);
        }
    }
}
