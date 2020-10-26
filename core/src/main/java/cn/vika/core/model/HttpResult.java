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

package cn.vika.core.model;

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
