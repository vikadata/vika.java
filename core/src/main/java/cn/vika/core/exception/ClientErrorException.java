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

package cn.vika.core.exception;

import cn.vika.core.http.HttpHeader;
import cn.vika.core.http.HttpStatus;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-06 15:13:50
 */
public class ClientErrorException extends StatusCodeException {

    private static final long serialVersionUID = 8373468532484444419L;

    public ClientErrorException(String message, HttpStatus statusCode, String statusText, HttpHeader responseHeaders, byte[] responseBody) {
        super(message, statusCode, statusText, responseHeaders, responseBody);
    }
}
