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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-19 16:17:16
 */
public class UpdateRecord extends RecordMap {

    private String recordId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public UpdateRecord withRecordId(String recordId) {
        this.recordId = recordId;
        return this;
    }

    public UpdateRecord withField(String key, Object value) {
        nullSafeSetFields().put(key, value);
        return this;
    }

    private Map<String, Object> nullSafeSetFields() {
        if (getFields() == null) {
            setFields(new LinkedHashMap<>());
        }
        return getFields();
    }
}
