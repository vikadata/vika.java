package cn.vika.client.api.model.builder;

import cn.vika.client.api.model.field.property.BaseFieldProperty;

/**
 * @author tao
 */
public interface IPropertyOfCreateField {

    /**
     *
     * @param property
     * @param <T>
     * @return
     */
    <T extends BaseFieldProperty> IBuildCreateField<T> withProperty(T property);

}
