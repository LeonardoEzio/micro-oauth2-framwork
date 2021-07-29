package cloud.security.constant;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 15:01
 *
 * 鉴权级别
 *
 */
public enum AuthorityLevel {

    /** 最低级别 - 不需要验证 */
    LOWEST(0),

    /** 仅仅校验token (已登录或者token在有效期内)*/
    TOKEN_ONLY(1),

    /** 验证菜单权限 */
    TOKEN_AND_MENU(2)

    ;


    private int value;

    AuthorityLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
