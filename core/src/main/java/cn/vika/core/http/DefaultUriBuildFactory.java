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
            Map<String, Object> map = new HashMap<>(defaultUriVariables);
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
