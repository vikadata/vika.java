package cn.vika.api.http;

import static cn.vika.core.http.HttpHeaderConstants.USER_AGENT;

import cn.vika.core.http.DefaultHttpClient;

/**
 * api http client
 * defined the host and timeout
 *
 * @author Zoe Zheng
 * @date 2020-12-15 18:03:44
 */
public class ApiHttpClient extends DefaultHttpClient {
    private static final String HOST = "https://vika.cn";
    private static final String BASE_PAT = "/fusion";
    private static final String VERSION = "/v1";

    /**
     * http client
     */
    private final DefaultHttpClient defaultHttpClient;

    public ApiHttpClient() {
        defaultHttpClient = new DefaultHttpClient(HOST + BASE_PAT + VERSION);
        defaultHttpClient.addGlobalHeader(USER_AGENT, "vika-java");
    }

    public ApiHttpClient(String host) {
        defaultHttpClient = new DefaultHttpClient(host + BASE_PAT + VERSION);
        defaultHttpClient.addGlobalHeader(USER_AGENT, "vika-java");
    }

    public ApiHttpClient(String host, String version) {
        defaultHttpClient = new DefaultHttpClient(host + BASE_PAT + version);
        defaultHttpClient.addGlobalHeader(USER_AGENT, "vika-java");
    }

    public DefaultHttpClient getDefaultHttpClient() {
        return defaultHttpClient;
    }
}
