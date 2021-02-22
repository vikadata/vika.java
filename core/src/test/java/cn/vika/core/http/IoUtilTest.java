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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import cn.vika.core.utils.IoUtil;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-22 10:53:10
 */
public class IoUtilTest {

    @Test
    public void testInputStreamToString() {
        String originalString = "\"code\":200,\"success\":true,\"message\":\"SUCCESS\"}";
        InputStream inputStream = new ByteArrayInputStream(originalString.getBytes(StandardCharsets.UTF_8));
        String string = IoUtil.readString(inputStream);
        System.out.println(string);
    }
}
