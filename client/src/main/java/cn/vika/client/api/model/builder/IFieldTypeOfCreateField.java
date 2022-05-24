package cn.vika.client.api.model.builder;

import cn.vika.client.api.model.field.FieldTypeEnum;

/**
 * @author tao
 */
public interface IFieldTypeOfCreateField {

    INameOfCreateField ofType(FieldTypeEnum fieldType);

}
