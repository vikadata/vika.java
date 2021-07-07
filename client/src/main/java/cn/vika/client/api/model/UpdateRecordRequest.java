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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Create Record Model
 * @author Shawn Deng
 * @date 2021-02-19 15:41:12
 */
public class UpdateRecordRequest {

    @JsonInclude(Include.NON_NULL)
    private FieldKey fieldKey;

    private List<UpdateRecord> records;

    public FieldKey getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(FieldKey fieldKey) {
        this.fieldKey = fieldKey;
    }

    public List<UpdateRecord> getRecords() {
        return records;
    }

    public void setRecords(List<UpdateRecord> records) {
        this.records = records;
    }

    public UpdateRecordRequest withRecords(List<UpdateRecord> records) {
        this.records = records;
        return this;
    }

    public UpdateRecordRequest withFieldKey(FieldKey fieldKey) {
        this.fieldKey = fieldKey;
        return this;
    }
}
