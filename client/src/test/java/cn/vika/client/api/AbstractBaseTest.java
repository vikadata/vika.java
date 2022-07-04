package cn.vika.client.api;

import cn.vika.client.api.http.ApiCredential;

import static cn.vika.client.api.ConstantKey.TEST_API_KEY;
import static cn.vika.client.api.ConstantKey.TEST_DATASHEET_ID;
import static cn.vika.client.api.ConstantKey.TEST_HOST_URL;

/**
 * @author Shawn Deng
 */
public abstract class AbstractBaseTest {

    protected final String DATASHEET_ID = TEST_DATASHEET_ID.get();

    private static final String HOST_URL = TEST_HOST_URL.get();

    private static final String API_KEY = TEST_API_KEY.get();

    protected VikaApiClient vikaApiClient = new VikaApiClient(HOST_URL, new ApiCredential(API_KEY));
}
