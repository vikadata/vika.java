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

package cn.vika.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shawn Deng
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
            if (index == -1) {
                uri = setUriTemplate(uri, entry.getKey(), entry.getKey());
                continue;
            }
            int length = entry.getKey().length();
            if (length > (index + 1)) {
                String key = entry.getKey().substring(0, index);
                uri = setUriTemplate(uri, key, entry.getKey());
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
