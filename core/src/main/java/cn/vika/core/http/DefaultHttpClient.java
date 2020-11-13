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

import cn.vika.core.model.HttpResult;
import cn.vika.core.utils.AssertUtil;
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
 * Http Client based on Vikadata API response schema
 *
 * @author Shawn Deng
 * @date 2020-10-26 18:57:44
 */
public class DefaultHttpClient extends AbstractHttpClient implements IHttpClient {

    private UriHandler uriHandler;

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
    public <T> HttpResult<T> get(URI uri, HttpHeader header, Class<T> responseType) {
        RequestWrapper requestWrapper = new HeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.GET, requestWrapper, responseHandler);
    }

    public <T> HttpResult<T> get(String urlTemplate, HttpHeader header, Class<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new HeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    public <T> HttpResult<T> get(String urlTemplate, HttpHeader header, Class<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new HeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    // POST Request

    public <T> HttpResult<T> post(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) {
        RequestWrapper requestWrapper = new BodyRequestWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.POST, requestWrapper, responseHandler);
    }

    public <T> HttpResult<T> post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables) {
        RequestWrapper requestWrapper = new BodyRequestWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    public <T> HttpResult<T> post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        RequestWrapper requestWrapper = new BodyRequestWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    // common request execute method

    protected <T> HttpResult<T> execute(URI uri, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler) {
        // do execute
        return doExecute(uri, method, requestWrapper, responseHandler);
    }

    protected <T> HttpResult<T> execute(String urlTemplate, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler, Object... uriVariables) {
        // encode uri with object type variables
        URI expandedUri = getUriHandler().format(urlTemplate, uriVariables);
        // do execute
        return doExecute(expandedUri, method, requestWrapper, responseHandler);
    }

    protected <T> HttpResult<T> execute(String urlTemplate, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler, Map<String, ?> uriVariables) {
        // encode uri with map type variables
        URI expandedUri = getUriHandler().format(urlTemplate, uriVariables);
        // do execute
        return doExecute(expandedUri, method, requestWrapper, responseHandler);
    }

    protected <T> HttpResult<T> doExecute(URI uri, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler) {
        // asset param non null
        AssertUtil.notNull(uri, "URI is required");
        AssertUtil.notNull(method, "HttpMethod is required");
        ClientHttpResponse response = null;
        try {
            // create Request instance
            ClientHttpRequest request = createRequest(uri, method);
            // wrapper request like header、requestBody
            if (requestWrapper != null) {
                requestWrapper.wrapper(request);
            }
            // execute request
            response = request.execute();
            // in case of response interceptor
            return (responseHandler != null ? responseHandler.extractData(response) : null);
        } catch (IOException ex) {
            String resource = uri.toString();
            String query = uri.getRawQuery();
            resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
            throw new RuntimeException("I/O error on " + method.name() + " request for \"" + resource + "\": " + ex.getMessage(), ex);
        } finally {
            // close
            if (response != null) {
                response.close();
            }
        }
    }

    private class HeaderWrapper implements RequestWrapper {

        private final HttpHeader header;

        public HeaderWrapper(HttpHeader header) {
            this.header = header;
        }

        @Override
        public void wrapper(ClientHttpRequest request) {
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

    private class BodyRequestWrapper extends HeaderWrapper {

        private final Object requestBody;

        public BodyRequestWrapper(HttpHeader header, Object requestBody) {
            super(header);
            this.requestBody = requestBody;
        }

        @Override
        public void wrapper(ClientHttpRequest request) {
            super.wrapper(request);
            if (requestBody != null) {

            }
        }
    }

    private static class ResponseBodyExtractHandler<T> implements ResponseHandler<T> {

        private final Class<T> responseClass;

        private PushbackInputStream pushbackInputStream;

        public ResponseBodyExtractHandler(Type responseType) {
            this.responseClass = (responseType instanceof Class ? (Class<T>) responseType : null);
        }

        @Override
        public HttpResult<T> extractData(ClientHttpResponse response) throws IOException {
            if (!hasBody(response) || hasEmptyBody(response)) {
                return null;
            }
            InputStream body = getBody(response);
            return JacksonConverter.toGenericBean(body, HttpResult.class, responseClass);
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
