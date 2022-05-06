package cn.vika.client.api.model.builder;

import cn.vika.client.api.model.field.property.BaseFieldProperty;
import cn.vika.client.api.model.field.property.EmptyProperty;

/**
 * @author tao
 */
public interface IPropertyOfCreateField {

    <T extends BaseFieldProperty> IBuildCreateField<T> withProperty(T property);

    IBuildCreateField<EmptyProperty> withoutProperty();
}
