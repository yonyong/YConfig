package top.yonyong.yconfig.config.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author yonyong
 * @Description //配置
 * @Date 2020/7/17 11:20
 * @Param 
 * @return 
 **/
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyConfig {
    /**
     * 如果此value为空，修改值为获取当前group，不为空正常获取配置文件中指定key的val
     * @return
     */
    String value() default "";
    Class<?> clazz() default MyConfig.class;
}
