package cn.vika.client.datasheet;

import cn.vika.api.datasheet.AttachmentApi;
import cn.vika.api.http.ApiCredential;
import cn.vika.api.http.ApiHttpClient;
import cn.vika.client.datasheet.model.AttachmentInfo;
import cn.vika.client.datasheet.model.AttachmentRequest;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.api.model.HttpResult;

/**
 * attachment client for attachment api
 *
 * @author Zoe Zheng
 * @date 2020-12-17 16:28:38
 */
public class AttachmentClient {

    AttachmentApi attachmentApi;

    /**
     * base constructor
     *
     * @param credential credential vika developer token
     * @param datasheetId the datasheet id
     */
    public AttachmentClient(ApiCredential credential, String datasheetId) {
        attachmentApi = new AttachmentApi(credential, datasheetId);
    }

    /**
     * constructor with customer defined httpClient
     *
     * @param credential credential vika developer token
     * @param httpClient httpclient
     * @param datasheetId the datasheet id
     */
    public AttachmentClient(ApiCredential credential, ApiHttpClient httpClient, String datasheetId) {
        attachmentApi = new AttachmentApi(credential, httpClient, datasheetId);
    }

    public AttachmentInfo uploadAttachment(AttachmentRequest param) {
        GenericTypeReference<HttpResult<AttachmentInfo>> reference =
            new GenericTypeReference<HttpResult<AttachmentInfo>>() {};
        AttachmentInfo response = attachmentApi.uploadAttachment(param, reference);
        return response;
    }



}
