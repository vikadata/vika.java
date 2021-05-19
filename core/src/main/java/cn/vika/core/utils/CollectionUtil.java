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

package cn.vika.core.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utils for Collection
 * @author Shawn Deng
 * @date 2021-05-19 23:52:38
 */
public class CollectionUtil {

    /**
     * Split collection by size using parallel stream
     * @param list List
     * @param splitSize split size
     * @param <T> Class Type
     * @return Split List Collection
     */
    public static <T> List<List<T>> splitListParallel(List<T> list, int splitSize) {
        int limit = (list.size() + splitSize - 1) / splitSize;
        return Stream.iterate(0, n -> n + 1)
            .limit(limit).parallel().map(item ->
                list.stream().skip((long) item * splitSize)
                    .limit(splitSize).parallel()
                    .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }
}
