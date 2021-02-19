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

import java.util.HashMap;
import java.util.List;

import cn.vika.client.api.model.AbstractModel;

/**
 * record page response info
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:10:31
 */
public class RecordPageInfo extends AbstractModel {

    /**
     * page number
     */
    private Integer pageNum;

    private Integer pageSize;

    private Integer total;

    private List<RecordDetail> records;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<RecordDetail> getRecords() {
        return records;
    }

    public void setRecords(List<RecordDetail> records) {
        this.records = records;
    }

    @Override
    public void toMap(HashMap<String, String> map, String prefix) {

    }
}
