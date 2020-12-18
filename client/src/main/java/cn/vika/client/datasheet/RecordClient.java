package cn.vika.client.datasheet;

import java.util.Arrays;
import java.util.stream.Stream;

import cn.vika.api.datasheet.RecordApi;
import cn.vika.api.http.ApiCredential;
import cn.vika.api.http.ApiHttpClient;
import cn.vika.client.datasheet.model.*;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.model.HttpResult;

/**
 * record client for all record api
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:02:36
 */
public class RecordClient {
    private static final Integer MAX_PAGE_SIZE = 1000;

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

    public RecordPageInfo queryRecords(QueryRecordRequest params) {
        GenericTypeReference<HttpResult<RecordPageInfo>> reference =
            new GenericTypeReference<HttpResult<RecordPageInfo>>() {};
        return recordApi.getRecords(params, reference);
    }

    public RecordInfo[] queryAllRecords(QueryRecordRequest params) {
        params.setPageSize(MAX_PAGE_SIZE);
        params.setPageNum(1);
        GenericTypeReference<HttpResult<RecordPageInfo>> reference =
            new GenericTypeReference<HttpResult<RecordPageInfo>>() {};
        RecordPageInfo result = recordApi.getRecords(params, reference);
        Stream<RecordInfo> recordsStream = Arrays.stream(result.getRecords());
        int total = result.getTotal();
        if (total > MAX_PAGE_SIZE) {
            int times = (int)Math.ceil((float)total / MAX_PAGE_SIZE);
            for (int i = 1; i <= times; i++) {
                params.setPageNum(i + 1);
                RecordPageInfo tmp = recordApi.getRecords(params, reference);
                recordsStream = Stream.concat(recordsStream, Arrays.stream(tmp.getRecords()));
            }

        }
        return recordsStream.toArray(RecordInfo[]::new);
    }

    public RecordInfo[] createRecords(RecordRequest body) {
        GenericTypeReference<HttpResult<RecordResponse>> reference =
            new GenericTypeReference<HttpResult<RecordResponse>>() {};
        RecordResponse response = recordApi.addRecords(body, reference);
        return response.getRecords();
    }

    public RecordInfo[] modifyRecords(RecordRequest body) {
        GenericTypeReference<HttpResult<RecordResponse>> reference =
            new GenericTypeReference<HttpResult<RecordResponse>>() {};
        RecordResponse response = recordApi.modifyRecords(body, reference);
        return response.getRecords();
    }

    public boolean deleteRecords(DeleteRecordRequest param) {
        return recordApi.deleteRecords(param);
    }
}
