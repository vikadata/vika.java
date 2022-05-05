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

package cn.vika.client.api;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-19 11:37:42
 */
public enum ConstantKey {

    TEST_HOST_URL("TEST_HOST_URL"),
    TEST_API_KEY("TEST_API_KEY"),
    TEST_SPACE_ID("TEST_SPACE_ID"),
    TEST_DATASHEET_ID("TEST_DATASHEET_ID"),
    PAGE_NUM("DEFAULT_PAGE_NUM"),
    PAGE_SIZE("DEFAULT_PAGE_SIZE"),
    TEST_VIEW_ID("TEST_VIEW_ID"),
    TEST_SORT("TEST_SORT"),
    TEST_FIELDS("TEST_FIELDS"),
    TEST_FILTER_FORMULA("TEST_FILTER_FORMULA"),
    TEST_FIELD_KEY("TEST_FIELD_KEY"),
    TEST_MAX_RECORDS("TEST_MAX_RECORDS");

    private final String key;

    ConstantKey(String key) {
        this.key = key;
    }

    public String get() {
        String value =  PropertiesUtil.getProperty(key);
        assertThat(value).isNotNull();
        return value;
    }
}
