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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link HttpHeader}
 * @author Shawn Deng
 * @date 2021-02-02 10:50:43
 */
public class HttpHeaderTest {

    private final HttpHeader httpHeader = new HttpHeader();

    @Test
    public void testEmptyHeader() {
        assertThat(httpHeader).isEmpty();
        assertThat(httpHeader).hasSize(0);
    }

    @Test
    public void testGetOrEmpty() {
        String key = "foo";

        // should null
        assertThat(httpHeader.get(key)).isNull();
        assertThat(httpHeader.getOrEmpty(key)).isEmpty();

        // exactly contain value "bar" and only one value
        httpHeader.add(key, "bar");
        assertThat(httpHeader.getOrEmpty(key)).hasSize(1);
        assertThat(httpHeader.getOrEmpty(key)).containsExactly("bar");

        // should empty after remove key
        httpHeader.remove(key);
        assertThat(httpHeader.get(key)).isNull();
        assertThat(httpHeader.getOrEmpty(key)).isEmpty();
    }

    @Test
    public void testGetFirstValue() {
        this.httpHeader.clear();
        String headerName = "foo";
        String headerValue = "bar";
        this.httpHeader.add(headerName, headerValue);
        assertThat(this.httpHeader.getFirstValue(headerName)).isEqualTo(headerValue);
    }

    @Test
    public void testBearerAuth() {
        this.httpHeader.clear();
        String token = "foo";
        httpHeader.setBearerAuth(token);
        String authorization = httpHeader.getFirstValue(HttpHeader.AUTHORIZATION);
        assertThat(authorization).isEqualTo("Bearer foo");
    }
}
