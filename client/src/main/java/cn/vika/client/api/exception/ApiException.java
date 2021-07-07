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

package cn.vika.client.api.exception;

/**
 * Api base exception
 *
 * @author Zoe Zheng
 * @date 2020-12-15 18:24:09
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -8107100836846410486L;

    public static Integer DEFAULT_CODE = 50001;

    private Integer code;

    private String message;

    public ApiException(String message) {
        super(message);
        this.message = message;
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApiException(Exception e) {
        super(e);
        message = e.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    /**
     * Return the reason phrase of this status code.
     */
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        if (getCode() == null || getCode() == 0) {
            this.code = DEFAULT_CODE;
        }
        return String.format("%s: code=%d, message=%s", getClass().getName(), getCode(), getLocalizedMessage());
    }
}
