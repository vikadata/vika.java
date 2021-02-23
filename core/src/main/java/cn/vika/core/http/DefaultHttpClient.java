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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.vika.core.exception.HttpClientException;
import cn.vika.core.utils.AssertUtil;
import cn.vika.core.utils.IoUtil;
import cn.vika.core.utils.JacksonConverter;
import cn.vika.core.utils.RandomUtil;
import cn.vika.core.utils.StringUtil;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import sun.applet.AppletIOException;

import static cn.vika.core.http.HttpMediaType.APPLICATION_FORM_URLENCODED;
import static cn.vika.core.http.HttpMediaType.APPLICATION_JSON;
import static cn.vika.core.http.HttpMediaType.APPLICATION_OCTET_STREAM;
import static cn.vika.core.http.HttpMediaType.MULTIPART_FORM_DATA;

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
        }
        else {
            throw new IllegalArgumentException("not supported with the configured uriHandler.");
        }
    }

    private UriHandler getUriHandler() {
        return this.uriHandler;
    }

    public void setErrorHandler(HttpResponseErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public HttpResponseErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    public ResponseBodyHandler getResponseBodyHandler() {
        return responseBodyHandler;
    }

    public void setResponseBodyHandler(ResponseBodyHandler bodyHandler) {
        AssertUtil.notNull(errorHandler, "ResponseBodyHandler must not be null");
        this.responseBodyHandler = bodyHandler;
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
    public <T> T get(String urlTemplate, HttpHeader header, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T get(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.GET, requestWrapper, responseHandler, uriVariables);
    }

    // POST Request

    @Override
    public <T> T post(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.POST, requestWrapper, responseHandler);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T post(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.POST, requestWrapper, responseHandler, uriVariables);
    }

    // PUT Method

    @Override
    public <T> T put(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.PUT, requestWrapper, responseHandler);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T put(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PUT, requestWrapper, responseHandler, uriVariables);
    }

    // PATCH Method

    @Override
    public <T> T patch(URI uri, HttpHeader header, Object requestBody, Class<T> responseType) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.PATCH, requestWrapper, responseHandler);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T patch(String urlTemplate, HttpHeader header, Object requestBody, GenericTypeReference<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new RequestBodyWrapper(header, requestBody);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.PATCH, requestWrapper, responseHandler, uriVariables);
    }

    // DELETE Method

    @Override
    public <T> T delete(URI uri, HttpHeader header, Class<T> responseType) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(uri, HttpMethod.GET, requestWrapper, responseHandler);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, Class<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, Class<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType);
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Object... uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    @Override
    public <T> T delete(String urlTemplate, HttpHeader header, GenericTypeReference<T> responseType, Map<String, ?> uriVariables) throws HttpClientException {
        RequestWrapper requestWrapper = new OnlyHeaderWrapper(header);
        ResponseHandler<T> responseHandler = new ResponseBodyExtractHandler<>(responseType.getType());
        return execute(urlTemplate, HttpMethod.DELETE, requestWrapper, responseHandler, uriVariables);
    }

    // Core Method

    protected <T> T execute(URI uri, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler) throws HttpClientException {
        // do execute
        return doExecute(uri, method, requestWrapper, responseHandler);
    }

    protected <T> T execute(String urlTemplate, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler, Object... uriVariables) throws HttpClientException {
        // encode uri with object type variables
        URI expandedUri = getUriHandler().format(urlTemplate, uriVariables);
        // do execute
        return doExecute(expandedUri, method, requestWrapper, responseHandler);
    }

    protected <T> T execute(String urlTemplate, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler, Map<String, ?> uriVariables) throws HttpClientException {
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

        private final List<String> formDataMediaType = new ArrayList<>();

        public RequestBodyWrapper(HttpHeader header, Object requestBody) {
            super(header);
            this.requestBody = requestBody;

            this.formDataMediaType.add(MULTIPART_FORM_DATA);
            this.formDataMediaType.add(APPLICATION_FORM_URLENCODED);
            this.formDataMediaType.add(APPLICATION_OCTET_STREAM);
        }

        @Override
        public void wrapper(ClientHttpRequest request) throws IOException {
            super.wrapper(request);
            if (requestBody != null) {
                Class<?> requestBodyClass = requestBody.getClass();
                HttpHeader requestHeaders = request.getHeaders();
                String requestContentType = requestHeaders.getContentType();
                if (isFormData(requestBodyClass, requestContentType)) {
                    if (isMultiPart((FormDataMap) requestBody, requestContentType)) {
                        writeMultiPart(request, (FormDataMap) requestBody);
                    }
                    else {
                        writeForm(request, (FormDataMap) requestBody);
                    }
                }
                else if (isJsonBody(requestBodyClass, requestContentType)) {
                    request.getHeaders().setContentType(HttpMediaType.APPLICATION_JSON);
                    byte[] content = JacksonConverter.toJsonBytes(requestBody);
                    request.getBody().write(content);
                }
                else {
                    // throw read exception
                    throw new HttpClientException("Can not read :" + requestBodyClass.getName() + " and content type " + requestContentType);
                }
            }
        }

        private boolean isFormData(Class<?> clazz, String contentType) {
            if (!FormDataMap.class.isAssignableFrom(clazz)) {
                return false;
            }
            if (contentType == null || HttpHeader.ALL.equals(contentType)) {
                return true;
            }
            for (String mediaType : formDataMediaType) {
                if (mediaType.equalsIgnoreCase(contentType)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isJsonBody(Class<?> clazz, String contentType) {
            if (clazz == null) {
                return false;
            }
            if (contentType == null || HttpHeader.ALL.equals(contentType)) {
                return true;
            }
            return contentType.equalsIgnoreCase(APPLICATION_JSON);
        }

        private boolean isMultiPart(FormDataMap mapData, String contentType) {
            if (contentType != null) {
                return contentType.startsWith("multipart");
            }
            for (Object value : mapData.values()) {
                if (value != null && !(value instanceof String)) {
                    return true;
                }
            }
            return false;
        }

        private void writeMultiPart(ClientHttpRequest request, FormDataMap parts) throws IOException {
            Map<String, String> parameters = new LinkedHashMap<>();
            parameters.put("charset", StandardCharsets.UTF_8.name());
            byte[] boundary = RandomUtil.generateMultipartBoundary();
            parameters.put("boundary", new String(boundary, StandardCharsets.US_ASCII));
            StringBuilder contentType = new StringBuilder(MULTIPART_FORM_DATA);
            parameters.forEach((key, value) -> contentType.append(';').append(key).append("=").append(value));
            request.getHeaders().setContentType(contentType.toString());
            OutputStream body = request.getBody();
            writeParts(body, parts, boundary);
            writeEnd(body, boundary);
            // don't forget flush body content
            body.flush();
        }

        private void writeForm(ClientHttpRequest request, FormDataMap formData) throws IOException {
            StringBuilder builder = new StringBuilder();
            formData.forEach((name, value) -> {
                if (name == null) {
                    AssertUtil.isTrue(value != null, "Null name in form data: " + formData);
                    return;
                }
                try {
                    if (builder.length() != 0) {
                        builder.append('&');
                    }
                    builder.append(URLEncoder.encode(name, StandardCharsets.UTF_8.name()));
                    if (value != null) {
                        builder.append('=');
                        builder.append(URLEncoder.encode(String.valueOf(value), StandardCharsets.UTF_8.name()));
                    }
                }
                catch (UnsupportedEncodingException ex) {
                    throw new IllegalStateException(ex);
                }
            });
            byte[] bytes = builder.toString().getBytes(StandardCharsets.UTF_8);
            request.getHeaders().setContentLength(bytes.length);
            IoUtil.copy(bytes, request.getBody());
        }

        private void writeParts(OutputStream os, Map<String, Object> parts, byte[] boundary) throws IOException {
            for (Entry<String, Object> entry : parts.entrySet()) {
                Object part = entry.getValue();
                if (part != null) {
                    // write boundary
                    writeBoundary(os, boundary);
                    // write multi part
                    writePart(os, entry.getKey(), part);
                    // write new line
                    writeNewLine(os);
                }
            }
        }

        private void writePart(OutputStream os, String name, Object partBody) throws IOException {
            if (partBody == null) {
                throw new IllegalStateException("Empty body for part '" + name + "'");
            }
            HttpHeader httpHeader = HttpHeader.EMPTY;
            if (partBody instanceof File) {
                File file = (File) partBody;
                httpHeader.setContentDispositionFormData(name, file.getName());
                defaultHeadersIfFileType(httpHeader, file);
                writeHeaders(os, httpHeader);
                byte[] data = Files.readAllBytes(file.toPath());
                IoUtil.copy(data, os);
            }
            else if (partBody instanceof ResourceLoader) {
                ResourceLoader loader = (ResourceLoader) partBody;
                httpHeader.setContentDispositionFormData(name, loader.getName());
                defaultHeadersIfResourceLoader(httpHeader, loader);
                writeHeaders(os, httpHeader);
                IoUtil.copy(loader.getInputStream(), os);
            } else {
                throw new AppletIOException("not support part param key " + name);
            }
        }

        private void writeHeaders(OutputStream os, HttpHeader headers) throws IOException {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                byte[] headerName = entry.getKey().getBytes(StandardCharsets.UTF_8);
                for (String headerValueString : entry.getValue()) {
                    byte[] headerValue = headerValueString.getBytes(StandardCharsets.UTF_8);
                    os.write(headerName);
                    os.write(':');
                    os.write(' ');
                    os.write(headerValue);
                    writeNewLine(os);
                }
            }
            writeNewLine(os);
        }

        private void writeBoundary(OutputStream os, byte[] boundary) throws IOException {
            os.write('-');
            os.write('-');
            os.write(boundary);
            writeNewLine(os);
        }

        private void writeNewLine(OutputStream os) throws IOException {
            os.write('\r');
            os.write('\n');
        }

        private void writeEnd(OutputStream os, byte[] boundary) throws IOException {
            os.write('-');
            os.write('-');
            os.write(boundary);
            os.write('-');
            os.write('-');
            writeNewLine(os);
        }

        private void defaultHeadersIfFileType(HttpHeader headers, File file) throws IOException {
            if (headers.getContentType() == null) {
                String fileContentType = getFileMimeType(file);
                if (fileContentType != null) {
                    headers.setContentType(fileContentType);
                }
            }
            // Set Content-Length
            if (headers.getContentLength() < 0 && !headers.containsKey(HttpHeader.TRANSFER_ENCODING)) {
                Long contentLength = getContentLength(file);
                if (contentLength != null) {
                    headers.setContentLength(contentLength);
                }
            }
        }

        private void defaultHeadersIfResourceLoader(HttpHeader headers, ResourceLoader loader) throws IOException {
            if (headers.getContentType() == null) {
                String fileContentType = getFileMimeType(loader);
                if (fileContentType != null) {
                    headers.setContentType(fileContentType);
                }
            }
            // Set Content-Length
            if (headers.getContentLength() < 0 && !headers.containsKey(HttpHeader.TRANSFER_ENCODING)) {
                Long contentLength = getContentLength(loader);
                if (contentLength != null) {
                    headers.setContentLength(contentLength);
                }
            }
        }

        private String getFileMimeType(File file) throws IOException {
            try {
                MagicMatch match = Magic.getMagicMatch(file, false);
                return match.getMimeType();
            }
            catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
                return null;
            }
        }

        private String getFileMimeType(ResourceLoader loader) throws IOException {
            try {
                MagicMatch match = Magic.getMagicMatch(loader.readBytes(), false);
                return match.getMimeType();
            }
            catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
                return null;
            }
        }

        private Long getContentLength(File file) throws IOException {
            long contentLength = file.length();
            return (contentLength < 0 ? null : contentLength);
        }

        private Long getContentLength(ResourceLoader loader) throws IOException {
            long contentLength = loader.contentLength();
            return (contentLength < 0 ? null : contentLength);
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
        public T extractData(ClientHttpResponse response, ResponseBodyHandler handler) throws IOException {
            if (!hasBody(response) || hasEmptyBody(response)) {
                return null;
            }
            InputStream body = getBody(response);
            byte[] content = IoUtil.copyToByteArray(body);
            if (handler != null) {
                handler.handleBody(content);
            }
            return JacksonConverter.toGenericBean(content, responseType);
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
                }
                else {
                    body.reset();
                    return false;
                }
            }
            else {
                this.pushbackInputStream = new PushbackInputStream(body);
                int b = this.pushbackInputStream.read();
                if (b == -1) {
                    return true;
                }
                else {
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
