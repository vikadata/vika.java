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
