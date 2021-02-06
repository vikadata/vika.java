package cn.vika.client.api.datasheet;

import cn.vika.client.api.model.AbstractModel;
import cn.vika.client.api.model.HttpResult;
import cn.vika.core.http.GenericTypeReference;

/**
 * test
 *
 * @author Zoe Zheng
 * @date 2020-12-15 17:19:41
 */
public interface IRecordApi {
    /**
     * record list
     *
     * @param params request query params
     * @param responseType response type
     * @return responseType
     */
    <T> T getRecords(String datasheetId, AbstractModel params, GenericTypeReference<HttpResult<T>> responseType);

    /**
     * add records
     *
     * @param model body data for post
     * @param responseType response type
     * @return responseType
     */
    <T> T addRecords(String datasheetId, AbstractModel model, GenericTypeReference<HttpResult<T>> responseType);

    /**
     * modify record
     *
     * @param model body data for post
     * @param responseType response type
     * @return responseType
     */
    <T> T modifyRecords(String datasheetId, AbstractModel model, GenericTypeReference<HttpResult<T>> responseType);

    /**
     * delete records
     *
     * @param model body data for post
     * @return boolean
     */
    boolean deleteRecords(String datasheetId, AbstractModel model);
}
