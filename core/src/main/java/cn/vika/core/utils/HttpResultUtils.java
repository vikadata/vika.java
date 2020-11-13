/*
 * MIT License
 *
 * Copyright (c) 2020 vika
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
