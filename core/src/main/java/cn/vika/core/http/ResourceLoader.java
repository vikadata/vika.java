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
import java.io.InputStream;
import java.net.URL;

/**
 * Resource Loader interface
 * @author Shawn Deng
 * @date 2021-02-23 10:13:18
 */
public interface ResourceLoader {

    /**
     * Resource Name
     */
    String getName();

    /**
     * Resource Url
     */
    URL getUrl();

    /**
     * Resource to input stream
     */
    InputStream getInputStream() throws IOException;

    /**
     * Resource to byte
     */
    byte[] readBytes() throws IOException;

    /**
     * Resource content length
     */
    long contentLength() throws IOException;
}
