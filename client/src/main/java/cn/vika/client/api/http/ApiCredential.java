package cn.vika.client.api.http;

/**
 * the credential for api
 *
 * @author Zoe Zheng
 * @date 2020-12-15 17:57:29
 */
public class ApiCredential {

    /**
     * token for api
     */
    private String token;

    public ApiCredential() {
    }

    public ApiCredential(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
