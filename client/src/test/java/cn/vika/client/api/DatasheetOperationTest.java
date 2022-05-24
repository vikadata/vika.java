package cn.vika.client.api;

import java.util.ArrayList;
import java.util.List;

import cn.vika.client.api.http.ApiCredential;
import cn.vika.client.api.model.CreateDatasheetRequest;
import cn.vika.client.api.model.CreateDatasheetResponse;
import cn.vika.client.api.model.CreateFieldRequest;
import cn.vika.client.api.model.builder.CreateFieldRequestBuilder;
import cn.vika.client.api.model.field.FieldTypeEnum;
import cn.vika.client.api.model.field.property.EmptyProperty;
import cn.vika.client.api.model.field.property.SingleTextFieldProperty;
import org.junit.jupiter.api.Test;

import static cn.vika.client.api.ConstantKey.TEST_API_KEY;
import static cn.vika.client.api.ConstantKey.TEST_HOST_URL;
import static cn.vika.client.api.ConstantKey.TEST_SPACE_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author tao
 */
public class DatasheetOperationTest {

    private final String SPACE_ID = TEST_SPACE_ID.get();

    private final String HOST_URL = TEST_HOST_URL.get();

    private final String API_KEY = TEST_API_KEY.get();

    private final VikaApiClient vikaApiClient = new VikaApiClient(HOST_URL, new ApiCredential(API_KEY));

    @Test
    void testAddDatasheet() {
        CreateDatasheetRequest request = new CreateDatasheetRequest();
        request.setName("datasheet");
        CreateDatasheetResponse response = vikaApiClient.getDatasheetApi().addDatasheet(SPACE_ID, request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @Test
    void testAddDatasheetWithOtherInfo() {
        CreateDatasheetRequest request = new CreateDatasheetRequest();
        request.setName("datasheetWithOtherInfo");
        request.setDescription("description");
        request.setFolderId("fodBk37ziEJ22");
        request.setPreNodeId("dstjWc5z2BJZ44sC9S");
        SingleTextFieldProperty property = new SingleTextFieldProperty();
        property.setDefaultValue("default");
        CreateFieldRequest<SingleTextFieldProperty> singleSelectField = CreateFieldRequestBuilder
                .create()
                .ofType(FieldTypeEnum.SingleText)
                .withName("singleSelect")
                .withProperty(property)
                .build();
        CreateFieldRequest<EmptyProperty> textField = CreateFieldRequestBuilder
                .create()
                .ofType(FieldTypeEnum.Text)
                .withName("text")
                .withoutProperty()
                .build();
        List<CreateFieldRequest<?>> fields = new ArrayList<>();
        fields.add(singleSelectField);
        fields.add(textField);
        request.setFields(fields);
        CreateDatasheetResponse response = vikaApiClient.getDatasheetApi().addDatasheet(SPACE_ID, request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

}
