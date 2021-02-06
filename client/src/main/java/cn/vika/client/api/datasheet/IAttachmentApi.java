package cn.vika.client.api.datasheet;

import cn.vika.client.api.model.AbstractModel;
import cn.vika.client.api.model.HttpResult;
import cn.vika.core.http.GenericTypeReference;

/**
 * attachment api
 *
 * @author Zoe Zheng
 * @date 2020-12-17 16:15:54
 */
public interface IAttachmentApi {

    /**
     * upload datasheet attachment
     *
     * @param params add attachment data
     * @param responseType response type
     * @return responseType
     */
    <T> T uploadAttachment(String datasheetId, AbstractModel params, GenericTypeReference<HttpResult<T>> responseType) throws Exception;
}
