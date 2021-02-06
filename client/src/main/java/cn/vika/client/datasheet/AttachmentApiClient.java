package cn.vika.client.datasheet;

import cn.vika.client.api.datasheet.AttachmentApi;
import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.HttpResult;
import cn.vika.client.datasheet.model.AttachmentInfo;
import cn.vika.client.datasheet.model.AttachmentRequest;
import cn.vika.core.http.GenericTypeReference;

/**
 * attachment client for attachment api
 *
 * @author Zoe Zheng
 * @date 2020-12-17 16:28:38
 */
@Deprecated
public class AttachmentApiClient {

    AttachmentApi attachmentApi;

    /**
     * constructor with customer defined httpClient
     *
     * @param httpClient httpclient
     */
    public AttachmentApiClient(ApiHttpClient httpClient) {
        attachmentApi = new AttachmentApi(httpClient);
    }

    public AttachmentInfo uploadAttachment(String datasheetId, AttachmentRequest param) throws ApiException {
        GenericTypeReference<HttpResult<AttachmentInfo>> reference =
                new GenericTypeReference<HttpResult<AttachmentInfo>>() {};
        AttachmentInfo response = attachmentApi.uploadAttachment(datasheetId, param, reference);
        return response;
    }

}
