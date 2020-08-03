package top.yonyong.yconfig.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author yonyong
 * @apiNote use without aop
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ConfigContextAutoConfig.class)
public @interface EnableYConfigConfiguration {
}
