package cn.vika.client.datasheet;

import cn.vika.client.api.datasheet.RecordApi;
import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.HttpResult;
import cn.vika.client.datasheet.model.DeleteRecordRequest;
import cn.vika.client.datasheet.model.QueryRecordRequest;
import cn.vika.client.datasheet.model.RecordDetail;
import cn.vika.client.datasheet.model.RecordPageInfo;
import cn.vika.client.datasheet.model.RecordRequest;
import cn.vika.client.datasheet.model.RecordResponse;
import cn.vika.core.http.GenericTypeReference;

/**
 * record client for all record api
 * This class provides an entry point to all the Vikadata API record calls.
 *
 * @author Zoe Zheng
 * @date 2020-12-16 14:02:36
 */
@Deprecated
public class RecordApiClient {

    private static final Integer MAX_PAGE_SIZE = 1000;

    RecordApi recordApi;

    /**
     * constructor with customer defined httpClient
     *
     * @param httpClient httpclient
     */
    public RecordApiClient(ApiHttpClient httpClient) {
        recordApi = new RecordApi(httpClient);
    }

    public RecordPageInfo queryRecords(String datasheetId, QueryRecordRequest params) throws ApiException {
        GenericTypeReference<HttpResult<RecordPageInfo>> reference =
                new GenericTypeReference<HttpResult<RecordPageInfo>>() {};
        return recordApi.getRecords(datasheetId, params, reference);
    }

    public RecordDetail[] createRecords(String datasheetId, RecordRequest body) throws ApiException {
        GenericTypeReference<HttpResult<RecordResponse>> reference =
                new GenericTypeReference<HttpResult<RecordResponse>>() {};
        RecordResponse response = recordApi.addRecords(datasheetId, body, reference);
        return response.getRecords();
    }

    public RecordDetail[] modifyRecords(String datasheetId, RecordRequest body) throws ApiException {
        GenericTypeReference<HttpResult<RecordResponse>> reference =
                new GenericTypeReference<HttpResult<RecordResponse>>() {};
        RecordResponse response = recordApi.modifyRecords(datasheetId, body, reference);
        return response.getRecords();
    }

    public boolean deleteRecords(String datasheetId, DeleteRecordRequest param) throws ApiException {
        return recordApi.deleteRecords(datasheetId, param);
    }
}
