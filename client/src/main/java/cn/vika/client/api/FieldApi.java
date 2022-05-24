package cn.vika.client.api;

import cn.vika.client.api.exception.ApiException;
import cn.vika.client.api.http.AbstractApi;
import cn.vika.client.api.http.ApiHttpClient;
import cn.vika.client.api.model.CreateFieldRequest;
import cn.vika.client.api.model.CreateFieldResponse;
import cn.vika.client.api.model.HttpResult;
import cn.vika.client.api.model.field.property.BaseFieldProperty;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.utils.StringUtil;

/**
 * @author wuyitao
 */
public class FieldApi extends AbstractApi {

    private static final String POST_FIELD_PATH = "/spaces/%s/datasheets/%s/fields";

    private static final String DELETE_FIELD_PATH = "/spaces/%s/datasheets/%s/fields/%s";


    public FieldApi(ApiHttpClient apiHttpClient) {
        super(apiHttpClient);
    }

    public CreateFieldResponse addField(String spaceId, String datasheetId,
            CreateFieldRequest<? extends BaseFieldProperty> field) throws ApiException {

        checkPostFieldPathArgs(spaceId, datasheetId);

        final String path = String.format(POST_FIELD_PATH, spaceId, datasheetId);
        HttpResult<CreateFieldResponse> result = getDefaultHttpClient().post(
                        path, new HttpHeader(), field,
                        new GenericTypeReference<HttpResult<CreateFieldResponse>>() {});
        return result.getData();
    }

    private void checkPostFieldPathArgs(String spaceId, String datasheetId) {

        if (!StringUtil.hasText(spaceId)) {
            throw new ApiException("space id must be not null");
        }

        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must be not null");
        }

    }

    public void deleteField(String spaceId, String datasheetId, String fieldId) {

        checkDeleteFieldPathArgs(spaceId, datasheetId, fieldId);

        final String path = String.format(DELETE_FIELD_PATH, spaceId, datasheetId, fieldId);

        getDefaultHttpClient().delete(path, new HttpHeader(), Void.class);

    }

    private void checkDeleteFieldPathArgs(String spaceId, String datasheetId, String fieldId) {
        if (!StringUtil.hasText(spaceId)) {
            throw new ApiException("space id must not be null");
        }

        if (!StringUtil.hasText(datasheetId)) {
            throw new ApiException("datasheet id must not be null");
        }

        if (!StringUtil.hasText(fieldId)) {
            throw new ApiException("fieldId id must not be null");
        }
    }
}
