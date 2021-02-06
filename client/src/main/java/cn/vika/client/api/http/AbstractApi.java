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

import cn.vika.core.http.DefaultHttpClient;

/**
 * public api client
 *
 * @author Zoe Zheng
 * @date 2020-12-16 11:27:39
 */
public abstract class AbstractApi {

    /**
     * build path
     *
     * @return string
     */
    protected abstract String basePath(String datasheetId);

    /**
     * http client
     */
    protected final ApiHttpClient apiHttpClient;

    public AbstractApi(ApiHttpClient apiHttpClient) {
        this.apiHttpClient = apiHttpClient;
    }

    public DefaultHttpClient getDefaultHttpClient() {
        return this.apiHttpClient.getDefaultHttpClient();
    }

    public int getDefaultPerPage() {
        return this.apiHttpClient.getDefaultPerPage();
    }
}
