

package leonardo.ezio.personal.annotation;

import leonardo.ezio.personal.enums.LogPlat;

import java.lang.annotation.*;

/**
 * 日志模块名称注解
 **/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogModule {

    /**
     * 模块名称
     *
     * @return
     */
    String name() default "";

    /**
     * 日志所属平台
     * */
    LogPlat plat();

}
