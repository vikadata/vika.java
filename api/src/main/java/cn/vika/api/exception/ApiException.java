package cn.vika.api.exception;

/**
 * Api excetiopn
 *
 * @author Zoe Zheng
 * @date 2020-12-15 18:24:09
 */
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = -6955751959643967224L;

    private Integer code;

    private Object data;


    public ApiException(ApiBaseException exception) {
        super(exception.getMessage());
        this.code = exception.code();
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

}
