/*
 * MIT License
 *
 * Copyright (c) 2020 vika
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
import java.util.Map;

/**
 * handle URI
 *
 * @author Shawn Deng
 * @date 2020-10-28 18:43:45
 */
public interface UriHandler {

    /**
     * Expand the given URI template with a map of URI variables.
     *
     * @param uriTemplate  the URI template
     * @param uriVariables variable values
     * @return the created URI instance
     */
    URI format(String uriTemplate, Map<String, ?> uriVariables);

    /**
     * Expand the given URI template with an array of URI variables.
     *
     * @param uriTemplate  the URI template
     * @param uriVariables variable values
     * @return the created URI instance
     */
    URI format(String uriTemplate, Object... uriVariables);
}
