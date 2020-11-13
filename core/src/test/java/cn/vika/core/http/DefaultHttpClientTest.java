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

import cn.vika.core.model.HttpResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * unit test for {@link DefaultHttpClient}
 *
 * @author Shawn Deng
 * @date 2020-11-13 16:15:56
 */
public class DefaultHttpClientTest {

    private final DefaultHttpClient httpClient = new DefaultHttpClient("https://integration.vika.ltd/fusion/v1");

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        httpClient.addGlobalHeader("Authorization", "Bearer uskM7PcEPftF4wh0Ni1");
    }

    @Test
    public void testGet_1() throws JsonProcessingException {
        Map<String, String> uriVariables = new HashMap<>(2);
        uriVariables.put("datasheetId", "dstZVB3pUFhwQZ2mJ2");
        uriVariables.put("viewId", "viwZqVNtxLDKl");
        uriVariables.put("fieldKey", "name");
        HttpResult<FetchDatasheetResult> result = httpClient.get("/datasheets/{datasheetId}/records?viewId={viewId}&fieldKey={fieldKey}", HttpHeader.EMPTY, FetchDatasheetResult.class, uriVariables);
        System.out.println(objectMapper.writeValueAsString(result));
        Assertions.assertNotNull(result);
    }
}
