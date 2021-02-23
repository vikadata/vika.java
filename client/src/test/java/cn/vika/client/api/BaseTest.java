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

package cn.vika.client.api;

import cn.vika.client.api.http.ApiCredential;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-18 14:37:15
 */
public abstract class BaseTest {

    protected static VikaApiClient testInitApiClient() {
        ApiCredential apiCredential = new ApiCredential(ConstantKey.TEST_API_KEY.get());
        return new VikaApiClient(ConstantKey.TEST_HOST_URL.get(), apiCredential);
    }
}
