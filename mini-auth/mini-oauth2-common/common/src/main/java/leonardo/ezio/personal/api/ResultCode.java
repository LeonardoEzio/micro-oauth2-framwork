package leonardo.ezio.personal.api;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UN_LOGGING(401, "未登录"),
    TOKEN_EXPIRED(402, "token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResultCode findByCode(long code){
        for (ResultCode value : values()) {
            if (value.getCode() == code){
                return value;
            }
        }
        return null;
    }
}
