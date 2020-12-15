package cn.vika.api.model;

import java.util.Arrays;
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
     * @param map
     */
    public abstract void toMap(HashMap<String, String> map);

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

    protected <V> String setUriArrayTemplate(String param, String key, V[] values) {
        if (values != null) {
            return setUriTemplate(param, key, Arrays.toString(values));
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
                setParamSimple(map, key + i, values[i]);
            }
        }
    }

}
