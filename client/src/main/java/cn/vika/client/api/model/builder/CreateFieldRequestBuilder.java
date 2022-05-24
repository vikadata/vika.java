package cn.vika.client.api.model.builder;

import cn.vika.client.api.model.CreateFieldRequest;
import cn.vika.client.api.model.field.FieldTypeEnum;
import cn.vika.client.api.model.field.property.BaseFieldProperty;
import cn.vika.client.api.model.field.property.EmptyProperty;

/**
 * step builder
 *
 * @author tao
 */
public class CreateFieldRequestBuilder implements IFieldTypeOfCreateField, INameOfCreateField, IPropertyOfCreateField {

    private final ContextOfCreateField context;

    public static IFieldTypeOfCreateField create() {
        return new CreateFieldRequestBuilder();
    }

    public CreateFieldRequestBuilder() {
        context = new ContextOfCreateField();
    }

    @Override
    public INameOfCreateField ofType(FieldTypeEnum fieldType) {
        context.setFieldType(fieldType);
        return this;
    }

    @Override
    public IPropertyOfCreateField withName(String name) {
        context.setName(name);
        return this;
    }

    @Override
    public <T extends BaseFieldProperty> IBuildCreateField<T> withProperty(T property) {
        context.setProperty(property);
        return new BuildCreateField<>(context);
    }

    @Override
    public IBuildCreateField<EmptyProperty> withoutProperty() {
        return new BuildCreateField<>(context);
    }

}

class BuildCreateField<T extends BaseFieldProperty> implements IBuildCreateField<T> {

    private final ContextOfCreateField context;

    public BuildCreateField(ContextOfCreateField context) {
        this.context = context;
    }

    @Override
    public CreateFieldRequest<T> build() {
        CreateFieldRequest<T> fieldRequest = new CreateFieldRequest<>();
        fieldRequest.setType(context.getFieldType().getFieldType());
        fieldRequest.setName(context.getName());
        fieldRequest.setProperty((T) context.getProperty());
        return fieldRequest;
    }

}


class ContextOfCreateField {

    private FieldTypeEnum fieldType;

    private String name;

    private BaseFieldProperty property;

    public FieldTypeEnum getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldTypeEnum fieldType) {
        this.fieldType = fieldType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseFieldProperty getProperty() {
        return property;
    }

    public void setProperty(BaseFieldProperty property) {
        this.property = property;
    }
}


