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
