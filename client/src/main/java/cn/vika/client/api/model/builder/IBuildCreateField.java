package cn.vika.client.api.model.builder;

import cn.vika.client.api.model.CreateFieldRequest;
import cn.vika.client.api.model.field.property.BaseFieldProperty;

/**
 * @author tao
 */
public interface IBuildCreateField <T extends BaseFieldProperty>  {

    CreateFieldRequest<T> build();

}
