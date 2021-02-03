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
 * handling response
 *
 * @author Shawn Deng
 * @date 2020-11-12 01:30:47
 */
public interface ResponseHandler<T> {

    /**
     * Extract data from the given {@code ClientHttpResponse} and return it.
     *
     * @param response the HTTP response
     * @return the extracted data
     * @throws IOException in case of I/O errors
     */
    T extractData(ClientHttpResponse response) throws IOException;
}
