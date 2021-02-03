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

import java.util.Map;

/**
 * this is for {@link String} util method
 *
 * @author Shawn Deng
 * @date 2020-10-27 16:55:30
 */
public class StringUtil {

    public static final String SLASH = "/";

    /**
     * Check whether the given {@code String} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code String} is not {@code null}, its length is greater than 0,
     * and it contains at least one non-whitespace character.
     *
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null}, its
     * length is greater than 0, and it does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String format(String template, Map<String, ?> values) {
        if (null == template) {
            return null;
        }
        if (null == values || values.isEmpty()) {
            return template;
        }

        String template2 = template;
        String value;
        for (Map.Entry<String, ?> entry : values.entrySet()) {
            value = entry.getValue().toString();
            if (null != value) {
                template2 = replace(template2, 0,"{" + entry.getKey() + "}", value);
            }
        }
        return template2;
    }

    public static String format(String template, Object... params) {
        if (null == template) {
            return null;
        }
        if (params == null || params.length == 0 || isEmpty(template)) {
            return template;
        }
        return String.format(template, params);
    }

    public static String replace(String str, int fromIndex, String searchStr, String replacement) {
        if (isEmpty(str) || isEmpty(searchStr)) {
            return str;
        }
        if (null == replacement) {
            replacement = "";
        }

        final int strLength = str.length();
        final int searchStrLength = searchStr.length();
        if (fromIndex > strLength) {
            return str;
        } else if (fromIndex < 0) {
            fromIndex = 0;
        }

        final StringBuilder result = new StringBuilder(strLength + 16);
        if (0 != fromIndex) {
            result.append(str.subSequence(0, fromIndex));
        }

        int preIndex = fromIndex;
        int index;
        while ((index = str.indexOf(searchStr, preIndex)) > -1) {
            result.append(str.subSequence(preIndex, index));
            result.append(replacement);
            preIndex = index + searchStrLength;
        }

        if (preIndex < strLength) {
            result.append(str.subSequence(preIndex, strLength));
        }
        return result.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
