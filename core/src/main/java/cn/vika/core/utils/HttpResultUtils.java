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

package cn.vika.core.utils;

import cn.vika.core.model.HttpResult;

/**
 * Http result utils
 *
 * @author Shawn Deng
 * @date 2020-10-26 17:48:33
 */
public class HttpResultUtils {

    public static <T> HttpResult<T> success() {
        return HttpResult.<T>builder().withCode(200).build();
    }

    public static <T> HttpResult<T> success(T data) {
        return HttpResult.<T>builder().withSuccess(true).withCode(200).withData(data).build();
    }

    public static <T> HttpResult<T> success(int code, T data) {
        return HttpResult.<T>builder().withSuccess(true).withCode(code).withData(data).build();
    }

    public static <T> HttpResult<T> failed() {
        return HttpResult.<T>builder().withSuccess(false).withCode(500).build();
    }

    public static <T> HttpResult<T> failed(String errMsg) {
        return HttpResult.<T>builder().withSuccess(false).withCode(500).withMsg(errMsg).build();
    }

    public static <T> HttpResult<T> failed(int code, T data) {
        return HttpResult.<T>builder().withSuccess(false).withCode(code).withData(data).build();
    }

    public static <T> HttpResult<T> failed(int code, T data, String errMsg) {
        return HttpResult.<T>builder().withSuccess(false).withCode(code).withData(data).withMsg(errMsg).build();
    }

    public static <T> HttpResult<T> failedWithMsg(int code, String errMsg) {
        return HttpResult.<T>builder().withSuccess(false).withCode(code).withMsg(errMsg).build();
    }
}
