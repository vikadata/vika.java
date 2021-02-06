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

package cn.vika.client.api.http;

import cn.vika.core.http.ClientHttpRequestFactory;
import cn.vika.core.http.DefaultHttpClient;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.http.OkHttpClientHttpRequestFactory;
import cn.vika.core.utils.AssertUtil;

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

    private static final String DEFAULT_HOST = "https://vika.cn";

    public static final int DEFAULT_PER_PAGE = 100;

    private DefaultHttpClient defaultHttpClient;

    private int defaultPerPage = DEFAULT_PER_PAGE;

    private String baseUrl;

    private Integer connectTimeout;

    private Integer readTimeout;

    public ApiHttpClient(ApiVersion apiVersion, ApiCredential apiCredential) {
        this(apiVersion, DEFAULT_HOST, apiCredential);
    }

    public ApiHttpClient(ApiVersion apiVersion, String baseUrl, ApiCredential apiCredential) {
        AssertUtil.isTrue(baseUrl.equals(DEFAULT_HOST), "Illegal baseUrl, only development");
        this.baseUrl = baseUrl;
        this.baseUrl += apiVersion.getApiNamespace();
        this.defaultHttpClient = new DefaultHttpClient(this.baseUrl);
        HttpHeader header = setDefaultHeader(apiCredential);
        this.defaultHttpClient.addGlobalHeader(header);
    }

    private HttpHeader setDefaultHeader(ApiCredential apiCredential) {
        HttpHeader header = HttpHeader.EMPTY;
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
        return this.defaultHttpClient;
    }
}
