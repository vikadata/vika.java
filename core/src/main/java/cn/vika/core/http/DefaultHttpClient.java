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

import cn.vika.core.utils.JacksonConverter;
import cn.vika.core.utils.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Http Client implementation IHttpClient interface
 *
 * @author Shawn Deng
 * @date 2020-10-26 18:57:44
 */
public class DefaultHttpClient extends AbstractHttpClient implements IHttpClient {

    private final UriHandler uriHandler;

    private final Map<String, String> defaultHeaders = new HashMap<>();

    public DefaultHttpClient() {
        // init default uri handler
        this.uriHandler = new DefaultUriBuildFactory();
    }

    public DefaultHttpClient(String baseUri) {
        // init default uri handler
        if (baseUri.endsWith(StringUtil.SLASH)) {
            baseUri = baseUri.substring(0, baseUri.length() - 2);
        }
        this.uriHandler = new DefaultUriBuildFactory(baseUri);
    }

    /**
     * construct of custom request factory
     * default is okhttp request
     *
     * @param requestFactory custom request factory
     */
    public DefaultHttpClient(ClientHttpRequestFactory requestFactory) {
        this();
        setRequestFactory(requestFactory);
    }

    public void addGlobalHeader(String headerName, String headerValue) {
        defaultHeaders.put(headerName, headerValue);
    }

    public void setDefaultUriVariables(Map<String, ?> uriVars) {
        if (this.uriHandler instanceof DefaultUriBuildFactory) {
            ((DefaultUriBuildFactory) this.uriHandler).setDefaultUriVariables(uriVars);
        } else {
            throw new IllegalArgumentException("not supported with the configured uriHandler.");
        }
    }

    private UriHandler getUriHandler() {
        return this.uriHandler;
    }

    // GET Request

    @Override
    public <T> T get(URI uri, HttpHeader header, Class<T> responseType) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.GET, requestWrapper, responseHandler);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, Class<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, Class<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    // POST Request

    @Override
    public <T> T post(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.POST, requestWrapper, responseHandler);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    // PUT Method

    @Override
    public <T> T put(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.PUT, requestWrapper, responseHandler);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    // PATCH Method

    @Override
    public <T> T patch(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.PATCH, requestWrapper, responseHandler);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    // DELETE Method

    @Override
    public <T> T delete(URI uri, HttpHeader header, Class<T> responseType) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.GET, requestWrapper, responseHandler);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, Class<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, Class<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    // Core Method

    protected <T> T execute(URI uri, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler) {
        // do execute
        return doExecute(uri, method, requestWrapper, responseHandler);
    }

    protected <T> T execute(String urlTemplate, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler, Object... uriVariables) {
        // encode uri with object type variables
        URI expandedUri = getUriHandler().format(urlTemplate, uriVariables);
        // do execute
        return doExecute(expandedUri, method, requestWrapper, responseHandler);
    }

    protected <T> T execute(String urlTemplate, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler, Map<String, ?> uriVariables) {
        // encode uri with map type variables
        URI expandedUri = getUriHandler().format(urlTemplate, uriVariables);
        // do execute
        return doExecute(expandedUri, method, requestWrapper, responseHandler);
    }

    private class OnlyHeaderWrapper implements RequestWrapper {

        private final HttpHeader header;

        public OnlyHeaderWrapper(HttpHeader header) {
            this.header = header;
        }

        @Override
        public void wrapper(ClientHttpRequest request) throws IOException {
            if (!this.header.isEmpty()) {
                HttpHeader httpHeader = request.getHeaders();
                this.header.forEach(httpHeader::put);
            }

            if (!defaultHeaders.isEmpty()) {
                HttpHeader httpHeader = request.getHeaders();
                defaultHeaders.forEach((key, value) -> httpHeader.put(key, Collections.singletonList(value)));
            }
        }
    }

    private class RequestBodyWrapper extends OnlyHeaderWrapper {

        private final Object requestBody;

        public RequestBodyWrapper(HttpHeader header, Object requestBody) {
            super(header);
            this.requestBody = requestBody;
        }

        @Override
        public void wrapper(ClientHttpRequest request) throws IOException {
            super.wrapper(request);
            if (requestBody != null) {
                request.getHeaders().setContentType(HttpMediaType.APPLICATION_JSON);
                byte[] content = JacksonConverter.toJsonBytes(requestBody);
                request.getBody().write(content);
            }
        }
    }

    private static class ResponseBodyExtractHandler<T> implements ResponseHandler<T> {

        private final Type responseType;

        // never close
        private PushbackInputStream pushbackInputStream;

        public ResponseBodyExtractHandler(Type responseType) {
            this.responseType = responseType;
        }

        @Override
        public T extractData(ClientHttpResponse response) throws IOException {
            if (!hasBody(response) || hasEmptyBody(response)) {
                return null;
            }
            InputStream body = getBody(response);
            return JacksonConverter.toGenericBean(body, responseType);
        }

        private boolean hasBody(ClientHttpResponse response) throws IOException {
            // whether is ok response
            HttpStatus status = HttpStatus.resolve(response.getRawStatusCode());
            if (status != null && (status.is1xxInformational() || status == HttpStatus.NO_CONTENT || status == HttpStatus.NOT_MODIFIED)) {
                return false;
            }
            // whether has content
            return response.getHeaders().getContentLength() != 0;
        }

        public boolean hasEmptyBody(ClientHttpResponse response) throws IOException {
            InputStream body = response.getBody();
            // Per contract body shouldn't be null, but check anyway..
            if (body == null) {
                return true;
            }
            if (body.markSupported()) {
                body.mark(1);
                if (body.read() == -1) {
                    return true;
                } else {
                    body.reset();
                    return false;
                }
            } else {
                this.pushbackInputStream = new PushbackInputStream(body);
                int b = this.pushbackInputStream.read();
                if (b == -1) {
                    return true;
                } else {
                    this.pushbackInputStream.unread(b);
                    return false;
                }
            }
        }

        private InputStream getBody(ClientHttpResponse response) throws IOException {
            return (this.pushbackInputStream != null ? this.pushbackInputStream : response.getBody());
        }
    }
}
