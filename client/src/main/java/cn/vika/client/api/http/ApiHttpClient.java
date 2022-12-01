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

package cn.vika.client.api.http;

import cn.vika.core.http.ClientHttpRequestFactory;
import cn.vika.core.http.DefaultHttpClient;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.http.OkHttpClientHttpRequestFactory;

/**
 * api http client
 * defined the host and timeout
 *
 * @author Zoe Zheng
 * @date 2020-12-15 18:03:44
 */
public class ApiHttpClient {

    public enum ApiVersion {
        V1;

        public String getApiNamespace() {
            return ("/fusion/" + name().toLowerCase());
        }
    }

    public static final int DEFAULT_PER_PAGE = 100;

    public static final int DEFAULT_CONNECT_TIMEOUT = 60000;

    public static final int DEFAULT_READ_TIMEOUT = 60000;

    public static final int DEFAULT_CALL_TIMEOUT = 60000;

    public static final int DEFAULT_WRITE_TIMEOUT = 60000;

    private final DefaultHttpClient defaultHttpClient;

    private int defaultPerPage = DEFAULT_PER_PAGE;

    private Integer connectTimeout = DEFAULT_CONNECT_TIMEOUT;

    private Integer readTimeout = DEFAULT_READ_TIMEOUT;

    private Integer writeTimeout = DEFAULT_WRITE_TIMEOUT;

    private Integer callTimeout = DEFAULT_CALL_TIMEOUT;

    public ApiHttpClient(ApiVersion apiVersion, String baseUrl, ApiCredential apiCredential) {
        baseUrl += apiVersion.getApiNamespace();
        this.defaultHttpClient = new DefaultHttpClient(baseUrl);
        HttpHeader header = setDefaultHeader(apiCredential);
        this.defaultHttpClient.addGlobalHeader(header);
        this.defaultHttpClient.setResponseBodyHandler(new ApiResponseErrorHandler());
    }

    private HttpHeader setDefaultHeader(ApiCredential apiCredential) {
        HttpHeader header = new HttpHeader();
        header.setUserAgent("vika-java-client");
        header.setBearerAuth(apiCredential.getToken());
        return header;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setWriteTimeout(Integer writeTimeout){ this.writeTimeout = writeTimeout; }

    public void setCallTimeout(Integer callTimeout){ this.callTimeout = callTimeout; }

    public void setDefaultPerPage(int defaultPerPage) {
        this.defaultPerPage = defaultPerPage;
    }

    public int getDefaultPerPage() {
        return this.defaultPerPage;
    }

    public DefaultHttpClient getDefaultHttpClient() {
        if (connectTimeout != null) {
            // Sets the per request connect timeout.
            ClientHttpRequestFactory requestFactory = this.defaultHttpClient.getRequestFactory();
            if (requestFactory instanceof OkHttpClientHttpRequestFactory) {
                ((OkHttpClientHttpRequestFactory) requestFactory).setConnectTimeout(connectTimeout);
            }
        }

        if (readTimeout != null) {
            // Sets the per request read timeout.
            ClientHttpRequestFactory requestFactory = this.defaultHttpClient.getRequestFactory();
            if (requestFactory instanceof OkHttpClientHttpRequestFactory) {
                ((OkHttpClientHttpRequestFactory) requestFactory).setReadTimeout(readTimeout);
            }
        }

        if (writeTimeout != null) {
            // Sets the per request write timeout.
            ClientHttpRequestFactory requestFactory = this.defaultHttpClient.getRequestFactory();
            if (requestFactory instanceof OkHttpClientHttpRequestFactory) {
                ((OkHttpClientHttpRequestFactory) requestFactory).setWriteTimeout(writeTimeout);
            }
        }

        if (callTimeout != null) {
            // Sets the per request call timeout.
            ClientHttpRequestFactory requestFactory = this.defaultHttpClient.getRequestFactory();
            if (requestFactory instanceof OkHttpClientHttpRequestFactory) {
                ((OkHttpClientHttpRequestFactory) requestFactory).setCallTimeout(callTimeout);
            }
        }

        return this.defaultHttpClient;
    }
}
