package top.yonyong.yconfig.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import top.yonyong.yconfig.utils.StringUtils;

/**
 * @Describtion manual auto inject bean
 * @Author yonyong
 * @Date 2020/7/13 15:55
 * @Version 1.0.0
 **/
@Configuration
@ConditionalOnClass(ConfigContext.class)
public class ConfigContextAutoConfig {

    @Value("${config.center.group:DEFAULT_ENV}")
    private String group;

    @Bean(name = "applicationConfigContext")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @ConditionalOnMissingBean(ConfigContext.class)
    public ConfigContext myConfigContext() {
        ConfigContext configContext = ConfigContext.builder().build();
        //set group
        if (StringUtils.isBlank(group))
            group = "DEFAULT_ENV";
        //set vals
        configContext = configContext.toBuilder()
                .group(group)
                .build();
        return configContext;
    }
}
