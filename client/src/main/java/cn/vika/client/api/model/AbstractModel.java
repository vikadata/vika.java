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

package cn.vika.client.api.model;

import java.util.HashMap;
import java.util.Map;

/**
 * abstract model
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:18:39
 */
public abstract class AbstractModel {

    /**
     * request query param to map
     *
     * @param map param
     */
    public abstract void toMap(HashMap<String, String> map, String prefix);

    /**
     * format the request query uri
     *
     * @param map params map
     * @return uri
     */
    public String toTemplateUri(HashMap<String, String> map) {
        if (null == map || map.isEmpty()) {
            return "";
        }
        String uri = "";
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            Object value = entry.getValue();
            uri = setUriTemplate(uri, entry.getKey(), value);
        }
        return uri;
    }

    /**
     * this method is used to mark which parameters are binary type
     *
     * @return String[]
     */
    public String[] getBinaryParams() {
        return new String[0];
    }

    /**
     * this method is used to mark which parameters are binary type name
     *
     * @return String[]
     */
    public HashMap<String, byte[]> getBinaryParamNames() {
        return new HashMap<String, byte[]>(1);
    }

    /**
     * Valid only when it's a multipart request object.
     *
     * @return HashMap<String, byte[]>
     */
    public HashMap<String, byte[]> getMultipartRequestParams() {
        return new HashMap<>(1);
    }

    protected <V> String setUriTemplate(String param, String key, V value) {
        if (value != null) {
            if (!"".equals(param)) {
                return param + "&" + key + "=" + "{" + key + "}";
            } else {
                return "?" + key + "=" + "{" + key + "}";
            }
        }
        return "";
    }

    protected <V> void setParamSimple(HashMap<String, String> map, String key, V value) {
        if (value != null) {
            map.put(key, String.valueOf(value));
        }
    }

    protected <V> void setParamArraySimple(HashMap<String, String> map, String key, V[] values) {
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                setParamSimple(map, key + '.' + i, values[i]);
            }
        }
    }

    protected <V extends AbstractModel> void setParamObj(HashMap<String, String> map, String prefix, V obj) {
        if (obj != null) {
            obj.toMap(map, prefix);
        }
    }

    protected <V extends AbstractModel> void setParamArrayObj(HashMap<String, String> map, String key, V[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                setParamObj(map, key + '.' + i + '.', array[i]);
            }
        }
    }
}
