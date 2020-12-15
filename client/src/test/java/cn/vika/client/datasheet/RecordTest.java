package cn.vika.client.datasheet;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cn.vika.api.http.ApiCredential;
import cn.vika.client.datasheet.model.QueryRecordParam;
import cn.vika.client.datasheet.model.RecordDetail;
import cn.vika.client.datasheet.model.RecordPageInfo;

/**
 * test
 *
 * @author Zoe Zheng
 * @date 2020-12-16 16:40:39
 */
public class RecordTest {
    @Test
    public void testQueryRecords() {
        ApiCredential credential = new ApiCredential("uskKxOVgOuns3ugs9X2nbBU");
        QueryRecordParam param = new QueryRecordParam();
        RecordClient recordClient = new RecordClient(credential, "dst7urpLop4QdLAv3j");
        RecordPageInfo pageInfo = recordClient.queryRecords(param);
        Assertions.assertNotNull(pageInfo);
        Assertions.assertEquals(pageInfo.getTotal(), pageInfo.getRecords().length);
    }

    @Test
    public void testQueryAllRecords() {
        ApiCredential credential = new ApiCredential("uskKxOVgOuns3ugs9X2nbBU");
        QueryRecordParam param = new QueryRecordParam();
        RecordClient recordClient = new RecordClient(credential, "dstUezl1Yh4wVNzgwl");
        List<RecordDetail> records = recordClient.queryAllRecords(param);
        Assertions.assertNotNull(records);
        Assertions.assertTrue(records.size() > 1000);
    }
}
