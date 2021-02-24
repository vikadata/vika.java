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
 * @date 2021-02-24 15:55:18
 */
public class OnlyHeaderWrapper implements RequestWrapper{

    private final HttpHeader header;

    public OnlyHeaderWrapper(HttpHeader header) {
        this.header = header;
    }

    @Override
    public void wrapper(ClientHttpRequest request) throws IOException {
        if (!this.header.isEmpty()) {
            HttpHeader httpHeader = request.getHeaders();
            this.header.forEach(httpHeader::put);
        }
    }
}
