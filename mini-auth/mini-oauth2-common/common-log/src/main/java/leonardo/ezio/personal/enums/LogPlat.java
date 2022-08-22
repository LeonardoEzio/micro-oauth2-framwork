package leonardo.ezio.personal.enums;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 15:43
 *
 * 日志所属平台
 *
 */
public enum LogPlat {

    SN(0,"SN系统"),

    MES(1,"MES系统"),

    AUTH(2,"授权服务"),

    AFTER_SALE(3,"售后服务")

    ;

    private final Integer code;

    private final String desc;

    LogPlat(Integer code, String desc) {
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
