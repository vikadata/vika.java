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

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.vika.core.utils.StringUtil;

/**
 * default uri template build factory implementation
 *
 * @author Shawn Deng
 * @date 2020-11-05 23:27:08
 */
public class DefaultUriBuildFactory implements UriHandler {

    private String baseUri;

    private final Map<String, Object> defaultUriVariables = new HashMap<>();

    public DefaultUriBuildFactory() {
    }

    public DefaultUriBuildFactory(String baseUri) {
        this.baseUri = StringUtil.isEmpty(baseUri) ? "" : baseUri;
    }

    @Override
    public URI format(String uriTemplate, Map<String, ?> uriVariables) {
        // Verify Uri
        if (checkUrl(uriTemplate)) {
            throw new IllegalArgumentException("Error Request Url");
        }
        // set value
        if (!defaultUriVariables.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.putAll(defaultUriVariables);
            map.putAll(uriVariables);
            uriVariables = map;
        }
        String url = StringUtil.format(uriTemplate, uriVariables);
        return URI.create(baseUri + url);
    }

    @Override
    public URI format(String uriTemplate, Object... uriVariables) {
        // Verify Uri
        if (checkUrl(uriTemplate)) {
            throw new IllegalArgumentException("Error Request Url");
        }
        // set value
        if (!defaultUriVariables.isEmpty() && isEmptyArray(uriVariables)) {
            return format(uriTemplate, Collections.emptyMap());
        }
        String url = StringUtil.format(uriTemplate, uriVariables);
        return URI.create(baseUri + url);
    }

    private boolean checkUrl(String uriString) {
        return !uriString.startsWith("/");
    }

    private boolean isEmptyArray(Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * Provide default URI variable values to use when expanding URI templates
     * with a Map of variables.
     *
     * @param defaultUriVariables default URI variable values
     */
    public void setDefaultUriVariables(Map<String, ?> defaultUriVariables) {
        this.defaultUriVariables.clear();
        if (defaultUriVariables != null) {
            this.defaultUriVariables.putAll(defaultUriVariables);
        }
    }

    /**
     * Return the configured default URI variable values.
     */
    public Map<String, ?> getDefaultUriVariables() {
        return Collections.unmodifiableMap(this.defaultUriVariables);
    }
}
