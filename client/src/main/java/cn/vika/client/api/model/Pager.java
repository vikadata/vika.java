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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import cn.vika.client.api.Constants;
import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.utils.JacksonConverter;
import cn.vika.core.utils.MapUtil;
import com.fasterxml.jackson.databind.JavaType;

/**
 * <p>This class defines an Iterator implementation that is used as a paging iterator for all API methods that
 * return a List of objects. </p>
 * @author Shawn Deng
 * @date 2021-02-05 17:48:35
 */
public class Pager<T> implements Iterator<List<T>> {

    private int itemsPerPage;

    private int totalPages;

    private int totalItems;

    private int currentPage;

    private List<T> currentItems;

    private Stream<T> pagerStream = null;

    private ApiQueryParam queryParam;

    private AbstractApi api;

    private String url;

    private JavaType javaType;

    public Pager(AbstractApi api, String url, int itemsPerPage, Class<T> type) throws ApiException {
        javaType = JacksonConverter.getCollectionJavaType(type);

        this.api = api;
        this.url = url;

        if (itemsPerPage < 1) {
            itemsPerPage = api.getDefaultPerPage();
        }

        this.queryParam = new ApiQueryParam(1, itemsPerPage);
        Map<String, String> uriVariables = this.queryParam.toMap();
        GenericTypeReference<HttpResult<PagerInfo<T>>> reference = new GenericTypeReference<HttpResult<PagerInfo<T>>>() {};
        String uri = url + MapUtil.extractKeyToVariables(uriVariables);
        HttpResult<PagerInfo<T>> result = api.getDefaultHttpClient().get(uri, HttpHeader.EMPTY, reference, uriVariables);
        if (result.getData().getRecords() != null) {
            this.currentItems = JacksonConverter.toGenericBean(result.getData().getRecords(), javaType);
            if (this.currentItems == null) {
                throw new ApiException("Invalid response from server");
            }
        }
        else {
            this.currentItems.clear();
        }
        this.itemsPerPage = result.getData().getPageSize();
        this.totalItems = result.getData().getTotal();
        this.totalPages = this.totalItems == 0 ? 1 : ((this.totalItems - 1) / this.itemsPerPage + 1);
    }

    public Pager(AbstractApi api, String url, ApiQueryParam queryParam, Class<T> type) throws ApiException {
        this.api = api;
        this.url = url;
        this.queryParam = queryParam;
        javaType = JacksonConverter.getCollectionJavaType(type);
        GenericTypeReference<HttpResult<PagerInfo<T>>> reference = new GenericTypeReference<HttpResult<PagerInfo<T>>>() {};
        Map<String, String> uriVariables = this.queryParam.toMap();
        String uri = url + MapUtil.extractKeyToVariables(uriVariables);
        HttpResult<PagerInfo<T>> result = api.getDefaultHttpClient().get(uri, HttpHeader.EMPTY, reference, uriVariables);
        if (result.getData().getRecords() != null) {
            this.currentItems = JacksonConverter.toGenericBean(result.getData().getRecords(), javaType);
            if (this.currentItems == null) {
                throw new ApiException("Invalid response from server");
            }
        }
        else {
            this.currentItems.clear();
        }
        this.itemsPerPage = result.getData().getPageSize();
        this.totalItems = result.getData().getTotal();
        this.totalPages = this.totalItems == 0 ? 1 : ((this.totalItems - 1) / this.itemsPerPage + 1);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    @Override
    public boolean hasNext() {
        return this.currentPage < this.totalPages;
    }

    @Override
    public List<T> next() {
        return page(this.currentPage + 1);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public List<T> first() {
        return page(1);
    }

    public List<T> last() {
        return page(totalPages);
    }

    public List<T> page(int pageNumber) {
        if (pageNumber > this.totalPages) {
            throw new NoSuchElementException();
        }
        else if (pageNumber < 1) {
            throw new NoSuchElementException();
        }

        if (this.currentPage == 0 && pageNumber == 1) {
            this.currentPage = 1;
            return this.currentItems;
        }

        if (this.currentPage == pageNumber) {
            return this.currentItems;
        }

        queryParam.withParam(Constants.PAGE_NUM, Integer.toString(pageNumber));
        Map<String, String> uriVariables = queryParam.toMap();
        GenericTypeReference<HttpResult<PagerInfo<T>>> reference = new GenericTypeReference<HttpResult<PagerInfo<T>>>() {};
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            // do Nothing
        }
        String uri = url + MapUtil.extractKeyToVariables(uriVariables);
        HttpResult<PagerInfo<T>> result = api.getDefaultHttpClient().get(uri, HttpHeader.EMPTY, reference, uriVariables);
        if (result.getData().getRecords() != null) {
            this.currentItems = JacksonConverter.toGenericBean(result.getData().getRecords(), javaType);
        }
        else {
            this.currentItems.clear();
        }
        this.currentPage = pageNumber;
        return this.currentItems;
    }

    /**
     * Gets all the items from each page
     * @return all the items
     */
    public List<T> all() {

        // Make sure that current page is 0, this will ensure the whole list is fetched
        // regardless of what page the instance is currently on.
        currentPage = 0;
        List<T> allItems = new ArrayList<>(Math.max(totalItems, 0));

        // Iterate through the pages and append each page of items to the list
        while (hasNext()) {
            allItems.addAll(next());
        }

        return allItems;
    }

    public Stream<T> stream() throws IllegalStateException {
        if (pagerStream == null) {
            synchronized (this) {
                if (pagerStream == null) {

                    // Make sure that current page is 0, this will ensure the whole list is streamed
                    // regardless of what page the instance is currently on.
                    currentPage = 0;

                    // Create a Stream.Builder to contain all the items. This is more efficient than
                    // getting a List with all() and streaming that List
                    Stream.Builder<T> streamBuilder = Stream.builder();

                    // Iterate through the pages and append each page of items to the stream builder
                    while (hasNext()) {
                        next().forEach(streamBuilder);
                    }

                    pagerStream = streamBuilder.build();
                    return pagerStream;
                }
            }
        }
        throw new IllegalStateException("Stream already issued");
    }
}
