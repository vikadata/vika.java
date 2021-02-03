package cn.vika.api.exception;

/**
 * Api base exception
 *
 * @author Zoe Zheng
 * @date 2020-12-15 18:24:09
 */
public class VikaApiException extends RuntimeException {
    public static Integer DEFAULT_CODE = 50001;
    private static final long serialVersionUID = -8107100836846410486L;
    /**
     * credential not initial
     */

    private final Integer code;

    private final String message;

    public VikaApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Return the integer value of this status code.
     */
    public int code() {
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
