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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-05 21:46:29
 */
public class MapUtil {

    /**
     * format the request query uri
     *
     * @param map params map
     * @return uri
     */
    public static String extractKeyToVariables(Map<String, String> map) {
        if (null == map || map.isEmpty()) {
            return "";
        }
        String uri = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            int index = entry.getKey().lastIndexOf('.');
            if ((entry.getKey().length() - index - 1) == 0 ) {
                uri = setUriTemplate(uri, entry.getKey().substring(0, index), entry.getKey());
            }
            else {
                uri = setUriTemplate(uri, entry.getKey(), entry.getKey());
            }
        }
        return uri;
    }

    public static Map<String, String> listToUriVariableMap(String keyName, List<String> list) {
        if (list == null) {
            return null;
        }

        if (list.isEmpty()) {
            return null;
        }

        Map<String, String> variables = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            variables.put(keyName + "." + i, list.get(i));
        }
        return variables;
    }

    public static String setUriTemplate(String param, String key, String value) {
        if (value != null) {
            if (!"".equals(param)) {
                return param + "&" + key + "=" + "{" + value + "}";
            }
            else {
                return "?" + key + "=" + "{" + value + "}";
            }
        }
        return "";
    }
}
