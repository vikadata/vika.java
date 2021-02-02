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
