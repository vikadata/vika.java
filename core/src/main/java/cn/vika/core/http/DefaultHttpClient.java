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

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;

import cn.vika.core.exception.HttpClientException;
import cn.vika.core.utils.JacksonConverter;
import cn.vika.core.utils.StringUtil;

/**
 * Http Client implementation IHttpClient interface
 *
 * @author Shawn Deng
 * @date 2020-10-26 18:57:44
 */
public class DefaultHttpClient extends AbstractHttpClient implements IHttpClient {

    private final UriHandler uriHandler;

    private final HttpHeader defaultHeaders = HttpHeader.EMPTY;

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
        defaultHeaders.add(headerName, headerValue);
    }

    public void addGlobalHeader(HttpHeader headers) {
        defaultHeaders.putAll(headers);
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

    public void setErrorHandler(HttpResponseErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    // GET Request

    @Override
    public <T> T get(URI uri, HttpHeader header, Class<T> responseType) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.GET, requestWrapper, responseHandler);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, Class<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Object... uriVariables)throws HttpClientException  {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    // POST Request

    @Override
    public <T> T post(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.POST, requestWrapper, responseHandler);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables)throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables)throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    // PUT Method

    @Override
    public <T> T put(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.PUT, requestWrapper, responseHandler);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables)throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables)throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    // PATCH Method

    @Override
    public <T> T patch(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.PATCH, requestWrapper, responseHandler);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    // DELETE Method

    @Override
    public <T> T delete(URI uri, HttpHeader header, Class<T> responseType) throws HttpClientException{
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.GET, requestWrapper, responseHandler);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, Class<T> responseType, Object... uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Object... uriVariables) throws HttpClientException{
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Map<String, ?> uriVariables)throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    // UPLOAD

    @Override
    public <T> T upload(String urlTemplate, HttpHeader header, byte[] requestBody, GenericTypeReference<T> responseType) throws HttpClientException{
        RequestWrapper requestWrapper = new RequestMultipartWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler);
    }

    // Core Method

    protected <T> T execute(URI uri, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler) throws HttpClientException{
        // do execute
        return doExecute(uri, method, requestWrapper, responseHandler);
    }

    protected <T> T execute(String urlTemplate, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler, Object... uriVariables) throws HttpClientException{
        // encode uri with object type variables
        URI expandedUri = getUriHandler().format(urlTemplate, uriVariables);
        // do execute
        return doExecute(expandedUri, method, requestWrapper, responseHandler);
    }

    protected <T> T execute(String urlTemplate, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler, Map<String, ?> uriVariables) throws HttpClientException{
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
                defaultHeaders.forEach(httpHeader::put);
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
                // System.out.println(new String(content, StandardCharsets.UTF_8));
                request.getBody().write(content);
            }
        }
    }

    private class RequestMultipartWrapper extends OnlyHeaderWrapper {

        private final byte[] requestBody;

        public RequestMultipartWrapper(HttpHeader header, byte[] requestBody) {
            super(header);
            this.requestBody = requestBody;
        }

        @Override
        public void wrapper(ClientHttpRequest request) throws IOException {
            super.wrapper(request);
            if (requestBody != null) {
                request.getBody().write(requestBody);
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
