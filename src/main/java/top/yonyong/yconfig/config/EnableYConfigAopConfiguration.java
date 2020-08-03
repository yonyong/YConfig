package top.yonyong.yconfig.config;


import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import top.yonyong.yconfig.config.aop.SystemConfigAop;
import top.yonyong.yconfig.utils.MySpringContext;

import java.lang.annotation.*;

/**
 * @author yonyong
 * @apiNote use with aop
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ConfigContextAutoConfig.class,SystemConfigAop.class, MySpringContext.class, DefaultYConfigHandlerFactory.class})
@EnableAspectJAutoProxy
public @interface EnableYConfigAopConfiguration {
}
