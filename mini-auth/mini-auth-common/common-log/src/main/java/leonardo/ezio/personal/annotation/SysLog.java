

package leonardo.ezio.personal.annotation;


import leonardo.ezio.personal.enums.LogType;
import leonardo.ezio.personal.enums.OperationType;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * 记录：日志名称，日志类型，日志备注
 *
 * @
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {

    /**
     * 日志类型 登录日志 业务日志
     * */
    LogType type() ;

    /**
     * 日志名称
     *
     * @return
     */
    String name() default "";

    /**
     * 操作类型 登录 登出 查询 增加 修改 ....
     * */
    OperationType operation() default OperationType.OTHER;

    /**
     * 备注信息
     * @return
     */
    String remark() default "";
}
