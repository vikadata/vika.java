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

import com.sun.tools.javac.util.Assert;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * SDK Request Client
 *
 * @author Shawn Deng
 * @date 2020-10-26 18:57:44
 */
public class DefaultHttpClient extends BaseHttpClient {

    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    public DefaultHttpClient() {
    }

    public DefaultHttpClient(ClientHttpRequestFactory requestFactory) {
        this();
        super.setRequestFactory(requestFactory);
    }

    public <T> T get(String url, Class<T> responseType) {
        return null;
    }

    public <T> T get(String url, Class<T> responseType, Object... uriVariables) {
        return null;
    }

    public <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        return null;
    }

    public <T> T execute(String url, HttpMethod method, Object... uriVariables) {

        return null;
    }

    public <T> T execute(URI url, HttpMethod method) {

        return null;
    }

    protected <T> T doExecute(URI url, HttpMethod method) {
        // asset param
        Assert.checkNonNull(url, "URI is required");
        Assert.checkNonNull(method, "HttpMethod is required");
        ClientHttpResponse response = null;
        try {
            // create Request instance
            ClientHttpRequest request = createRequest(url, method);
            // execute request
            response = request.execute();
            handleResponse(url, method, response);
            return null;
//            return (responseExtractor != null ? responseExtractor.extractData(response) : null);
        } catch (IOException ex) {
            String resource = url.toString();
            String query = url.getRawQuery();
            resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
            return null;
//            throw new ResourceAccessException("I/O error on " + method.name() + " request for \"" + resource + "\": " + ex.getMessage(), ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private static <T> T nonNull(T result) {
        Assert.check(result != null, "No Response Result Back!");
        return result;
    }

    protected void handleResponse(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        ResponseErrorHandler errorHandler = getErrorHandler();
        boolean hasError = errorHandler.hasError(response);
        if (hasError) {
            errorHandler.handleError(url, method, response);
        }
    }

    public ResponseErrorHandler getErrorHandler() {
        return this.errorHandler;
    }
}
