package cn.vika.api.datasheet;

import cn.vika.api.model.AbstractModel;
import cn.vika.api.model.HttpResult;
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
    <T> T getRecords(AbstractModel params, GenericTypeReference<HttpResult<T>> responseType);

    /**
     * add records
     *
     * @param model body data for post
     * @param responseType response type
     * @return responseType
     */
    <T> T addRecords(AbstractModel model, GenericTypeReference<HttpResult<T>> responseType);

    /**
     * modify record
     *
     * @param model body data for post
     * @param responseType response type
     * @return responseType
     */
    <T> T modifyRecords(AbstractModel model, GenericTypeReference<HttpResult<T>> responseType);

    /**
     * delete records
     *
     * @param model body data for post
     * @return boolean
     */
    boolean deleteRecords(AbstractModel model);
}
