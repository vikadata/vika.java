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

package cn.vika.client.api.exception;

/**
 * Api base exception
 *
 * @author Zoe Zheng
 * @date 2020-12-15 18:24:09
 */
public class ApiException extends Exception {

    private static final long serialVersionUID = -8107100836846410486L;

    public static Integer DEFAULT_CODE = 50001;

    public static String DEFAULT_ERROR_MESSAGE = "SERVER_ERROR";

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
}
