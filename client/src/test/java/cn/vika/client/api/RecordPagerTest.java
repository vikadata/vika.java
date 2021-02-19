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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.model.ApiQueryParam;
import cn.vika.client.api.model.Pager;
import cn.vika.client.api.models.RecordResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static cn.vika.client.api.models.Order.ASC;
import static cn.vika.client.api.models.Order.DESC;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * test record pager query
 *
 * @author Zoe Zheng
 * @date 2020-12-16 16:40:39
 */
@TestMethodOrder(OrderAnnotation.class)
public class RecordPagerTest extends BaseTest {

    private static VikaApiClient vikaApiClient;

    public RecordPagerTest() {
        super();
    }

    @BeforeAll
    public static void setup() {
        vikaApiClient = testInitApiClient();
    }

    @BeforeEach
    public void beforeTestMethod() {
        assertThat(vikaApiClient).isNotNull();
    }

    @Test
    @Order(1)
    public void testGetDefaultPage() throws ApiException, InterruptedException {
        int itemPerPage = Integer.parseInt(ConstantKey.PAGE_SIZE.get());
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), itemPerPage);
        assertThat(pager).isNotNull();
        int itemNumber = 0;
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<RecordResult> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (RecordResult record : records) {
                itemNumber++;
                System.out.format("page=%d, item=%d, recordId=%s \n", pageIndex, itemNumber, record.getRecordId());
            }
        }
        Thread.sleep(1000);
    }

    @Test
    @Order(2)
    public void testGetAllPage() throws ApiException, InterruptedException {
        int itemPerPage = Integer.parseInt(ConstantKey.PAGE_SIZE.get());
        long startTime = System.currentTimeMillis();
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), itemPerPage);
        assertThat(pager).isNotNull();
        List<RecordResult> records = pager.all();
        System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
        int itemNumber = 0;
        for (RecordResult record : records) {
            itemNumber++;
            System.out.format("item=%d, recordId=%s \n", itemNumber, record.getRecordId());
        }
        Thread.sleep(1000);
    }

    @Test
    @Order(3)
    public void testPageStream() throws ApiException, InterruptedException {
        int itemPerPage = Integer.parseInt(ConstantKey.PAGE_SIZE.get());
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), itemPerPage);
        assertThat(pager).isNotNull();
        Stream<RecordResult> records = pager.stream();
        records.forEach(record -> System.out.format("recordId=%s \n", record.getRecordId()));
        Thread.sleep(1000);
    }

    @Test
    @Order(4)
    public void testPageWithView() throws ApiException, InterruptedException {
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withView(ConstantKey.TEST_VIEW_ID.get());
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), queryParam);
        assertThat(pager).isNotNull();
        int itemNumber = 0;
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<RecordResult> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (RecordResult record : records) {
                itemNumber++;
                System.out.format("page=%d, item=%d, recordId=%s \n", pageIndex, itemNumber, record.getRecordId());
            }
        }
        Thread.sleep(1000);
    }

    @Test
    @Order(5)
    public void testPageWithSort() throws ApiException, InterruptedException {
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withSort("AutoNumber", DESC).withSort("Options", ASC);
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), queryParam);
        assertThat(pager).isNotNull();
        int itemNumber = 0;
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<RecordResult> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (RecordResult record : records) {
                itemNumber++;
                System.out.format("page=%d, item=%d, recordId=%s \n", pageIndex, itemNumber, record.getFields().get("AutoNumber"));
            }
        }
        Thread.sleep(1000);
    }

    @Test
    @Order(6)
    public void testPageWithFields() throws ApiException, InterruptedException {
        String[] fields = ConstantKey.TEST_FIELDS.get().split(",");
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withFields(Arrays.asList(fields));
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), queryParam);
        assertThat(pager).isNotNull();
        int itemNumber = 0;
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<RecordResult> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (RecordResult record : records) {
                itemNumber++;
                System.out.format("page=%d, item=%d, recordId=%s \n", pageIndex, itemNumber, record.getRecordId());
            }
        }
        Thread.sleep(1000);
    }

    @Test
    @Order(7)
    public void testPagesWithRecordIds() throws ApiException, InterruptedException {
        String[] recordIds = ConstantKey.TEST_RECORD_IDS.get().split(",");
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withRecordIds(Arrays.asList(recordIds));
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), queryParam);
        assertThat(pager).isNotNull();
        int itemNumber = 0;
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<RecordResult> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (RecordResult record : records) {
                itemNumber++;
                System.out.format("page=%d, item=%d, recordId=%s \n", pageIndex, itemNumber, record.getRecordId());
            }
        }
        Thread.sleep(1000);
    }

    @Test
    @Order(8)
    public void testPagesWithFormulaFilter() throws ApiException {
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withFilter(ConstantKey.TEST_FILTER_FORMULA.get());
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), queryParam);
        assertThat(pager).isNotNull();
        int itemNumber = 0;
        int pageIndex = 0;
        while (pager.hasNext()) {
            long startTime = System.currentTimeMillis();
            List<RecordResult> records = pager.next();
            System.out.format("cost time: %d ms \n", (System.currentTimeMillis() - startTime));
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            for (RecordResult record : records) {
                itemNumber++;
                System.out.format("page=%d, item=%d, recordId=%s \n", pageIndex, itemNumber, record.getRecordId());
            }
        }
    }
}
