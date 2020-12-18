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
    /**
     * upload a new resource by POSTing the given object to the URL,
     * and returns the representation found in the response.
     *
     * @param urlTemplate the URL Template
     * @param header the request header
     * @param requestBody the byte[] to be sent (may be {@code null})
     * @param responseType the type of the return value
     * @return the converted object
     */
    <T> T upload(String urlTemplate, HttpHeader header, byte[] requestBody, GenericTypeReference<T> responseType);
}
