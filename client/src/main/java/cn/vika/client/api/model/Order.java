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

package cn.vika.client.api.model;

/**
 * sort order enum
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:10:31
 */
public enum Order {
    /**
     * sort records descending
     */
    DESC,
    /**
     * sort records ascending
     */
    ASC;

    public static Order of(String name) {
        for (Order value : Order.values()) {
            if (value.name().toLowerCase().equals(name)) {
                return value;
            }
        }
        throw new NullPointerException("not support name: " + name);
    }
}
