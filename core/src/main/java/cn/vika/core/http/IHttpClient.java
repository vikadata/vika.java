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
import java.util.Map;

/**
 * a basic set of http request interface
 *
 * @author Shawn Deng
 * @date 2020-11-13 15:57:48
 */
public interface IHttpClient {

    // GET

    /**
     * Retrieve a representation by doing a GET on the URL .
     * The response (if any) is converted and returned.
     *
     * @param uri          the URL Connection
     * @param header       the request header
     * @param responseType the type of the return value
     * @return the converted object
     */
    <T> T get(URI uri, HttpHeader header, Class<T> responseType);

    /**
     * Retrieve a representation by doing a GET on the specified URL.
     * The response (if any) is converted and returned.
     * <p>URI Template variables are expanded using the given URI variables, if any.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand the template
     * @return the converted object
     */
    <T> T get(String urlTemplate, HttpHeader header, Class<T> responseType, Object... uriVariables);

    /**
     * Retrieve a representation by doing a GET on the URI client.
     * The response (if any) is converted and returned.
     * <p>URI Template variables are expanded using the given map.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param responseType the type of the return value
     * @param uriVariables the map containing variables for the URI template
     * @return the converted object
     */
    <T> T get(String urlTemplate, HttpHeader header, Class<T> responseType, Map<String, ?> uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;List&lt;MyBean&gt;&gt; reference =
     *     new GenericTypeReference&lt;List&lt;MyBean&gt;&gt;() {};
     *
     * List&lt;MyBean&gt;&gt; response = client.get(&quot;/get/me&quot;, HttpHeader.EMPTY, reference, uriVariables);
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T get(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Map<String, ?> uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;List&lt;MyBean&gt;&gt; reference =
     *     new GenericTypeReference&lt;List&lt;MyBean&gt;&gt;() {};
     *
     * List&lt;MyBean&gt;&gt; response = client.get(&quot;/get/me&quot;, HttpHeader.EMPTY, reference, uriVariables);
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T get(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Object... uriVariables);

    // POST

    /**
     * Create a new resource by POSTing the given object to the URL,
     * and returns the representation found in the response.
     *
     * @param uri          the URL Connection
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @return the converted object
     */
    <T> T post(URI uri, HttpHeader header, Object requestBody, Class<T> responseType);

    /**
     * Create a new resource by POSTing the given object to the URI template,
     * and returns the representation found in the response.
     * <p>URI Template variables are expanded using the given URI variables, if any.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand the template
     * @return the converted object
     */
    <T> T post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables);

    /**
     * Create a new resource by POSTing the given object to the URI template,
     * and returns the representation found in the response.
     * <p>URI Template variables are expanded using the given URI variables, if any.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand the template
     * @return the converted object
     */
    <T> T post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;List&lt;MyBean&gt;&gt; reference =
     *     new GenericTypeReference&lt;List&lt;MyBean&gt;&gt;() {};
     *
     * User user = new User();
     * user.setName(&quot;jack&quot;);
     *
     * List&lt;MyBean&gt;&gt; response =
     *     client.post(&quot;/get/me&quot;, HttpHeader.EMPTY,
     *     user, reference, uriVariables);
     *
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T post(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;List&lt;MyBean&gt;&gt; reference =
     *     new GenericTypeReference&lt;List&lt;MyBean&gt;&gt;() {};
     *
     * User user = new User();
     * user.setName(&quot;jack&quot;);
     *
     * List&lt;MyBean&gt;&gt; response =
     *     client.post(&quot;/get/me&quot;, HttpHeader.EMPTY,
     *     user, reference, uriVariables);
     *
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T post(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables);

    // PUT

    /**
     * Create a new resource by the given object to the URL,
     * and returns the representation found in the response.
     *
     * @param uri          the URL Connection
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @return the converted object
     */
    <T> T put(URI uri, HttpHeader header, Object requestBody, Class<T> responseType);

    /**
     * Create a new resource by the given object to the URI template,
     * and returns the representation found in the response.
     * <p>URI Template variables are expanded using the given URI variables, if any.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand the template
     * @return the converted object
     */
    <T> T put(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables);

    /**
     * Create a new resource by the given object to the URI template,
     * and returns the representation found in the response.
     * <p>URI Template variables are expanded using the given URI variables, if any.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand the template
     * @return the converted object
     */
    <T> T put(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;List&lt;MyBean&gt;&gt; reference =
     *     new GenericTypeReference&lt;List&lt;MyBean&gt;&gt;() {};
     *
     * User user = new User();
     * user.setName(&quot;jack&quot;);
     *
     * List&lt;MyBean&gt;&gt; response =
     *     client.put(&quot;/get/me&quot;, HttpHeader.EMPTY,
     *     user, reference, uriVariables);
     *
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T put(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;List&lt;MyBean&gt;&gt; reference =
     *     new GenericTypeReference&lt;List&lt;MyBean&gt;&gt;() {};
     *
     * User user = new User();
     * user.setName(&quot;jack&quot;);
     *
     * List&lt;MyBean&gt;&gt; response =
     *     client.put(&quot;/get/me&quot;, HttpHeader.EMPTY,
     *     user, reference, uriVariables);
     *
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T put(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables);

    /**
     * Create a new resource by the given object to the URL,
     * and returns the representation found in the response.
     *
     * @param uri          the URL Connection
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @return the converted object
     */
    <T> T patch(URI uri, HttpHeader header, Object requestBody, Class<T> responseType);

    /**
     * Create a new resource by the given object to the URI template,
     * and returns the representation found in the response.
     * <p>URI Template variables are expanded using the given URI variables, if any.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand the template
     * @return the converted object
     */
    <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables);

    /**
     * Create a new resource by the given object to the URI template,
     * and returns the representation found in the response.
     * <p>URI Template variables are expanded using the given URI variables, if any.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand the template
     * @return the converted object
     */
    <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;List&lt;MyBean&gt;&gt; reference =
     *     new GenericTypeReference&lt;List&lt;MyBean&gt;&gt;() {};
     *
     * User user = new User();
     * user.setName(&quot;jack&quot;);
     *
     * List&lt;MyBean&gt;&gt; response =
     *     client.patch(&quot;/get/me&quot;, HttpHeader.EMPTY,
     *     user, reference, uriVariables);
     *
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;List&lt;MyBean&gt;&gt; reference =
     *     new GenericTypeReference&lt;List&lt;MyBean&gt;&gt;() {};
     *
     * User user = new User();
     * user.setName(&quot;jack&quot;);
     *
     * List&lt;MyBean&gt;&gt; response =
     *     client.patch(&quot;/get/me&quot;, HttpHeader.EMPTY,
     *     user, reference, uriVariables);
     *
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param requestBody  the Object to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables);

    /**
     * Create a new resource by the given object to the URL,
     * and returns the representation found in the response.
     *
     * @param uri          the URL Connection
     * @param header       the request header
     * @param responseType the type of the return value
     * @return the converted object
     */
    <T> T delete(URI uri, HttpHeader header, Class<T> responseType);

    /**
     * Create a new resource by the given object to the URI template,
     * and returns the representation found in the response.
     * <p>URI Template variables are expanded using the given URI variables, if any.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand the template
     * @return the converted object
     */
    <T> T delete(String urlTemplate, HttpHeader header, Class<T> responseType, Object... uriVariables);

    /**
     * Create a new resource by the given object to the URI template,
     * and returns the representation found in the response.
     * <p>URI Template variables are expanded using the given URI variables, if any.
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand the template
     * @return the converted object
     */
    <T> T delete(String urlTemplate, HttpHeader header, Class<T> responseType, Map<String, ?> uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;Void&gt; reference =
     *     new GenericTypeReference&lt;Void&gt;() {};
     *
     * client.delete(&quot;/user&quot;, HttpHeader.EMPTY,
     *     reference, uriVariables);
     *
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T delete(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Object... uriVariables);

    /**
     * Execute the HTTP method to the given URL template, writing the given
     * request entity to the request, and returns the response as any
     * The given {@link GenericTypeReference} is used to pass generic type information:
     * <pre class="code">
     * GenericTypeReference&lt;Void&gt; reference =
     *     new GenericTypeReference&lt;Void&gt;() {};
     *
     * client.delete(&quot;/user&quot;, HttpHeader.EMPTY,
     *     reference, uriVariables);
     *
     * </pre>
     *
     * @param urlTemplate  the URL Template
     * @param header       the request header
     * @param responseType the type of the return value
     * @param uriVariables the variables to expand in the template
     * @return the converted object from reference
     */
    <T> T delete(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Map<String, ?> uriVariables);
}
