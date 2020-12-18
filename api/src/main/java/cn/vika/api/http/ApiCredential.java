package cn.vika.api.http;

/**
 * the credential for api
 *
 * @author Zoe Zheng
 * @date 2020-12-15 17:57:29
 */
public class ApiCredential {
    /**
     * developer token
     */
    private String token;

    public String getToken() {
        return token;
    }

    public ApiCredential(String token) {
        this.token = token;
    }
}
