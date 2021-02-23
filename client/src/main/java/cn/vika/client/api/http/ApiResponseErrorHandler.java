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

import java.io.IOException;

import cn.vika.client.api.exception.ApiException;
import cn.vika.core.http.ResponseBodyHandler;
import cn.vika.core.utils.JacksonConverter;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * vika rest api error handler
 * @author Shawn Deng
 * @date 2021-02-06 16:07:28
 */
public class ApiResponseErrorHandler implements ResponseBodyHandler {

    @Override
    public void handleBody(byte[] content) throws IOException {
        JsonNode jsonNode = JacksonConverter.unmarshal(content);
        boolean success = jsonNode.get("success").asBoolean();
        if (!success) {
            throw new ApiException(jsonNode.get("code").asInt(), jsonNode.get("message").toString());
        }
    }
}
