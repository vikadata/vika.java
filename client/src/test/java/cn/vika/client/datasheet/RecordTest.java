package cn.vika.client.datasheet;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.ApiCredential;
import cn.vika.client.api.model.ApiQueryParam;
import cn.vika.client.api.model.Pager;
import cn.vika.client.datasheet.model.Order;
import cn.vika.client.datasheet.model.RecordDetail;
import cn.vika.core.utils.JacksonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * test
 *
 * @author Zoe Zheng
 * @date 2020-12-16 16:40:39
 */
public class RecordTest {

    private VikaApiClient vikaApiClient;

    private String apiKey = System.getenv("API_KEY");

    private String datasheetId = System.getenv("D_NAME");

    @BeforeEach
    public void setup() {
        ApiCredential credential = new ApiCredential(apiKey);
        if (vikaApiClient == null) {
            vikaApiClient = new VikaApiClient(credential);
        }
    }

    @Test
    public void testDefaultPage() throws ApiException {
        Pager<RecordDetail> pager = vikaApiClient.getRecordApi().getRecords(datasheetId, 10);
        assertThat(pager).isNotNull();

        int itemNumber = 0;
        int pageIndex = 0;
        while (pager.hasNext()) {
            List<RecordDetail> records = pager.next();
            pageIndex++;
            assertThat(pageIndex).isEqualTo(pager.getCurrentPage());
            if (pageIndex < pager.getTotalPages()) {
                assertThat(records.size()).isEqualTo(10);
            }
            for (RecordDetail record : records) {
                itemNumber++;
                System.out.format("page=%d, item=%d, recordId=%s \n", pageIndex, itemNumber, record.getRecordId());
            }
        }
    }

    @Test
    public void testGetAllPage() throws ApiException {
        Pager<RecordDetail> pager = vikaApiClient.getRecordApi().getRecords(datasheetId, 10);
        assertThat(pager).isNotNull();
        List<RecordDetail> records = pager.all();
        int itemNumber = 0;
        for (RecordDetail record : records) {
            itemNumber++;
            System.out.format("item=%d, recordId=%s \n", itemNumber, record.getRecordId());
        }
    }

    @Test
    public void testPageStream() throws ApiException {
        Pager<RecordDetail> pager = vikaApiClient.getRecordApi().getRecords(datasheetId, 10);
        assertThat(pager).isNotNull();
        Stream<RecordDetail> records = pager.stream();
        records.forEach(record -> System.out.format("recordId=%s \n", record.getRecordId()));
    }

    @Test
    public void testPageByView() throws ApiException {
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withView("viwPjaor1digt");
        Pager<RecordDetail> pager = vikaApiClient.getRecordApi().getRecords(datasheetId, queryParam);
        assertThat(pager).isNotNull();

        List<RecordDetail> records = pager.all();

        int itemNumber = 0;
        for (RecordDetail record : records) {
            itemNumber++;
            System.out.format("item=%d, record=%s \n", itemNumber, JacksonConverter.toJson(record));
        }
    }

    @Test
    public void testPageBySort() throws ApiException {
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withSort("语言", Order.DESC);
        Pager<RecordDetail> pager = vikaApiClient.getRecordApi().getRecords(datasheetId, queryParam);
        assertThat(pager).isNotNull();
        List<RecordDetail> records = pager.all();
        int itemNumber = 0;
        for (RecordDetail record : records) {
            itemNumber++;
            System.out.format("item=%d, record=%s \n", itemNumber, JacksonConverter.toJson(record));
        }
    }

    @Test
    public void testPageWithFieldsFilter() throws ApiException {
        ApiCredential credential = new ApiCredential("usk9Urb7NLr8b42SJCIH42J");
        VikaApiClient vikaApiClient = new VikaApiClient(credential);
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withFields(Arrays.asList("语言", "心裂"));
        Pager<RecordDetail> pager = vikaApiClient.getRecordApi().getRecords("dstkbJ434jLJ40q4TQ", queryParam);
        assertThat(pager).isNotNull();
        List<RecordDetail> records = pager.all();
        int itemNumber = 0;
        for (RecordDetail record : records) {
            itemNumber++;
            System.out.format("item=%d, record=%s \n", itemNumber, JacksonConverter.toJson(record));
        }
    }

    @Test
    public void testPagesWithRecordIds() throws ApiException {
        ApiQueryParam queryParam = ApiQueryParam.EMPTY.withRecordIds(Arrays.asList("recnUNvCc6YYp", "recX7OKsvrQyT"));
        Pager<RecordDetail> pager = vikaApiClient.getRecordApi().getRecords(datasheetId, queryParam);
        assertThat(pager).isNotNull();
        List<RecordDetail> records = pager.all();
        int itemNumber = 0;
        for (RecordDetail record : records) {
            itemNumber++;
            System.out.format("item=%d, record=%s \n", itemNumber, JacksonConverter.toJson(record));
        }
    }


    //    @Test
    public void testCreateRecords() {
//        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
//        RecordRequest param = new RecordRequest();
//        RecordInfo[] createRecords = new RecordInfo[2];
//        HashMap<String, Object> field1 = new HashMap<>();
//        field1.put("数字ID", 999);
//        field1.put("多行文本", "本质上和上面的需求是同一个999");
//        RecordInfo record1 = new RecordInfo();
//        record1.setFields(field1);
//        HashMap<String, Object> field2 = new HashMap<>();
//        RecordInfo record2 = new RecordInfo();
//        field2.put("数字ID", 1000);
//        field2.put("多行文本", "本质上和上面的需求是同一个1000");
//        record2.setFields(field2);
//        createRecords[0] = record1;
//        createRecords[1] = record2;
//        param.setRecords(createRecords);
//        RecordClient recordClient = new RecordClient(credential, "dst7urpLop4QdLAv3j");
//        RecordInfo[] records = recordClient.createRecords(param);
//        Assertions.assertNotNull(records);
//        Assertions.assertEquals(records.length, 2);
    }

    //    @Test
    public void testModifyRecords() {
//        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
//        RecordRequest param = new RecordRequest();
//        RecordInfo[] createRecords = new RecordInfo[2];
//        HashMap<String, Object> field1 = new HashMap<>();
//        field1.put("数字ID", 6666);
//        field1.put("多行文本", "本质上和上面的需求是同一个6666");
//        RecordInfo record1 = new RecordInfo();
//        record1.setFields(field1);
//        record1.setRecordId("recWfiMuFk60P");
//        HashMap<String, Object> field2 = new HashMap<>();
//        RecordInfo record2 = new RecordInfo();
//        field2.put("数字ID", 88888);
//        field2.put("多行文本", "本质上和上面的需求是同一个88888");
//        record2.setFields(field2);
//        record2.setRecordId("recZmiZ5QyrTC");
//        createRecords[0] = record1;
//        createRecords[1] = record2;
//        param.setRecords(createRecords);
//        RecordClient recordClient = new RecordClient(credential, "dst7urpLop4QdLAv3j");
//        RecordInfo[] records = recordClient.modifyRecords(param);
//        Assertions.assertNotNull(records);
//        Assertions.assertEquals(records.length, 2);
    }

    //    @Test
    public void testDeleteRecords() {
//        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
//        RecordClient recordClient = new RecordClient(credential, "dst7urpLop4QdLAv3j");
//        DeleteRecordRequest param = new DeleteRecordRequest();
//        param.setRecordIds(new String[] {"recWfiMuFk60P", "recZmiZ5QyrTC"});
//        boolean result = recordClient.deleteRecords(param);
//        Assertions.assertNotNull(result);
//        Assertions.assertTrue(result);
    }
}
