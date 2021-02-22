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

package cn.vika.core.http;

import java.io.IOException;

/**
 * Response Body handler
 * @author Shawn Deng
 * @date 2021-02-20 17:20:09
 */
public interface ResponseBodyHandler {

    /**
     * Handle the error in the given response body byte array content.
     * <p>This method is only called when body is not empty</p>
     * @param content the response body byte
     * @throws IOException in case of I/O errors
     */
    void handleBody(byte[] content) throws IOException;
}
