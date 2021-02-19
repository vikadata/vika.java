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
 * Create Record Model
 * @author Shawn Deng
 * @date 2021-02-19 15:41:12
 */
public class CreateRecordRequest {

    private List<RecordMap> records;

    public List<RecordMap> getRecords() {
        return records;
    }

    public void setRecords(List<RecordMap> records) {
        this.records = records;
    }

    public CreateRecordRequest withRecords(List<RecordMap> records) {
        this.records = records;
        return this;
    }
}
