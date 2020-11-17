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
import cn.vika.core.utils.JacksonConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static cn.vika.core.http.HttpHeaderConstants.AUTHORIZATION;

/**
 * unit test for {@link DefaultHttpClient}
 *
 * @author Shawn Deng
 * @date 2020-11-13 16:15:56
 */
public class DefaultHttpClientTest {

    private final DefaultHttpClient httpClient = new DefaultHttpClient("https://vika.cn/fusion/v1");

    @BeforeEach
    public void setup() {
        httpClient.addGlobalHeader(AUTHORIZATION, "Bearer uskvjUUnS5A166i8EUfj1r8");
    }

    @Test
    public void testGet() {
        Map<String, String> uriVariables = new HashMap<>(3);
        uriVariables.put("datasheetId", "dstkbJ434jLJ40q4TQ");
        GenericTypeReference<HttpResult<FetchDatasheetResult>> reference = new GenericTypeReference<HttpResult<FetchDatasheetResult>>() {
        };
        HttpResult<FetchDatasheetResult> result = httpClient.get("/datasheets/{datasheetId}/records", HttpHeader.EMPTY, reference, uriVariables);
        System.out.println(JacksonConverter.toJson(result));
        Assertions.assertNotNull(result);
    }

    @Test
    public void testPost() {
        Map<String, String> uriVariables = new HashMap<>(3);
        uriVariables.put("datasheetId", "dstkbJ434jLJ40q4TQ");

        HttpHeader header = HttpHeader.EMPTY;
        header.put(HttpHeaderConstants.CONTENT_TYPE, Collections.singletonList(HttpMediaType.APPLICATION_JSON));

        RecordDTO dto = new RecordDTO();
        Map<String, Object> row = new HashMap<>();
        row.put("语言", "RQWEQWE");
        dto.setFields(row);

        RecordRequestBody body = new RecordRequestBody();
        body.setRecords(Collections.singletonList(dto));

        GenericTypeReference<HttpResult<AddOrUpdateResult>> reference = new GenericTypeReference<HttpResult<AddOrUpdateResult>>() {
        };
        HttpResult<AddOrUpdateResult> result = httpClient.post("/datasheets/{datasheetId}/records",
            header,
            body,
            reference,
            uriVariables
        );

        System.out.println(JacksonConverter.toJson(result));
        Assertions.assertNotNull(result);
    }

    @Test
    public void testPatch() {
        Map<String, String> uriVariables = new HashMap<>(3);
        uriVariables.put("datasheetId", "dstkbJ434jLJ40q4TQ");

        HttpHeader header = HttpHeader.EMPTY;
        header.put(HttpHeaderConstants.CONTENT_TYPE, Collections.singletonList(HttpMediaType.APPLICATION_JSON));

        UpdateRecordDTO dto = new UpdateRecordDTO();
        dto.setRecordId("recmWssUMbQXz");
        Map<String, Object> row = new HashMap<>();
        row.put("语言", "我来修改的");
        dto.setFields(row);

        RecordUpdateRequestBody body = new RecordUpdateRequestBody();
        body.setRecords(Collections.singletonList(dto));

        GenericTypeReference<HttpResult<AddOrUpdateResult>> reference = new GenericTypeReference<HttpResult<AddOrUpdateResult>>() {
        };
        HttpResult<AddOrUpdateResult> result = httpClient.patch("/datasheets/{datasheetId}/records",
            header,
            body,
            reference,
            uriVariables
        );

        System.out.println(JacksonConverter.toJson(result));
        Assertions.assertNotNull(result);
    }

    @Test
    public void testDelete() {
        Map<String, String> uriVariables = new HashMap<>(3);
        uriVariables.put("datasheetId", "dstkbJ434jLJ40q4TQ");
        uriVariables.put("recordId", "recL2aBCqbmdv");

        HttpHeader header = HttpHeader.EMPTY;

        GenericTypeReference<HttpResult<Void>> reference = new GenericTypeReference<HttpResult<Void>>() {
        };
        HttpResult<Void> result = httpClient.delete("/datasheets/{datasheetId}/records?recordIds={recordId}", HttpHeader.EMPTY, reference, uriVariables);
        System.out.println(JacksonConverter.toJson(result));
        Assertions.assertNotNull(result);
    }
}
