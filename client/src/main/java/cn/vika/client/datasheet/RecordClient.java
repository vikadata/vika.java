package cn.vika.client.datasheet;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.vika.api.datasheet.RecordApi;
import cn.vika.api.http.ApiCredential;
import cn.vika.api.http.ApiHttpClient;
import cn.vika.client.datasheet.model.QueryRecordParam;
import cn.vika.client.datasheet.model.RecordDetail;
import cn.vika.client.datasheet.model.RecordPageInfo;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.model.HttpResult;

/**
 * test
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:02:36
 */
public class RecordClient {
    private static final Integer maxPageSize = 1000;

    RecordApi recordApi;

    /**
     * base constructor
     *
     * @param credential credential vika developer token
     * @param datasheetId the datasheet id
     */
    public RecordClient(ApiCredential credential, String datasheetId) {
        recordApi = new RecordApi(credential, datasheetId);
    }

    /**
     * constructor with customer defined httpClient
     *
     * @param credential credential vika developer token
     * @param httpClient httpclient
     * @param datasheetId the datasheet id
     */
    public RecordClient(ApiCredential credential, ApiHttpClient httpClient, String datasheetId) {
        recordApi = new RecordApi(credential, httpClient, datasheetId);
    }

    public RecordPageInfo queryRecords(QueryRecordParam params) {
        GenericTypeReference<HttpResult<RecordPageInfo>> reference =
            new GenericTypeReference<HttpResult<RecordPageInfo>>() {};
        return recordApi.getRecords(params, reference);
    }

    public List<RecordDetail> queryAllRecords(QueryRecordParam params) {
        params.setPageSize(maxPageSize);
        params.setPageNum(1);
        GenericTypeReference<HttpResult<RecordPageInfo>> reference =
            new GenericTypeReference<HttpResult<RecordPageInfo>>() {};
        RecordPageInfo result = recordApi.getRecords(params, reference);
        Stream<RecordDetail> recordsStream = Arrays.stream(result.getRecords());
        int total = result.getTotal();
        if (total > maxPageSize) {
            Integer times = (int)Math.ceil(total / maxPageSize);
            for (int i = 1; i <= times; i++) {
                params.setPageNum(i + 1);
                RecordPageInfo tmp = recordApi.getRecords(params, reference);
                recordsStream = Stream.concat(recordsStream, Arrays.stream(tmp.getRecords()));
            }

        }
        return recordsStream.collect(Collectors.toList());
    }
}
