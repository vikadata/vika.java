package cn.vika.client.api;

import cn.vika.client.api.http.ApiCredential;
import cn.vika.client.api.model.CreateFieldRequest;
import cn.vika.client.api.model.CreateFieldResponse;
import cn.vika.client.api.model.builder.CreateFieldRequestBuilder;
import cn.vika.client.api.model.field.FieldType;
import cn.vika.client.api.model.field.property.FormulaFieldProperty;
import cn.vika.client.api.model.field.property.SingleTextFieldProperty;
import cn.vika.client.api.model.field.property.option.Format;
import cn.vika.client.api.model.field.property.option.FormatType;
import cn.vika.client.api.model.field.property.option.NumberFormat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static cn.vika.client.api.ConstantKey.TEST_API_KEY;
import static cn.vika.client.api.ConstantKey.TEST_DATASHEET_ID;
import static cn.vika.client.api.ConstantKey.TEST_HOST_URL;
import static cn.vika.client.api.ConstantKey.TEST_SPACE_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author tao
 */
public class FieldAddOperationTest {

    private final String SPACE_ID = TEST_SPACE_ID.get();

    private final String DATASHEET_ID = TEST_DATASHEET_ID.get();

    private final String HOST_URL = TEST_HOST_URL.get();

    private final String API_KEY = TEST_API_KEY.get();

    private final VikaApiClient vikaApiClient = new VikaApiClient(HOST_URL, new ApiCredential(API_KEY));

    private String cacheFieldId;

    @Test
    void testCreateField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        SingleTextFieldProperty singleTextFieldProperty = new SingleTextFieldProperty();
        singleTextFieldProperty.setDefaultValue("hello");
        CreateFieldRequest<SingleTextFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.SingleText)
                .withName("java test")
                .withProperty(singleTextFieldProperty)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("java test");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateFormalField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        FormulaFieldProperty formulaFieldProperty = new FormulaFieldProperty();
        formulaFieldProperty.setExpression("1 / 3");
        Format<NumberFormat> numberFormatFormat = new Format<>();
        numberFormatFormat.setType(FormatType.Number);
        NumberFormat numberFormat = new NumberFormat();
        numberFormat.setPrecision(3);
        numberFormatFormat.setFormat(numberFormat);
        formulaFieldProperty.setFormat(numberFormatFormat);
        CreateFieldRequest<FormulaFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Formula)
                .withName("java test")
                .withProperty(formulaFieldProperty)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("java test");
        cacheFieldId = response.getId();
    }

    @AfterEach
    void tearDown() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        fieldApi.deleteField(SPACE_ID, DATASHEET_ID, cacheFieldId);
    }

}
