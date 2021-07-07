/*
 * Copyright (c) 2021 vikadata, https://vika.cn <support@vikadata.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
