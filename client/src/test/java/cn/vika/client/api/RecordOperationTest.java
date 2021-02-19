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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.model.ApiQueryParam;
import cn.vika.client.api.model.Pager;
import cn.vika.client.api.models.CreateRecordRequest;
import cn.vika.client.api.models.RecordMap;
import cn.vika.client.api.models.RecordResult;
import cn.vika.client.api.models.UpdateRecord;
import cn.vika.client.api.models.UpdateRecordRequest;
import cn.vika.core.utils.JacksonConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static cn.vika.client.api.ConstantKey.TEST_DATASHEET_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Shawn Deng
 * @date 2021-02-19 13:16:15
 */
@TestMethodOrder(OrderAnnotation.class)
public class RecordOperationTest extends BaseTest {

    private static VikaApiClient vikaApiClient;

    @BeforeAll
    public static void setup() {
        vikaApiClient = testInitApiClient();
    }

    @AfterAll
    public static void teardown() throws ApiException {
    }

    @BeforeEach
    public void beforeTestMethod() {
        assertThat(vikaApiClient).isNotNull();
    }

    @Test
    @Order(1)
    public void testCreateRecordFromFile() throws IOException, ApiException {
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalResourceToList(RecordMap.class, "create-record.json");
        CreateRecordRequest recordRequest = new CreateRecordRequest().withRecords(recordMaps);
        List<RecordResult> newRecords = vikaApiClient.getRecordApi().addRecords(TEST_DATASHEET_ID.get(), recordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        // compare result for create record
        int i = 0;
        for (RecordResult newRecord : newRecords) {
            Map<String, Object> recordMap = recordMaps.get(i).getFields();
            for (Entry<String, Object> entry : newRecord.getFields().entrySet()) {
                if (recordMap.containsKey(entry.getKey())) {
                    assertThat(recordMap.get(entry.getKey())).isEqualTo(entry.getValue());
                }
            }
            i++;
        }
        List<String> recordIds = newRecords.stream().map(RecordResult::getRecordId).collect(Collectors.toList());
        assertThat(recordIds).isNotEmpty();
        deleteTestData(recordIds);
    }

    @Test
    @Order(2)
    public void testCreateRecordFromJson() throws IOException, ApiException {
        ObjectNode fieldMap = JsonNodeFactory.instance.objectNode()
                .put("ShortText", "Json manual builder")
                .put("LongText", "Json manual builder");
        fieldMap.set("Options", JsonNodeFactory.instance.arrayNode().add("A"));
        fieldMap.set("MultiSelect", JsonNodeFactory.instance.arrayNode().add("GG"));
        ObjectNode fields = JsonNodeFactory.instance.objectNode().set("fields", fieldMap);
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode().add(fields);
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalJsonNodeToList(RecordMap.class, arrayNode);
        CreateRecordRequest recordRequest = new CreateRecordRequest().withRecords(recordMaps);
        List<RecordResult> newRecords = vikaApiClient.getRecordApi().addRecords(TEST_DATASHEET_ID.get(), recordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        // compare result for create record
        int i = 0;
        for (RecordResult newRecord : newRecords) {
            Map<String, Object> recordMap = recordMaps.get(i).getFields();
            for (Entry<String, Object> entry : newRecord.getFields().entrySet()) {
                if (recordMap.containsKey(entry.getKey())) {
                    assertThat(recordMap.get(entry.getKey())).isEqualTo(entry.getValue());
                }
            }
            i++;
        }
        List<String> recordIds = newRecords.stream().map(RecordResult::getRecordId).collect(Collectors.toList());
        assertThat(recordIds).isNotEmpty();
        deleteTestData(recordIds);
    }

    @Test
    @Order(3)
    public void testCreateRecordFromBean() throws ApiException {
        TestFieldDTO fieldDTO = new TestFieldDTO();
        fieldDTO.setShortText("From Bean");
        fieldDTO.setLongText("From Bean");
        fieldDTO.setOptions(Collections.singletonList("A"));
        fieldDTO.setMultiSelect(Collections.singletonList(("KK")));

        List<RecordMap> recordMaps = Collections.singletonList(new RecordMap().withFields(JacksonConverter.toMap(fieldDTO)));
        CreateRecordRequest recordRequest = new CreateRecordRequest().withRecords(recordMaps);
        List<RecordResult> newRecords = vikaApiClient.getRecordApi().addRecords(TEST_DATASHEET_ID.get(), recordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        // compare result for create record
        int i = 0;
        for (RecordResult newRecord : newRecords) {
            Map<String, Object> recordMap = recordMaps.get(i).getFields();
            for (Entry<String, Object> entry : newRecord.getFields().entrySet()) {
                if (recordMap.containsKey(entry.getKey())) {
                    assertThat(recordMap.get(entry.getKey())).isEqualTo(entry.getValue());
                }
            }
            i++;
        }
        List<String> recordIds = newRecords.stream().map(RecordResult::getRecordId).collect(Collectors.toList());
        assertThat(recordIds).isNotEmpty();
        deleteTestData(recordIds);
    }

    @Test
    @Order(4)
    public void testUpdateRecord() throws IOException, ApiException {
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalResourceToList(RecordMap.class, "update-record.json");
        CreateRecordRequest createRecordRequest = new CreateRecordRequest().withRecords(recordMaps);
        List<RecordResult> newRecords = vikaApiClient.getRecordApi().addRecords(TEST_DATASHEET_ID.get(), createRecordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        assertThat(newRecords).hasSize(1);

        RecordResult recordResult = newRecords.get(0);
        assertThat(recordResult).isNotNull();

        String recordId = recordResult.getRecordId();

        UpdateRecord record = new UpdateRecord()
                .withRecordId(recordId)
                .withField("ShortText", "Ps: Test Update, content is 'This is from unit Test update record' before")
                // select can be set null or empty array if you want to clear field value
                .withField("Options", Collections.emptyList())
                .withField("MultiSelect", Arrays.asList("LL", "NN"));

        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
                .withRecords(Collections.singletonList(record));

        List<RecordResult> updateRecords = vikaApiClient.getRecordApi().updateRecords(TEST_DATASHEET_ID.get(), updateRecordRequest);
        assertThat(updateRecords).isNotNull();
        assertThat(updateRecords).isNotEmpty();
        assertThat(updateRecords).hasSize(1);

        RecordResult returnResult = updateRecords.get(0);
        assertThat(returnResult).isNotNull();
        assertThat(returnResult.getRecordId()).isEqualTo(recordId);
        assertThat(returnResult.getFields()).isNotNull();

        List<String> recordIds = newRecords.stream().map(RecordResult::getRecordId).collect(Collectors.toList());
        assertThat(recordIds).isNotEmpty();
        deleteTestData(recordIds);
    }

    @Test
    @Order(5)
    public void testDeleteRecord() throws IOException, ApiException {
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalResourceToList(RecordMap.class, "delete-one-record.json");
        CreateRecordRequest createRecordRequest = new CreateRecordRequest().withRecords(recordMaps);
        List<RecordResult> newRecords = vikaApiClient.getRecordApi().addRecords(TEST_DATASHEET_ID.get(), createRecordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        assertThat(newRecords).hasSize(1);

        RecordResult recordResult = newRecords.get(0);
        assertThat(recordResult).isNotNull();

        String recordId = recordResult.getRecordId();

        vikaApiClient.getRecordApi().deleteRecord(TEST_DATASHEET_ID.get(), recordId);

        // assert query whether record exist
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withRecordIds(Collections.singletonList(recordId));
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), queryParam);
        assertThat(pager).isNotNull();
        assertThat(pager.getTotalItems()).isZero();
    }

    @Test
    @Order(6)
    public void testDeleteRecordBatch() throws IOException, ApiException {
        // First create three record
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalResourceToList(RecordMap.class, "delete-many-record.json");
        CreateRecordRequest createRecordRequest = new CreateRecordRequest().withRecords(recordMaps);
        List<RecordResult> newRecords = vikaApiClient.getRecordApi().addRecords(TEST_DATASHEET_ID.get(), createRecordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        assertThat(newRecords).hasSize(3);

        List<String> recordIds = newRecords.stream().map(RecordResult::getRecordId).collect(Collectors.toList());

        // Now delete record which above code for creating record
        vikaApiClient.getRecordApi().deleteRecords(TEST_DATASHEET_ID.get(), recordIds);

        // assert query whether record exist
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withRecordIds(recordIds);
        Pager<RecordResult> pager = vikaApiClient.getRecordApi().getRecords(ConstantKey.TEST_DATASHEET_ID.get(), queryParam);
        assertThat(pager).isNotNull();
        assertThat(pager.getTotalItems()).isZero();
    }

    public static void deleteTestData(List<String> recordIds) throws ApiException {
        vikaApiClient.getRecordApi().deleteRecords(TEST_DATASHEET_ID.get(), recordIds);
        System.out.format("delete test data complete......\n");
    }

    public static class TestFieldDTO {

        @JsonProperty("ShortText")
        private String shortText;

        @JsonProperty("LongText")
        private String longText;

        @JsonProperty("Options")
        private List<String> options;

        @JsonProperty("MultiSelect")
        private List<String> multiSelect;

        public String getShortText() {
            return shortText;
        }

        public void setShortText(String shortText) {
            this.shortText = shortText;
        }

        public String getLongText() {
            return longText;
        }

        public void setLongText(String longText) {
            this.longText = longText;
        }

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }

        public List<String> getMultiSelect() {
            return multiSelect;
        }

        public void setMultiSelect(List<String> multiSelect) {
            this.multiSelect = multiSelect;
        }
    }
}
