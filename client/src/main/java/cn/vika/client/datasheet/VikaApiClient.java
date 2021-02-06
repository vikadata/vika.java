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

package cn.vika.client.datasheet;

import cn.vika.client.api.datasheet.AttachmentApi;
import cn.vika.client.api.datasheet.RecordApi;
import cn.vika.client.api.http.ApiCredential;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.http.ApiHttpClient.ApiVersion;

/**
 * This class is provides a simplified interface to a Vikadata API server, and divides the API up into
 * a separate API class for each concern.
 * @author Shawn Deng
 * @date 2021-02-05 11:10:33
 */
public class VikaApiClient {

    private final ApiVersion apiVersion;

    private final ApiCredential credential;

    private ApiHttpClient apiHttpClient;

    private RecordApi recordApi;

    private AttachmentApi attachmentApi;

    private RecordApiClient recordApiClient;

    private AttachmentApiClient attachmentApiClient;

    public VikaApiClient(ApiCredential credential) {
        this(ApiVersion.V1, credential);
    }

    public VikaApiClient(ApiVersion apiVersion, ApiCredential credential) {
        this.apiVersion = apiVersion;
        this.credential = credential;
        this.apiHttpClient = new ApiHttpClient(apiVersion, credential);
    }

    /**
     * Return the API version that this instance is using.
     *
     * @return the API version that this instance is using
     */
    public ApiVersion getApiVersion() {
        return this.apiVersion;
    }

    /**
     * Get the default number per page for calls that return multiple items.
     *
     * @return the default number per page for calls that return multiple item
     */
    public int getDefaultPerPage() {
        return this.apiHttpClient.getDefaultPerPage();
    }

    /**
     * Set the default number per page for calls that return multiple items.
     *
     * @param defaultPerPage the new default number per page for calls that return multiple item
     */
    public void setDefaultPerPage(int defaultPerPage) {
        this.apiHttpClient.setDefaultPerPage(defaultPerPage);
    }

    /**
     * Sets the per request connect timeout.
     *
     * @param connectTimeout the per request connect timeout in milliseconds, can be null to use default
     */
    public void setRequestTimeout(Integer connectTimeout) {
        this.apiHttpClient.setConnectTimeout(connectTimeout);
    }

    /**
     * Build method that sets the per request connect timeout.
     * @param connectTimeout the per request connect timeout in milliseconds, can be null to use default
     * @return VikaApiClient instance
     */
    public VikaApiClient withRequestTimeout(Integer connectTimeout) {
        apiHttpClient.setConnectTimeout(connectTimeout);
        return this;
    }

    /**
     * Sets the per request read timeout.
     *
     * @param readTimeout the per request read timeout in milliseconds, can be null to use default
     */
    public void setReadTimeout(Integer readTimeout) {
        this.apiHttpClient.setReadTimeout(readTimeout);
    }

    /**
     * Build method that sets the per request read timeout.
     * @param readTimeout the per request read timeout in milliseconds, can be null to use default
     * @return VikaApiClient instance
     */
    public VikaApiClient withReadTimeout(Integer readTimeout) {
        apiHttpClient.setReadTimeout(readTimeout);
        return this;
    }

    public RecordApi getRecordApi() {
        if (this.recordApi == null) {
            synchronized (this) {
                if (this.recordApi == null) {
                    this.recordApi = new RecordApi(this.apiHttpClient);
                }
            }
        }
        return this.recordApi;
    }

    public AttachmentApi getAttachmentApi() {
        if (this.attachmentApi == null) {
            synchronized (this) {
                if (this.attachmentApi == null) {
                    this.attachmentApi = new AttachmentApi(this.apiHttpClient);
                }
            }
        }
        return this.attachmentApi;
    }

    @Deprecated
    public RecordApiClient getRecordApiClient() {
        if (this.recordApiClient == null) {
            synchronized (this) {
                if (this.recordApiClient == null) {
                    this.recordApiClient = new RecordApiClient(this.apiHttpClient);
                }
            }
        }
        return this.recordApiClient;
    }

    @Deprecated
    public AttachmentApiClient getAttachmentApiClient() {
        if (this.attachmentApiClient == null) {
            synchronized (this) {
                if (this.attachmentApiClient == null) {
                    this.attachmentApiClient = new AttachmentApiClient(this.apiHttpClient);
                }
            }
        }
        return this.attachmentApiClient;
    }
}
