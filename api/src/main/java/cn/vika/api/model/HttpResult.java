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

package cn.vika.api.model;

import java.io.Serializable;

/**
 * Http request result
 *
 * @author Shawn Deng
 * @date 2020-10-26 17:41:41
 */
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = -7209417159215144399L;

    private boolean success;

    private int code;

    private String message;

    private T data;

    public HttpResult() {
    }

    public HttpResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public HttpResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public HttpResult(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" + "success=" + success + "code=" + code + ", message='" + message + '\'' + ", data=" + data + '}';
    }

    public static <T> HttpResultBuilder<T> builder() {
        return new HttpResultBuilder<T>();
    }

    public static final class HttpResultBuilder<T> {

        private boolean success;

        private int code;

        private String errMsg;

        private T data;

        private HttpResultBuilder() {
        }

        public HttpResultBuilder<T> withSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public HttpResultBuilder<T> withCode(int code) {
            this.code = code;
            return this;
        }

        public HttpResultBuilder<T> withMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        public HttpResultBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        /**
         * Build result.
         *
         * @return result
         */
        public HttpResult<T> build() {
            HttpResult<T> restResult = new HttpResult<T>();
            restResult.setCode(code);
            restResult.setMessage(errMsg);
            restResult.setData(data);
            return restResult;
        }
    }
}
