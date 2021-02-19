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

package cn.vika.client.api.datasheet;

import cn.vika.client.api.model.AbstractModel;
import cn.vika.client.api.model.HttpResult;
import cn.vika.core.http.GenericTypeReference;

/**
 * test
 *
 * @author Zoe Zheng
 * @date 2020-12-15 17:19:41
 */
public interface IRecordApi {
    /**
     * record list
     *
     * @param params request query params
     * @param responseType response type
     * @return responseType
     */
    <T> T getRecords(String datasheetId, AbstractModel params, GenericTypeReference<HttpResult<T>> responseType);

    /**
     * add records
     *
     * @param model body data for post
     * @param responseType response type
     * @return responseType
     */
    <T> T addRecords(String datasheetId, AbstractModel model, GenericTypeReference<HttpResult<T>> responseType);

    /**
     * modify record
     *
     * @param model body data for post
     * @param responseType response type
     * @return responseType
     */
    <T> T modifyRecords(String datasheetId, AbstractModel model, GenericTypeReference<HttpResult<T>> responseType);

    /**
     * delete records
     *
     * @param model body data for post
     * @return boolean
     */
    boolean deleteRecords(String datasheetId, AbstractModel model);
}
