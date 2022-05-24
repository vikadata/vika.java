package cn.vika.client.api;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.CreateDatasheetRequest;
import cn.vika.client.api.model.CreateDatasheetResponse;
import cn.vika.client.api.model.HttpResult;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.utils.StringUtil;

/**
 * @author tao
 */
public class DatasheetApi extends AbstractApi {

    private static final String POST_DATASHEET_PATH = "/spaces/%s/datasheets";

    public DatasheetApi(ApiHttpClient apiHttpClient) {
        super(apiHttpClient);
    }

    public CreateDatasheetResponse addDatasheet(String spaceId,
            CreateDatasheetRequest datasheet) {

        checkPostDatasheetPathArgs(spaceId);

        final String path = String.format(POST_DATASHEET_PATH, spaceId);

        HttpResult<CreateDatasheetResponse> result = getDefaultHttpClient().post(
                path, new HttpHeader(), datasheet,
                new GenericTypeReference<HttpResult<CreateDatasheetResponse>>() {});
        return result.getData();
    }

    private void checkPostDatasheetPathArgs(String spaceId) {

        if (!StringUtil.hasText(spaceId)) {
            throw new ApiException("space id must be not null");
        }

    }

}
