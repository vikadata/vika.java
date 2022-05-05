package cn.vika.client.api;

import cn.vika.client.api.http.ApiCredential;
import cn.vika.client.api.model.CreateDatasheetRequest;
import cn.vika.client.api.model.CreateDatasheetResponse;
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
        request.setName("java test");
        CreateDatasheetResponse response = vikaApiClient.getDatasheetApi().addDatasheet(SPACE_ID, request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

}
