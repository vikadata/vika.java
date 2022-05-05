package cn.vika.client.api.model.builder;

import cn.vika.client.api.model.field.FieldType;

/**
 * @author tao
 */
public interface IFieldTypeOfCreateField {

    /**
     *
     * @param fieldType
     * @return
     */
    INameOfCreateField ofType(FieldType fieldType);

}
