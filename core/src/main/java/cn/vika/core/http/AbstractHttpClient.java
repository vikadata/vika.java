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
import java.net.URI;

import cn.vika.core.utils.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * abstract http client class
 *
 * @author Shawn Deng
 * @date 2020-10-27 17:59:37
 */
public abstract class AbstractHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpClient.class);

    /**
     * Default implementation OkhttpClient
     */
    private ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory();

    /**
     * Set the request factory that this accessor uses for obtaining client request handles.
     * <p>The default is a {@link OkHttpClientHttpRequestFactory} based on the OKHttp libraries.
     *
     * @param requestFactory http request client factory
     */
    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        AssertUtil.notNull(requestFactory, "HttpRequestFactory must not be null");
        this.requestFactory = requestFactory;
    }

    /**
     * Return the request factory that this accessor uses for obtaining client request handles.
     */
    public ClientHttpRequestFactory getRequestFactory() {
        return this.requestFactory;
    }

    /**
     * Create a new {@link ClientHttpRequest} via this template's {@link ClientHttpRequestFactory}.
     *
     * @param uri    the URL to connect to
     * @param method the HTTP method to execute (GET, POST, etc)
     * @return the created request
     * @throws IOException in case of I/O errors
     * @see #getRequestFactory()
     * @see ClientHttpRequestFactory#createRequest(URI, HttpMethod)
     */
    protected ClientHttpRequest createRequest(URI uri, HttpMethod method) throws IOException {
        // create request by factory
        return getRequestFactory().createRequest(uri, method);
    }

    protected <T> T doExecute(URI uri, HttpMethod method, RequestWrapper requestWrapper, ResponseHandler<T> responseHandler) {
        // asset param non null
        AssertUtil.notNull(uri, "URI is required");
        AssertUtil.notNull(method, "HttpMethod is required");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Http Request: {}", uri.toString());
        }
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
        }
        catch (IOException ex) {
            String resource = uri.toString();
            String query = uri.getRawQuery();
            resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
            throw new RuntimeException("I/O error on " + method.name() + " request for \"" + resource + "\": " + ex.getMessage(), ex);
        }
        finally {
            // close
            if (response != null) {
                response.close();
            }
        }
    }
}
