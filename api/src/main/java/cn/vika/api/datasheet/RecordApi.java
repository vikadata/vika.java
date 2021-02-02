package cn.vika.api.datasheet;

import java.util.HashMap;

import cn.vika.api.exception.VikaApiException;
import cn.vika.api.http.AbstractApi;
import cn.vika.api.http.ApiCredential;
import cn.vika.api.http.ApiHttpClient;
import cn.vika.api.model.AbstractModel;
import cn.vika.core.exception.JsonConvertException;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.http.HttpHeader;
import cn.vika.core.model.HttpResult;

import static cn.vika.api.exception.VikaApiException.DEFAULT_CODE;

/**
 * the api for operate records
 *
 * @author Zoe Zheng
 * @date 2020/12/15 5:14 下午
 */
public class RecordApi extends AbstractApi implements IRecordApi {
    private static final String PATH = "/datasheets/%s/records";
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
    public <T> T getRecords(AbstractModel model, GenericTypeReference<HttpResult<T>> responseType)
        throws VikaApiException {
        HttpResult<T> result;
        HashMap<String, String> params = new HashMap<>(10);
        try {
            model.toMap(params, "");
            result = getDefaultHttpClient().get(basePath() + model.toTemplateUri(params), HttpHeader.EMPTY,
                responseType, params);
            if (result.isSuccess()) {
                return result.getData();
            }
        } catch (JsonConvertException e) {
            throw new VikaApiException(DEFAULT_CODE, e.getMessage());
        }
        throw new VikaApiException(result.getCode(), result.getMessage());

    }

    @Override
    public <T> T addRecords(AbstractModel model, GenericTypeReference<HttpResult<T>> responseType)
        throws VikaApiException {
        HttpResult<T> result;
        try {
            result = getDefaultHttpClient().post(basePath(), HttpHeader.EMPTY, model, responseType);
            if (result.isSuccess()) {
                return result.getData();
            }
        } catch (JsonConvertException e) {
            throw new VikaApiException(DEFAULT_CODE, e.getMessage());
        }
        throw new VikaApiException(result.getCode(), result.getMessage());
    }

    /**
     * modify record
     *
     * @param model body data for post
     * @param responseType response type
     * @return responseType
     */
    @Override
    public <T> T modifyRecords(AbstractModel model, GenericTypeReference<HttpResult<T>> responseType)
        throws VikaApiException {
        HttpResult<T> result;
        try {
            result = getDefaultHttpClient().patch(basePath(), HttpHeader.EMPTY, model, responseType);
            if (result.isSuccess()) {
                return result.getData();
            }
        } catch (JsonConvertException e) {
            throw new VikaApiException(DEFAULT_CODE, e.getMessage());
        }
        throw new VikaApiException(result.getCode(), result.getMessage());
    }

    /**
     * delete records
     *
     * @param model body data for post
     * @return boolean
     */
    @Override
    public boolean deleteRecords(AbstractModel model) {
        GenericTypeReference<HttpResult<Boolean>> responseType = new GenericTypeReference<HttpResult<Boolean>>() {};
        HashMap<String, String> params = new HashMap<>(10);
        model.toMap(params, "");
        HttpResult<Boolean> result = getDefaultHttpClient().delete(basePath() + model.toTemplateUri(params),
            HttpHeader.EMPTY, responseType, params);
        if (result.isSuccess()) {
            return result.isSuccess();
        }
        throw new VikaApiException(result.getCode(), result.getMessage());
    }

    @Override
    protected String basePath() {
        return String.format(PATH, datasheetId);
    }

}
