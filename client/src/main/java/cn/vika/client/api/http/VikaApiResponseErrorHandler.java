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

package cn.vika.client.api.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.vika.client.api.exception.ApiException;
import cn.vika.core.http.ClientHttpResponse;
import cn.vika.core.http.DefaultHttpResponseErrorHandler;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static cn.vika.client.api.exception.ApiException.DEFAULT_CODE;
import static cn.vika.client.api.exception.ApiException.DEFAULT_ERROR_MESSAGE;

/**
 * vika rest api error handler
 * @author Shawn Deng
 * @date 2021-02-06 16:07:28
 */
public class VikaApiResponseErrorHandler extends DefaultHttpResponseErrorHandler {

    @Override
    public void handleCustomError(ClientHttpResponse response) throws Exception {
        String responseBody = getBody(response.getBody());
        try {
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            JsonNode jsonNode = mapper.readValue(responseBody, JsonNode.class);
            boolean success = jsonNode.has("success") && jsonNode.get("success").asBoolean(false);
            if (!success) {
                int code = jsonNode.has("code") ? jsonNode.get("code").intValue() : DEFAULT_CODE;
                String message = jsonNode.has("message") ? jsonNode.get("message").asText() : DEFAULT_ERROR_MESSAGE;

                throw new ApiException(code, message);
            }
        }
        catch (IOException e) {
            throw new ApiException(e);
        }
    }

    private String getBody(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }
        return sb.toString();
    }
}
