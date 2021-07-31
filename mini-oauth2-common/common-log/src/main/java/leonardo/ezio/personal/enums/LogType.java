package leonardo.ezio.personal.enums;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 14:21
 *
 * 日志类型
 *
 */
public enum LogType {

    LOGGING(0,"登录日志"),

    BUSINESS(1,"业务日志")

    ;

    private final Integer code;

    private final String desc;


    LogType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
