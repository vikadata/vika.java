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

package cn.vika.client.api.models;

import java.util.List;

/**
 * create record request params
 *
 * @author Zoe Zheng
 * @date 2020-12-17 11:22:01
 */
public class Record {

    private String fieldKey;

    private List<RecordDetail> records;

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public Record withFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
        return this;
    }

    public List<RecordDetail> getRecords() {
        return records;
    }

    public void setRecords(List<RecordDetail> records) {
        this.records = records;
    }

    public Record withRecords(List<RecordDetail> records) {
        this.records = records;
        return this;
    }
}
