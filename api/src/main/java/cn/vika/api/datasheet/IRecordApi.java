package cn.vika.api.datasheet;

import cn.vika.api.model.AbstractModel;
import cn.vika.core.http.GenericTypeReference;
import cn.vika.core.model.HttpResult;

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
}
