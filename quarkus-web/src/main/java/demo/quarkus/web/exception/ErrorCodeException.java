package demo.quarkus.web.exception;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
public class ErrorCodeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;
    private final Object[] args;

    public ErrorCodeException(ErrorCode errorCode, Object...args) {
        super(errorCode.getMessage(args));
        this.errorCode = errorCode;
        this.args = args;
    }

    public int getStatus() {
        return errorCode.getStatus();
    }

    public int getCode() {
        return errorCode.getCode();
    }

    public String getJson() {
        return errorCode.toJson(args);
    }

}
