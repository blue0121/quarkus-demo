package demo.quarkus.web.exception;

import java.text.MessageFormat;
import java.util.Map;

import demo.quarkus.web.util.JsonUtil;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
public enum ErrorCode {
    CLIENT_ERROR(400, 400000, "请求错误: {0}"),
    BAD_REQUEST(400, 400001, "请求错误"),
    INVALID_JSON(400, 400002, "Json格式错误"),

    NOT_FOUND(404, 404000, "找不到资源"),
    USER_NOT_EXIST(404, 400002, "用户 [{0}] 不存在"),

    SYSTEM_ERROR(500, 500000, "系统错误"),
    WEB_ERROR(500, 500001, "Web错误: {0}");

    int status;
    int code;
    String message;

    ErrorCode(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String toJson(Object...args) {
        var message = this.getMessage(args);
        Map<String, Object> map = Map.of("code", code, "message", message);
        return JsonUtil.toString(map);
    }

    public ErrorCodeException newException(Object...args) {
        return new ErrorCodeException(this, args);
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage(Object...args) {
        return args.length > 0 ? MessageFormat.format(message, args) : message;
    }
}
