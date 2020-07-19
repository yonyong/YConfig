package top.yonyong.yconfig.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import top.yonyong.yconfig.mapper.TblConfigcenterMapper;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private TblConfigcenterMapper tblConfigcenterMapper;

    @Bean(name = "applicationConfigContext")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @ConditionalOnMissingBean(ConfigContext.class)
    public ConfigContext myConfigContext() {
        ConfigContext configContext = ConfigContext.builder().build();
        //set group
        if (StringUtils.isNotBlank(group))
            group = "DEFAULT_ENV";
        //set vals
        TblConfig tblConfig = TblConfig.builder().keyGroup(group).build();
        final List<TblConfig> tblConfigs = tblConfigcenterMapper.selectByExample(tblConfig);
        configContext = configContext.toBuilder()
                .vals(tblConfigs)
                .group(group)
                .build();
        return configContext;
    }
}
