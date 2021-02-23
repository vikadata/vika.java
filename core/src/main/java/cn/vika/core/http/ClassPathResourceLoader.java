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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.vika.core.utils.AssertUtil;
import cn.vika.core.utils.StringUtil;

/**
 * Class Path Resource
 * @author Shawn Deng
 * @date 2021-02-23 10:13:35
 */
public class ClassPathResourceLoader extends UrlResourceLoader {

    private static final String CLASSPATH_URL_PREFIX = "classpath:";

    public static final String FILE_URL_PREFIX = "file:";

    private final String path;

    private final ClassLoader classLoader;

    private final Class<?> clazz;

    public ClassPathResourceLoader(String path) {
        this(path, null, null);
    }

    public ClassPathResourceLoader(String path, ClassLoader classLoader) {
        this(path, classLoader, null);
    }

    public ClassPathResourceLoader(String path, Class<?> clazz) {
        this(path, null, clazz);
    }

    public ClassPathResourceLoader(String pathBaseClassLoader, ClassLoader classLoader, Class<?> clazz) {
        super(null);
        AssertUtil.notNull(pathBaseClassLoader, "Path must not be null");

        final String path = normalizePath(pathBaseClassLoader);
        this.path = path;
        this.name = StringUtil.hasText(path) ? StringUtil.getName(path) : null;

        this.classLoader = classLoader != null ? classLoader : getClassLoader();
        this.clazz = clazz;
        initUrl();
    }

    private void initUrl() {
        if (null != this.clazz) {
            super.url = this.clazz.getResource(this.path);
        }
        else if (null != this.classLoader) {
            super.url = this.classLoader.getResource(this.path);
        }
        else {
            super.url = ClassLoader.getSystemResource(this.path);
        }
        if (null == super.url) {
            throw new RuntimeException("Resource of path [" + this.path + "] not exist!");
        }
    }

    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassPathResourceLoader.class.getClassLoader();
            if (null == classLoader) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    private String normalizePath(String path) {
        // 标准化路径
        path = normalize(path);
        path = StringUtil.removePrefix(path, StringUtil.SLASH);

        AssertUtil.isTrue(!isAbsolutePath(path), "Path [" + path + "] must be a relative path !");
        return path;
    }

    private boolean isAbsolutePath(String path) {
        return StringUtil.SLASH_CHAR == path.charAt(0) || path.matches("^[a-zA-Z]:([/\\\\].*)?");
    }

    private String normalize(String path) {
        if (path == null) {
            return null;
        }
        // Spring style path
        String pathToUse = StringUtil.removePrefixIgnoreCase(path, CLASSPATH_URL_PREFIX);
        // remove prefix file:
        pathToUse = StringUtil.removePrefixIgnoreCase(pathToUse, FILE_URL_PREFIX);

        if (!StringUtil.hasText(pathToUse)) {
            return StringUtil.EMPTY;
        }

        // recognize home dir and translate absolute path
        if (pathToUse.startsWith("~")) {
            pathToUse = pathToUse.replace("~", System.getProperty("user.home"));
        }

        // replace use \\
        pathToUse = pathToUse.replaceAll("[/\\\\]+", StringUtil.SLASH).trim();
        // windows path style
        if (path.startsWith("\\\\")) {
            pathToUse = "\\" + pathToUse;
        }

        String prefix = "";
        int prefixIndex = pathToUse.indexOf(StringUtil.COLON);
        if (prefixIndex > -1) {
            // may be windows style path
            prefix = pathToUse.substring(0, prefixIndex + 1);
            if (prefix.startsWith(StringUtil.SLASH)) {
                // remove / prefix
                prefix = prefix.substring(1);
            }
            if (!prefix.contains(StringUtil.SLASH)) {
                pathToUse = pathToUse.substring(prefixIndex + 1);
            }
            else {
                prefix = StringUtil.EMPTY;
            }
        }
        if (pathToUse.startsWith(StringUtil.SLASH)) {
            prefix += StringUtil.SLASH;
            pathToUse = pathToUse.substring(1);
        }

        List<String> pathList = Arrays.asList(pathToUse.split(StringUtil.SLASH));
        List<String> pathElements = new LinkedList<>();
        int tops = 0;

        String element;
        for (int i = pathList.size() - 1; i >= 0; i--) {
            element = pathList.get(i);
            if (!StringUtil.DOT.equals(element)) {
                if (StringUtil.DOUBLE_DOT.equals(element)) {
                    tops++;
                }
                else {
                    if (tops > 0) {
                        tops--;
                    }
                    else {
                        // Normal path element found.
                        pathElements.add(0, element);
                    }
                }
            }
        }
        return prefix + StringUtil.join(pathElements, StringUtil.SLASH);
    }

    @Override
    public String toString() {
        return (null == this.path) ? super.toString() : "classpath:" + this.path;
    }
}
