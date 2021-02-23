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

package cn.vika.client.api.model;

import java.util.Map;

import cn.vika.core.utils.JacksonConverter;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-19 15:36:51
 */
public class RecordMap {

    /**
     * record fields
     */
    protected Map<String, Object> fields;

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public RecordMap withFields(Map<String, Object> fields) {
        this.fields = fields;
        return this;
    }

    public static <T> Map<String, Object> parseFieldsFromBean(T bean) {
        return JacksonConverter.toMap(bean);
    }
}
