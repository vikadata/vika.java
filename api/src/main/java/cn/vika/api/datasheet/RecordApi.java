package cn.vika.api.datasheet;

import java.util.HashMap;

import cn.vika.api.exception.ApiBaseException;
import cn.vika.api.http.AbstractApi;
import cn.vika.api.http.ApiCredential;
import cn.vika.api.http.ApiHttpClient;
import cn.vika.api.model.AbstractModel;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.model.HttpResult;
import cn.vika.core.utils.StringUtil;

/**
 * the api for operate records
 *
 * @author Zoe Zheng
 * @date 2020/12/15 5:14 下午
 */
public class RecordApi extends AbstractApi implements IRecordApi {
    private static final String path = "/datasheets/%s/records";
    /**
     * datasheetId
     */
    private final String datasheetId;

    public RecordApi(ApiCredential credential, String datasheetId) {
        super(credential);
        this.datasheetId = datasheetId;
    }

    public RecordApi(ApiCredential credential, ApiHttpClient httpClient, String datasheetId) {
        super(credential, httpClient);
        this.datasheetId = datasheetId;
    }

    @Override
    public <T> T getRecords(AbstractModel model, GenericTypeReference<HttpResult<T>> responseType) {
        HashMap<String, String> params = new HashMap<>(10);
        model.toMap(params);
        HttpResult<T> result = getDefaultHttpClient().get(basePath() + model.toTemplateUri(params), HttpHeader.EMPTY,
            responseType, params);
        if (result.isSuccess()) {
            return result.getData();
        }
        throw new ApiBaseException(result.getCode(), result.getMessage());
    }

    private String basePath() {
        return StringUtil.format(path, datasheetId);
    }

}
