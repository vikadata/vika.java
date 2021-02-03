package cn.vika.client.datasheet;

import java.util.HashMap;

import cn.vika.api.http.ApiCredential;
import cn.vika.client.datasheet.model.DeleteRecordRequest;
import cn.vika.client.datasheet.model.Order;
import cn.vika.client.datasheet.model.QueryRecordRequest;
import cn.vika.client.datasheet.model.RecordInfo;
import cn.vika.client.datasheet.model.RecordPageInfo;
import cn.vika.client.datasheet.model.RecordRequest;
import cn.vika.client.datasheet.model.SortRequest;
import org.junit.jupiter.api.Assertions;

/**
 * test
 *
 * @author Zoe Zheng
 * @date 2020-12-16 16:40:39
 */
public class RecordTest {
//    @Test
    public void testQueryRecords() {
        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
        QueryRecordRequest param = new QueryRecordRequest();
        RecordClient recordClient = new RecordClient(credential, "dst7urpLop4QdLAv3j");
        RecordPageInfo pageInfo = recordClient.queryRecords(param);
        Assertions.assertNotNull(pageInfo);
        Assertions.assertEquals(pageInfo.getTotal(), pageInfo.getRecords().length);
    }

//    @Test
    public void testQueryRecordsWithSort() {
        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
        QueryRecordRequest param = new QueryRecordRequest();
        SortRequest[] sorts = new SortRequest[1];
        SortRequest sort = new SortRequest();
        sort.setField("数字ID");
        sort.setOrder(Order.DESC);
        sorts[0] = sort;
        param.setSort(sorts);
        RecordClient recordClient = new RecordClient(credential, "dst7urpLop4QdLAv3j");
        RecordPageInfo pageInfo = recordClient.queryRecords(param);
        Assertions.assertNotNull(pageInfo);
        Assertions.assertTrue((int)pageInfo.getRecords()[0].getFields().get("数字ID") > (int)pageInfo.getRecords()[1]
            .getFields().get("数字ID"));
    }

//    @Test
    public void testQueryAllRecords() {
        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
        QueryRecordRequest param = new QueryRecordRequest();
        RecordClient recordClient = new RecordClient(credential, "dstUezl1Yh4wVNzgwl");
        RecordInfo[] records = recordClient.queryAllRecords(param);
        Assertions.assertNotNull(records);
        Assertions.assertTrue(records.length > 1000);
    }

//    @Test
    public void testCreateRecords() {
        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
        RecordRequest param = new RecordRequest();
        RecordInfo[] createRecords = new RecordInfo[2];
        HashMap<String, Object> field1 = new HashMap<>();
        field1.put("数字ID", 999);
        field1.put("多行文本", "本质上和上面的需求是同一个999");
        RecordInfo record1 = new RecordInfo();
        record1.setFields(field1);
        HashMap<String, Object> field2 = new HashMap<>();
        RecordInfo record2 = new RecordInfo();
        field2.put("数字ID", 1000);
        field2.put("多行文本", "本质上和上面的需求是同一个1000");
        record2.setFields(field2);
        createRecords[0] = record1;
        createRecords[1] = record2;
        param.setRecords(createRecords);
        RecordClient recordClient = new RecordClient(credential, "dst7urpLop4QdLAv3j");
        RecordInfo[] records = recordClient.createRecords(param);
        Assertions.assertNotNull(records);
        Assertions.assertEquals(records.length, 2);
    }

//    @Test
    public void testModifyRecords() {
        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
        RecordRequest param = new RecordRequest();
        RecordInfo[] createRecords = new RecordInfo[2];
        HashMap<String, Object> field1 = new HashMap<>();
        field1.put("数字ID", 6666);
        field1.put("多行文本", "本质上和上面的需求是同一个6666");
        RecordInfo record1 = new RecordInfo();
        record1.setFields(field1);
        record1.setRecordId("recWfiMuFk60P");
        HashMap<String, Object> field2 = new HashMap<>();
        RecordInfo record2 = new RecordInfo();
        field2.put("数字ID", 88888);
        field2.put("多行文本", "本质上和上面的需求是同一个88888");
        record2.setFields(field2);
        record2.setRecordId("recZmiZ5QyrTC");
        createRecords[0] = record1;
        createRecords[1] = record2;
        param.setRecords(createRecords);
        RecordClient recordClient = new RecordClient(credential, "dst7urpLop4QdLAv3j");
        RecordInfo[] records = recordClient.modifyRecords(param);
        Assertions.assertNotNull(records);
        Assertions.assertEquals(records.length, 2);
    }

//    @Test
    public void testDeleteRecords() {
        ApiCredential credential = new ApiCredential(System.getenv("VIKA_TOKEN"));
        RecordClient recordClient = new RecordClient(credential, "dst7urpLop4QdLAv3j");
        DeleteRecordRequest param = new DeleteRecordRequest();
        param.setRecordIds(new String[] {"recWfiMuFk60P", "recZmiZ5QyrTC"});
        boolean result = recordClient.deleteRecords(param);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result);
    }
}
