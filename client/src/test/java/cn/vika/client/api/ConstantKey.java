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
