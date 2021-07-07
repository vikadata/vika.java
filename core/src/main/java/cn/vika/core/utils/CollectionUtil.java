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
