package cn.vika.client.api.model.builder;

import cn.vika.client.api.model.CreateFieldRequest;
import cn.vika.client.api.model.field.FieldType;
import cn.vika.client.api.model.field.property.BaseFieldProperty;

/**
 * @author tao
 */
public class CreateFieldRequestBuilder{

    public static IFieldTypeOfCreateField create() {
        return new FieldTypeOfCreateField(new ContextOfCreateField());
    }

}

class ContextOfCreateField {
    private FieldType fieldType;
    private String name;
    private BaseFieldProperty property;

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
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

class FieldTypeOfCreateField implements IFieldTypeOfCreateField {

    private final ContextOfCreateField context;

    FieldTypeOfCreateField(ContextOfCreateField context) {
        this.context = context;
    }

    @Override
    public INameOfCreateField ofType(FieldType fieldType) {
        context.setFieldType(fieldType);
        return new NameOfCreateField(context);
    }

}

class NameOfCreateField implements INameOfCreateField {

    private final ContextOfCreateField context;

    NameOfCreateField(ContextOfCreateField context) {
        this.context = context;
    }

    @Override
    public IPropertyOfCreateField withName(String name) {
        context.setName(name);
        return new PropertyOfCreateField(context);
    }

}

class PropertyOfCreateField implements IPropertyOfCreateField {

    private final ContextOfCreateField context;

    PropertyOfCreateField(ContextOfCreateField context) {
        this.context = context;
    }

    @Override
    public <T extends BaseFieldProperty> IBuildCreateField<T> withProperty(T property) {

        // TODO: 泛型正确性判断

        context.setProperty(property);
        return new BuildCreateField<>(context);
    }

}

class BuildCreateField<T extends BaseFieldProperty>  implements IBuildCreateField<T> {

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


