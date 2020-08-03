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

    /**
     * 获取存储在静态变量中的 ApplicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 从静态变量 applicationContext 中获取 Bean，自动转型成所赋值对象的类型
     *
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量 applicationContext 中获取 Bean，自动转型成所赋值对象的类型
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> clazz) {
        assertContextInjected();
        return applicationContext.getBean(clazz);
    }

    /**
     * 实现 DisposableBean 接口，在 Context 关闭时清理静态变量
     *
     * @throws Exception
     */
    public void destroy() throws Exception {
        logger.debug("清除 MySpringContext 中的 ApplicationContext: {}", applicationContext);
        applicationContext = null;
    }

    /**
     * 实现 ApplicationContextAware 接口，注入 Context 到静态变量中
     *
     * @param applicationContext
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 断言 Context 已经注入
     */
    private static void assertContextInjected() {
        if (null == applicationContext)
            throw new RuntimeException("applicationContext 属性未注入，请在 spring-context.xml 配置中定义 MySpringContext");
    }

    /**
     * 刷新指定的bean
     * @param clazz
     */
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

    /**
     * 首字母小写
     *
     * @param name
     * @return
     */
    public static String enCaptureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);

    }
}