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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static cn.vika.core.http.HttpHeader.AUTHORIZATION;

/**
 * unit test for {@link DefaultHttpClient}
 *
 * @author Shawn Deng
 * @date 2020-11-13 16:15:56
 */
public class DefaultHttpClientTest {

    private final DefaultHttpClient httpClient = new DefaultHttpClient("https://vika.cn/fusion/v1");

//    @BeforeEach
    public void setup() {
        httpClient.addGlobalHeader(AUTHORIZATION, "Bearer usk9Urb7NLr8b42SJCIH42J");
    }

//    @Test
    public void testGet() {
        Map<String, String> uriVariables = new HashMap<>(3);
        uriVariables.put("datasheetId", "dstkbJ434jLJ40q4TQ");
//        GenericTypeReference<HttpResult<FetchDatasheetResult>> reference = new GenericTypeReference<HttpResult<FetchDatasheetResult>>() {
//        };
//        HttpResult<FetchDatasheetResult> result = httpClient.get("/datasheets/{datasheetId}/records", HttpHeader.EMPTY, reference, uriVariables);
//        System.out.println(JacksonConverter.toJson(result));
//        Assertions.assertNotNull(result);
    }

//    @Test
    public void testPost() {
        Map<String, String> uriVariables = new HashMap<>(3);
        uriVariables.put("datasheetId", "dstkbJ434jLJ40q4TQ");

        HttpHeader header = HttpHeader.EMPTY;

        RecordDTO dto = new RecordDTO();
        Map<String, Object> row = new HashMap<>();
        row.put("语言", "RQWEQWE");
        dto.setFields(row);

        RecordRequestBody body = new RecordRequestBody();
        body.setRecords(Collections.singletonList(dto));

//        GenericTypeReference<HttpResult<AddOrUpdateResult>> reference = new GenericTypeReference<HttpResult<AddOrUpdateResult>>() {
//        };
//        HttpResult<AddOrUpdateResult> result = httpClient.post("/datasheets/{datasheetId}/records",
//            header,
//            body,
//            reference,
//            uriVariables
//        );

//        System.out.println(JacksonConverter.toJson(result));
//        Assertions.assertNotNull(result);
    }

//    @Test
    public void testPatch() {
        Map<String, String> uriVariables = new HashMap<>(3);
        uriVariables.put("datasheetId", "dstkbJ434jLJ40q4TQ");

        HttpHeader header = HttpHeader.EMPTY;

        UpdateRecordDTO dto = new UpdateRecordDTO();
        dto.setRecordId("recmWssUMbQXz");
        Map<String, Object> row = new HashMap<>();
        row.put("语言", "我来修改的");
        dto.setFields(row);

        RecordUpdateRequestBody body = new RecordUpdateRequestBody();
        body.setRecords(Collections.singletonList(dto));

//        GenericTypeReference<HttpResult<AddOrUpdateResult>> reference = new GenericTypeReference<HttpResult<AddOrUpdateResult>>() {
//        };
//        HttpResult<AddOrUpdateResult> result = httpClient.patch("/datasheets/{datasheetId}/records",
//            header,
//            body,
//            reference,
//            uriVariables
//        );

//        System.out.println(JacksonConverter.toJson(result));
//        Assertions.assertNotNull(result);
    }

//    @Test
    public void testDelete() {
        Map<String, String> uriVariables = new HashMap<>(3);
        uriVariables.put("datasheetId", "dstkbJ434jLJ40q4TQ");
        uriVariables.put("recordId", "recmWssUMbQXz");

//        GenericTypeReference<HttpResult<Void>> reference = new GenericTypeReference<HttpResult<Void>>() {
//        };
//        HttpResult<Void> result = httpClient.delete("/datasheets/{datasheetId}/records?recordIds={recordId}", HttpHeader.EMPTY, reference, uriVariables);
//        System.out.println(JacksonConverter.toJson(result));
//        Assertions.assertNotNull(result);
    }
}
