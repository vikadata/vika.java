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

import java.net.URI;

/**
 * Factory for {@link ClientHttpRequest} objects.
 * Requests are created by the {@link #createRequest(URI, HttpMethod)} method.
 *
 * @author Shawn Deng
 * @date 2020-10-26 19:18:02
 */
public interface ClientHttpRequestFactory {

    /**
     * Create a new {@link ClientHttpRequest} for the specified URI and HTTP method.
     * <p>
     * The returned request can be written to, and then executed by calling
     *
     * @param uri the URI to create a request for client
     * @param httpMethod the HTTP method to execute
     * @return ClientHttpRequest
     */
    ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod);

}
