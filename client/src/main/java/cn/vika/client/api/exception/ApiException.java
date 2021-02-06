package cn.vika.client.api.exception;

/**
 * Api base exception
 *
 * @author Zoe Zheng
 * @date 2020-12-15 18:24:09
 */
public class ApiException extends Exception {

    private static final long serialVersionUID = -8107100836846410486L;

    public static Integer DEFAULT_CODE = 50001;

    public static String DEFAULT_ERROR_MESSAGE = "SERVER_ERROR";

    private Integer code;

    private String message;

    public ApiException(String message) {
        super(message);
        this.message = message;
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApiException(Exception e) {
        super(e);
        message = e.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    /**
     * Return the reason phrase of this status code.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
