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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.ApiCredential;
import cn.vika.client.api.model.ApiQueryParam;
import cn.vika.client.api.model.CreateRecordRequest;
import cn.vika.client.api.model.Pager;
import cn.vika.client.api.model.Record;
import cn.vika.client.api.model.RecordMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static cn.vika.client.api.ConstantKey.TEST_API_KEY;
import static cn.vika.client.api.ConstantKey.TEST_DATASHEET_ID;
import static cn.vika.client.api.ConstantKey.TEST_HOST_URL;
import static cn.vika.client.api.ConstantKey.TEST_SORT;
import static cn.vika.client.api.model.Order.of;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-24 13:18:26
 */
@TestMethodOrder(OrderAnnotation.class)
public class RecordPagerTest {

    private static final String DATASHEET_ID = TEST_DATASHEET_ID.get();
    private static final String HOST_URL = TEST_HOST_URL.get();
    private static final String API_KEY = TEST_API_KEY.get();

    private static final VikaApiClient vikaApiClient = new VikaApiClient(HOST_URL, new ApiCredential(API_KEY));

    private static List<String> initRecordIds = new ArrayList<>();

    @BeforeAll
    static void setUp() throws IOException {
        initTestData();
    }

    @AfterAll
    static void tearDown() {
        if (initRecordIds != null && !initRecordIds.isEmpty()) {
            vikaApiClient.getRecordApi().deleteRecords(DATASHEET_ID, initRecordIds);
            initRecordIds.clear();
        }
    }

    static void initTestData() throws IOException {
        ClassLoader classLoader = RecordPagerTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("test-init-record.json");
        assertThat(inputStream).isNotNull();
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalInputStreamToList(RecordMap.class, inputStream);
        CreateRecordRequest recordRequest = new CreateRecordRequest().withRecords(recordMaps);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, recordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        initRecordIds = newRecords.stream().map(Record::getRecordId).collect(Collectors.toList());
        assertThat(initRecordIds.size()).isNotZero();
        System.out.format("init test data complete....\n");
    }

    @Test
    @Order(101)
    void testGetAll() throws ApiException, JsonProcessingException {
        assertThat(DATASHEET_ID).isNotNull();
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID);
        assertThat(pager).isNotNull();
        assertThat(pager.getTotalItems()).isNotZero();
        List<Record> records = pager.all();
        for (Record record : records) {
            System.out.format("record: %s \n", JacksonJsonUtil.toJson(record, true));
        }
    }

    @Test
    @Order(102)
    void testPageStream() throws ApiException {
        Stream<Record> records = vikaApiClient.getRecordApi().getRecordsAsStream(DATASHEET_ID);
        assertThat(records).isNotNull();
        assertThat(records.findFirst()).isPresent();
    }

    @Test
    @Order(103)
    void testWithPageNumAndPageSize() throws ApiException, JsonProcessingException {
        int page = Integer.parseInt(ConstantKey.PAGE_NUM.get());
        int itemPerPage = Integer.parseInt(ConstantKey.PAGE_SIZE.get());
        List<Record> records = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID, page, itemPerPage);
        assertThat(records).isNotNull();
        assertThat(records.size()).isEqualTo(itemPerPage);
        for (Record record : records) {
            System.out.format("record: %s \n", JacksonJsonUtil.toJson(record, true));
        }
    }

    @Test
    @Order(104)
    void testPageWithPageSize() throws ApiException, JsonProcessingException {
        int itemPerPage = Integer.parseInt(ConstantKey.PAGE_SIZE.get());
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID, itemPerPage);
        assertThat(pager).isNotNull();
        assertThat(pager.getTotalItems()).isEqualTo(10);
        assertThat(pager.getItemsPerPage()).isNotZero();
        if (itemPerPage > pager.getItemsPerPage()) {
            itemPerPage = pager.getItemsPerPage();
        }
        int actualPages = (pager.getTotalItems() - 1) / itemPerPage + 1;
        assertThat(pager.getTotalPages()).isEqualTo(actualPages);
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<Record> records = pager.next();
            System.out.format("next pager cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (Record record : records) {
                System.out.format("current page=%d, record: %s \n", pageIndex, JacksonJsonUtil.toJson(record, true));
            }
        }
        assertThat(pageIndex).isEqualTo(pager.getTotalPages());
    }

    @Test
    @Order(105)
    void testPageWithView() throws ApiException, JsonProcessingException {
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withView(ConstantKey.TEST_VIEW_ID.get());
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID, queryParam);
        assertThat(pager).isNotNull();
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<Record> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (Record record : records) {
                System.out.format("record: %s \n", JacksonJsonUtil.toJson(record, true));
            }
        }
    }

    @Test
    @Order(106)
    void testPageWithSort() throws ApiException, JsonProcessingException {
        String[] sorts = TEST_SORT.get().split(",");
        ApiQueryParam queryParam = ApiQueryParam.EMPTY;
        for (String s : sorts) {
            String[] sort = s.split(":");
            queryParam.withSort(sort[0], of(sort[1]));
        }
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID, queryParam);
        assertThat(pager).isNotNull();
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<Record> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (Record record : records) {
                System.out.format("record: %s \n", JacksonJsonUtil.toJson(record, true));
            }
        }
    }

    @Test
    @Order(107)
    void testPageWithFields() throws ApiException, JsonProcessingException {
        String[] fields = ConstantKey.TEST_FIELDS.get().split(",");
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withFields(Arrays.asList(fields));
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID, queryParam);
        assertThat(pager).isNotNull();
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<Record> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (Record record : records) {
                System.out.format("record: %s \n", JacksonJsonUtil.toJson(record, true));
            }
        }
    }

    @Test
    @Order(108)
    void testPagesWithRecordIds() throws ApiException, JsonProcessingException {
        Random random = new Random();
        List<String> randomRecordIds = new ArrayList<>(2);
        int length = 2;
        for (int i = 0; i < length; i++) {
            randomRecordIds.add(initRecordIds.get(random.nextInt(initRecordIds.size())));
        }
        System.out.format("Record Id Filter List: %s", randomRecordIds);
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withRecordIds(randomRecordIds);
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID, queryParam);
        assertThat(pager).isNotNull();
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<Record> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (Record record : records) {
                System.out.format("record: %s \n", JacksonJsonUtil.toJson(record, true));
            }
        }
    }

    @Test
    @Order(109)
    void testPagesWithFormulaFilter() throws ApiException, JsonProcessingException {
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withFilter(ConstantKey.TEST_FILTER_FORMULA.get());
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID, queryParam);
        assertThat(pager).isNotNull();
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<Record> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (Record record : records) {
                System.out.format("record: %s \n", JacksonJsonUtil.toJson(record, true));
            }
        }
    }
}
