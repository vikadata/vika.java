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

package cn.vika.client.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.ApiCredential;
import cn.vika.client.api.model.ApiQueryParam;
import cn.vika.client.api.model.CreateRecordRequest;
import cn.vika.client.api.model.FieldKey;
import cn.vika.client.api.model.Pager;
import cn.vika.client.api.model.Record;
import cn.vika.client.api.model.RecordMap;
import cn.vika.client.api.model.UpdateRecord;
import cn.vika.client.api.model.UpdateRecordRequest;
import cn.vika.core.utils.JacksonConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static cn.vika.client.api.ConstantKey.TEST_API_KEY;
import static cn.vika.client.api.ConstantKey.TEST_DATASHEET_ID;
import static cn.vika.client.api.ConstantKey.TEST_HOST_URL;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Shawn Deng
 */
@TestMethodOrder(OrderAnnotation.class)
public class RecordOperationTest {

    private final String DATASHEET_ID = TEST_DATASHEET_ID.get();

    private final String HOST_URL = TEST_HOST_URL.get();

    private final String API_KEY = TEST_API_KEY.get();

    private final VikaApiClient vikaApiClient = new VikaApiClient(HOST_URL, new ApiCredential(API_KEY));

    private List<String> deleteRecordIds = new ArrayList<>();

    @AfterEach
    void testDeleteData() {
        if (!deleteRecordIds.isEmpty()) {
            vikaApiClient.getRecordApi().deleteRecords(DATASHEET_ID, deleteRecordIds);
            deleteRecordIds.clear();
        }
    }

    @Test
    @Order(10)
    void testCreateRecordByNameFromFile() throws IOException, ApiException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("create-record.json");
        assertThat(inputStream).isNotNull();
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalInputStreamToList(RecordMap.class, inputStream);
        CreateRecordRequest recordRequest = new CreateRecordRequest()
                .withRecords(recordMaps);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, recordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        // compare result for create record
        int i = 0;
        for (Record newRecord : newRecords) {
            Map<String, Object> recordMap = recordMaps.get(i).getFields();
            for (Entry<String, Object> entry : newRecord.getFields().entrySet()) {
                if (recordMap.containsKey(entry.getKey())) {
                    assertThat(recordMap.get(entry.getKey())).isEqualTo(entry.getValue());
                }
            }
            i++;
        }
        deleteRecordIds = newRecords.stream().map(Record::getRecordId).collect(Collectors.toList());
        assertThat(deleteRecordIds).isNotEmpty();
    }

    @Test
    @Order(11)
    void testCreateRecordByIdFromFile() throws IOException, ApiException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("create-record-by-id.json");
        assertThat(inputStream).isNotNull();
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalInputStreamToList(RecordMap.class, inputStream);
        CreateRecordRequest recordRequest = new CreateRecordRequest()
                .withRecords(recordMaps)
                .withFieldKey(FieldKey.ID);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, recordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        // compare result for create record
        int i = 0;
        for (Record newRecord : newRecords) {
            Map<String, Object> recordMap = recordMaps.get(i).getFields();
            for (Entry<String, Object> entry : newRecord.getFields().entrySet()) {
                if (recordMap.containsKey(entry.getKey())) {
                    assertThat(recordMap.get(entry.getKey())).isEqualTo(entry.getValue());
                }
            }
            i++;
        }
        deleteRecordIds = newRecords.stream().map(Record::getRecordId).collect(Collectors.toList());
        assertThat(deleteRecordIds).isNotEmpty();
    }

    @Test
    @Order(20)
    void testCreateRecordFromJson() throws IOException, ApiException {
        ObjectNode fieldMap = JsonNodeFactory.instance.objectNode()
                .put("ShortText", "Json manual builder")
                .put("LongText", "Json manual builder")
                .set("Options", JsonNodeFactory.instance.arrayNode().add("A"));
        fieldMap.set("Options", JsonNodeFactory.instance.arrayNode().add("A"));
        fieldMap.set("MultiSelect", JsonNodeFactory.instance.arrayNode().add("GG"));
        ObjectNode fields = JsonNodeFactory.instance.objectNode().set("fields", fieldMap);
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode().add(fields);
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalJsonNodeToList(RecordMap.class, arrayNode);
        CreateRecordRequest recordRequest = new CreateRecordRequest()
                .withRecords(recordMaps);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, recordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        // compare result for create record
        int i = 0;
        for (Record newRecord : newRecords) {
            Map<String, Object> recordMap = recordMaps.get(i).getFields();
            for (Entry<String, Object> entry : newRecord.getFields().entrySet()) {
                if (recordMap.containsKey(entry.getKey())) {
                    assertThat(recordMap.get(entry.getKey())).isEqualTo(entry.getValue());
                }
            }
            i++;
        }
        deleteRecordIds = newRecords.stream().map(Record::getRecordId).collect(Collectors.toList());
        assertThat(deleteRecordIds).isNotEmpty();
    }

    @Test
    @Order(30)
    void testCreateRecordFromBean() throws ApiException {
        TestFieldDTO fieldDTO = new TestFieldDTO();
        fieldDTO.setShortText("From Bean");
        fieldDTO.setLongText("From Bean");
        fieldDTO.setOptions(Collections.singletonList("A"));
        fieldDTO.setMultiSelect(Collections.singletonList(("KK")));

        List<RecordMap> recordMaps = Collections.singletonList(new RecordMap().withFields(JacksonConverter.toMap(fieldDTO)));
        CreateRecordRequest recordRequest = new CreateRecordRequest()
                .withRecords(recordMaps);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, recordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        // compare result for create record
        int i = 0;
        for (Record newRecord : newRecords) {
            Map<String, Object> recordMap = recordMaps.get(i).getFields();
            for (Entry<String, Object> entry : newRecord.getFields().entrySet()) {
                if (recordMap.containsKey(entry.getKey())) {
                    assertThat(recordMap.get(entry.getKey())).isEqualTo(entry.getValue());
                }
            }
            i++;
        }
        deleteRecordIds = newRecords.stream().map(Record::getRecordId).collect(Collectors.toList());
        assertThat(deleteRecordIds).isNotEmpty();
    }

    @Test
    @Order(40)
    void testUpdateRecord() throws IOException, ApiException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("update-record.json");
        assertThat(inputStream).isNotNull();
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalInputStreamToList(RecordMap.class, inputStream);
        CreateRecordRequest createRecordRequest = new CreateRecordRequest()
                .withRecords(recordMaps);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, createRecordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        assertThat(newRecords).hasSize(1);

        Record recordResult = newRecords.get(0);
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

        List<Record> updateRecords = vikaApiClient.getRecordApi().updateRecords(DATASHEET_ID, updateRecordRequest);
        assertThat(updateRecords).isNotNull();
        assertThat(updateRecords).isNotEmpty();
        assertThat(updateRecords).hasSize(1);

        Record returnResult = updateRecords.get(0);
        assertThat(returnResult).isNotNull();
        assertThat(returnResult.getRecordId()).isEqualTo(recordId);
        assertThat(returnResult.getFields()).isNotNull();

        deleteRecordIds = newRecords.stream().map(Record::getRecordId).collect(Collectors.toList());
        assertThat(deleteRecordIds).isNotEmpty();
    }

    @Test
    @Order(41)
    void testUpdateRecordByFieldId() throws IOException, ApiException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("update-record-by-id.json");
        assertThat(inputStream).isNotNull();
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalInputStreamToList(RecordMap.class, inputStream);
        CreateRecordRequest createRecordRequest = new CreateRecordRequest()
                .withRecords(recordMaps)
                .withFieldKey(FieldKey.ID);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, createRecordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        assertThat(newRecords).hasSize(1);

        Record recordResult = newRecords.get(0);
        assertThat(recordResult).isNotNull();

        String recordId = recordResult.getRecordId();

        UpdateRecord record = new UpdateRecord()
                .withRecordId(recordId)
                .withField("fldjoQSlHfV2z", "Ps: Test Update, content is 'This is from unit Test update record' before")
                // select can be set null or empty array if you want to clear field value
                .withField("fldAY9mW7MUdW", Collections.emptyList())
                .withField("fld8w4C5fwDBl", Arrays.asList("LL", "NN"));

        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
                .withRecords(Collections.singletonList(record))
                .withFieldKey(FieldKey.ID);

        List<Record> updateRecords = vikaApiClient.getRecordApi().updateRecords(DATASHEET_ID, updateRecordRequest);
        assertThat(updateRecords).isNotNull();
        assertThat(updateRecords).isNotEmpty();
        assertThat(updateRecords).hasSize(1);

        Record returnResult = updateRecords.get(0);
        assertThat(returnResult).isNotNull();
        assertThat(returnResult.getRecordId()).isEqualTo(recordId);
        assertThat(returnResult.getFields()).isNotNull();

        deleteRecordIds = newRecords.stream().map(Record::getRecordId).collect(Collectors.toList());
        assertThat(deleteRecordIds).isNotEmpty();
    }

    @Test
    @Order(50)
    void testDeleteRecord() throws IOException, ApiException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("delete-one-record.json");
        assertThat(inputStream).isNotNull();
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalInputStreamToList(RecordMap.class, inputStream);
        CreateRecordRequest createRecordRequest = new CreateRecordRequest()
                .withRecords(recordMaps);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, createRecordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        assertThat(newRecords).hasSize(1);

        Record record = newRecords.get(0);
        assertThat(record).isNotNull();

        String recordId = record.getRecordId();

        vikaApiClient.getRecordApi().deleteRecord(DATASHEET_ID, recordId);

        // assert query whether record exist
        ApiQueryParam queryParam = ApiQueryParam.newInstance().withRecordIds(Collections.singletonList(recordId));
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID, queryParam);
        assertThat(pager).isNotNull();
        assertThat(pager.getTotalItems()).isZero();
    }

    @Test
    @Order(60)
    void testDeleteRecordBatch() throws IOException, ApiException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("delete-many-record.json");
        assertThat(inputStream).isNotNull();
        // First create three record
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalInputStreamToList(RecordMap.class, inputStream);
        CreateRecordRequest createRecordRequest = new CreateRecordRequest().withRecords(recordMaps);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, createRecordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        assertThat(newRecords).hasSize(10);

        List<String> recordIds = newRecords.stream().map(Record::getRecordId).collect(Collectors.toList());

        // Now delete record which above code for creating record
        vikaApiClient.getRecordApi().deleteRecords(DATASHEET_ID, recordIds);

        // assert query whether record exist
        ApiQueryParam queryParam = ApiQueryParam.newInstance().withRecordIds(recordIds);
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID, queryParam);
        assertThat(pager).isNotNull();
        assertThat(pager.getTotalItems()).isZero();
    }

    @Test
    @Order(61)
    public void testDeleteAllRecordsOnEmptyData() {
        vikaApiClient.getRecordApi().deleteAllRecords(DATASHEET_ID);
    }

    @Test
    @Order(62)
    public void testDeleteAllRecordsOnNoEmptyData() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("delete-many-record.json");
        assertThat(inputStream).isNotNull();
        List<RecordMap> recordMaps = JacksonJsonUtil.unmarshalInputStreamToList(RecordMap.class, inputStream);
        CreateRecordRequest createRecordRequest = new CreateRecordRequest().withRecords(recordMaps);
        List<Record> newRecords = vikaApiClient.getRecordApi().addRecords(DATASHEET_ID, createRecordRequest);
        assertThat(newRecords).isNotNull();
        assertThat(newRecords).isNotEmpty();
        assertThat(newRecords).hasSize(10);

        vikaApiClient.getRecordApi().deleteAllRecords(DATASHEET_ID);

        // assert query whether record exist
        Pager<Record> pager = vikaApiClient.getRecordApi().getRecords(DATASHEET_ID);
        assertThat(pager).isNotNull();
        assertThat(pager.getTotalItems()).isZero();
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
