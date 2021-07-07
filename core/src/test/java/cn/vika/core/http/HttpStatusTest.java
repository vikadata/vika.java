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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link HttpStatus}
 * @author Shawn Deng
 * @date 2021-02-02 14:07:13
 */
public class HttpStatusTest {

    private final Map<Integer, String> statusCodes = new LinkedHashMap<>();

    @BeforeEach
    public void initStatusCode() {
        statusCodes.put(100, "CONTINUE");
        statusCodes.put(101, "SWITCHING_PROTOCOLS");
        statusCodes.put(102, "PROCESSING");
        statusCodes.put(103, "CHECKPOINT");

        statusCodes.put(200, "OK");
        statusCodes.put(201, "CREATED");
        statusCodes.put(202, "ACCEPTED");
        statusCodes.put(203, "NON_AUTHORITATIVE_INFORMATION");
        statusCodes.put(204, "NO_CONTENT");
        statusCodes.put(205, "RESET_CONTENT");
        statusCodes.put(206, "PARTIAL_CONTENT");
        statusCodes.put(207, "MULTI_STATUS");
        statusCodes.put(208, "ALREADY_REPORTED");
        statusCodes.put(226, "IM_USED");

        statusCodes.put(300, "MULTIPLE_CHOICES");
        statusCodes.put(301, "MOVED_PERMANENTLY");
        statusCodes.put(302, "FOUND");
        statusCodes.put(303, "SEE_OTHER");
        statusCodes.put(304, "NOT_MODIFIED");
        statusCodes.put(307, "TEMPORARY_REDIRECT");
        statusCodes.put(308, "PERMANENT_REDIRECT");

        statusCodes.put(400, "BAD_REQUEST");
        statusCodes.put(401, "UNAUTHORIZED");
        statusCodes.put(402, "PAYMENT_REQUIRED");
        statusCodes.put(403, "FORBIDDEN");
        statusCodes.put(404, "NOT_FOUND");
        statusCodes.put(405, "METHOD_NOT_ALLOWED");
        statusCodes.put(406, "NOT_ACCEPTABLE");
        statusCodes.put(407, "PROXY_AUTHENTICATION_REQUIRED");
        statusCodes.put(408, "REQUEST_TIMEOUT");
        statusCodes.put(409, "CONFLICT");
        statusCodes.put(410, "GONE");
        statusCodes.put(411, "LENGTH_REQUIRED");
        statusCodes.put(412, "PRECONDITION_FAILED");
        statusCodes.put(413, "PAYLOAD_TOO_LARGE");
        statusCodes.put(414, "URI_TOO_LONG");
        statusCodes.put(415, "UNSUPPORTED_MEDIA_TYPE");
        statusCodes.put(416, "REQUESTED_RANGE_NOT_SATISFIABLE");
        statusCodes.put(417, "EXPECTATION_FAILED");
        statusCodes.put(418, "I_AM_A_TEAPOT");
        statusCodes.put(422, "UNPROCESSABLE_ENTITY");
        statusCodes.put(423, "LOCKED");
        statusCodes.put(424, "FAILED_DEPENDENCY");
        statusCodes.put(425, "TOO_EARLY");
        statusCodes.put(426, "UPGRADE_REQUIRED");
        statusCodes.put(428, "PRECONDITION_REQUIRED");
        statusCodes.put(429, "TOO_MANY_REQUESTS");
        statusCodes.put(431, "REQUEST_HEADER_FIELDS_TOO_LARGE");
        statusCodes.put(451, "UNAVAILABLE_FOR_LEGAL_REASONS");

        statusCodes.put(500, "INTERNAL_SERVER_ERROR");
        statusCodes.put(501, "NOT_IMPLEMENTED");
        statusCodes.put(502, "BAD_GATEWAY");
        statusCodes.put(503, "SERVICE_UNAVAILABLE");
        statusCodes.put(504, "GATEWAY_TIMEOUT");
        statusCodes.put(505, "HTTP_VERSION_NOT_SUPPORTED");
        statusCodes.put(506, "VARIANT_ALSO_NEGOTIATES");
        statusCodes.put(507, "INSUFFICIENT_STORAGE");
        statusCodes.put(508, "LOOP_DETECTED");
        statusCodes.put(509, "BANDWIDTH_LIMIT_EXCEEDED");
        statusCodes.put(510, "NOT_EXTENDED");
        statusCodes.put(511, "NETWORK_AUTHENTICATION_REQUIRED");
    }

    @Test
    public void testMapToEnum() {
        statusCodes.forEach((code, value) -> {
            HttpStatus status = HttpStatus.valueOf(code);
            assertThat(status.code()).as("Invalid code").isEqualTo(code);
            assertThat(status.name()).as("Invalid value for [" + code + "]").isEqualTo(value);
        });
    }

    @Test
    public void testEnumToMap() {
        Arrays.stream(HttpStatus.values()).forEach(status -> {
            int code = status.code();
            assertThat(statusCodes).as("Map has no value for [" + code + "]").containsKey(code);
            assertThat(status.name()).as("Invalid name for [" + code + "]").isEqualTo(statusCodes.get(code));
        });
    }

    @Test
    public void testIntToEnum() {
        int code = 201;
        HttpStatus status = HttpStatus.resolve(code);
        assertThat(status).isNotNull();
        assertThat(status.code()).as("must be [" + code + "]").isEqualTo(code);
    }

    @Test
    public void testHttpStatusSeries() {
        int code1xx = 101;
        HttpStatus status1xx = HttpStatus.resolve(code1xx);
        assertThat(status1xx).isNotNull();
        assertThat(status1xx.is1xxInformational()).as("must be information series code").isTrue();

        int code2xx = 201;
        HttpStatus status2xx = HttpStatus.resolve(code2xx);
        assertThat(status2xx).isNotNull();
        assertThat(status2xx.is2xxSuccessful()).as("must be success series code").isTrue();

        int code3xx = 301;
        HttpStatus status3xx = HttpStatus.resolve(code3xx);
        assertThat(status3xx).isNotNull();
        assertThat(status3xx.is3xxRedirection()).as("must be redirect series code").isTrue();

        int code4xx = 401;
        HttpStatus status4xx = HttpStatus.resolve(code4xx);
        assertThat(status4xx).isNotNull();
        assertThat(status4xx.is4xxClientError()).as("must be client error series code").isTrue();

        int code5xx = 501;
        HttpStatus status5xx = HttpStatus.resolve(code5xx);
        assertThat(status5xx).isNotNull();
        assertThat(status5xx.is5xxServerError()).as("must be server error series code").isTrue();
    }
}
