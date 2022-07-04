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

package cn.vika.client.api.model;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiQueryParamTest {

    @Test
    public void testParamWithSort() {
        ApiQueryParam queryParam = new ApiQueryParam()
                .withSort("id", Order.DESC);
        Map<String, String> queryMap = queryParam.toMap();
        assertThat(queryMap).containsOnlyKeys("sort%5B%5D");
    }

    @Test
    public void testParamWithSortMulti() {
        ApiQueryParam queryParam = new ApiQueryParam()
                .withSort("id", Order.DESC)
                .withSort("name", Order.DESC);
        Map<String, String> queryMap = queryParam.toMap();
        assertThat(queryMap).containsOnlyKeys("sort%5B%5D.0", "sort%5B%5D.1");
    }

    @Test
    public void testParamWithRecordIds() {
        List<String> recordIds = Lists.list("rec1", "rec2", "rec3");
        ApiQueryParam queryParam = new ApiQueryParam().withRecordIds(recordIds);
        Map<String, String> queryMap = queryParam.toMap();
        assertThat(queryMap).containsOnlyKeys("recordIds.0", "recordIds.1", "recordIds.2");
    }
}
