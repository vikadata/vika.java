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
 *
 * @author Shawn Deng
 * @date 2021-02-06 11:14:11
 */
public interface HttpResponseErrorHandler {

    /**
     * Indicate whether the given response has any errors.
     * use check if error happen,then call handlerError method
     * @param response the response to inspect
     * @return {@code true} if the response indicates an error; {@code false} otherwise
     * @throws IOException in case of I/O errors
     */
    boolean hasError(ClientHttpResponse response) throws IOException;

    /**
     * Handle the error in the given response.
     * <p>This method is only called
     * when {@link #hasError(ClientHttpResponse)} has returned {@code true}.</p>
     * @param response the response with the error
     * @throws IOException in case of I/O errors
     */
    void handlerError(ClientHttpResponse response) throws IOException;

    /**
     * Handle the error in the given response.
     * <p>This method is only called
     * when you deal with self custom response body.</p>
     * @param response the response with the error
     * @throws IOException in case of I/O errors
     */
    default void handleCustomError(ClientHttpResponse response) throws Exception {
    }

    ;
}
