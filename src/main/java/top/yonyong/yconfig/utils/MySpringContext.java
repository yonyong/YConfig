package top.yonyong.yconfig.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MySpringContext implements ApplicationContextAware, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(MySpringContext.class);

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }


    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }


    public <T> T getBean(Class<T> clazz) {
        assertContextInjected();
        return applicationContext.getBean(clazz);
    }

    public void destroy() throws Exception {
        logger.debug("清除 MySpringContext 中的 ApplicationContext: {}", applicationContext);
        applicationContext = null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static void assertContextInjected() {
        if (null == applicationContext)
            throw new RuntimeException("applicationContext 属性未注入，请在 spring-context.xml 配置中定义 MySpringContext");
    }

    public void refresh(Class clazz) {
//        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
//        String beanName = enCaptureName(clazz.getSimpleName());
//        BeanDefinition beanDefinition = genericApplicationContext.getBeanDefinition(beanName);
//        genericApplicationContext.removeBeanDefinition(beanName);
//        logger.info("remove bean");
//        genericApplicationContext.registerBeanDefinition(beanName, beanDefinition);
//        logger.info("register bean");
        AnnotationConfigServletWebServerApplicationContext configApplicationContext = (AnnotationConfigServletWebServerApplicationContext) applicationContext;
//        configApplicationContext.refresh();
    }

    public static String enCaptureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);

    }
}