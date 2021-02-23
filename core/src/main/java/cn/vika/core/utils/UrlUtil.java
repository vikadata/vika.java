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

package cn.vika.core.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Url Util
 * @author Shawn Deng
 * @date 2021-02-23 15:59:09
 */
public class UrlUtil {

    public static URL url(String url) {
        try {
            return new URL(null, url);
        }
        catch (MalformedURLException e) {
            // 尝试文件路径
            try {
                return new File(url).toURI().toURL();
            }
            catch (MalformedURLException ex2) {
                throw new RuntimeException(e);
            }
        }
    }

    public static URL getURL(File file) {
        AssertUtil.notNull(file, "File is null !");
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error when get URL!");
        }
    }
}
