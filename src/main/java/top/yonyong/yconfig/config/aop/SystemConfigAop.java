package top.yonyong.yconfig.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yonyong.yconfig.config.ConfigContext;
import top.yonyong.yconfig.utils.DataConverter;
import top.yonyong.yconfig.utils.MySpringContext;
import top.yonyong.yconfig.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yonyong
 */
@Aspect
@Component
@Slf4j
public class SystemConfigAop {

    @Autowired
    ConfigContext applicationConfigContext;

    @Autowired
    MySpringContext mySpringContext;

    @Pointcut("@annotation(top.yonyong.yconfig.config.aop.MyConfig)")
    public void pointcut(){}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        initStatus();

        //get Method
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyConfig myConfig = method.getAnnotation(MyConfig.class);
        // get class
        Class<?> clazz = myConfig.clazz();
        final Field[] declaredFields = clazz.getDeclaredFields();
        //get bean by class
        Object bean = mySpringContext.getBean(clazz);

        // invoke set method to set val
        for (Field declaredField : declaredFields) {
            final MyConfig annotation = declaredField.getAnnotation(MyConfig.class);
            if (null != annotation && StringUtils.isNotBlank(annotation.value())){
                String val = getVal(annotation.value());
                if (StringUtils.isBlank(val))
                    throw new RuntimeException("val can not be null");

                try {
                    buildMethod(clazz,bean,declaredField,val);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * get val from ConfigContext
     * @param key
     * @return
     */
    private String getVal(String key){
        if (StringUtils.isNotBlank(key)){
            return applicationConfigContext.getValue(key);
        }else {
            return applicationConfigContext.getGroup();
        }
    }

    /**
     * invoke set method
     * @param clz
     * @param obj
     * @param field
     * @param propertiedValue
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void buildMethod(Class<?> clz ,Object obj,Field field,String propertiedValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 获取属性的名字
        String name = field.getName();
        // 将属性的首字符大写， 构造get，set方法
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        // 获取属性的类型
        String type = field.getGenericType().toString();
        // 如果type是类类型，则前面包含"class "，后面跟类名
        // String 类型
        if (type.equals("class java.lang.String")) {
            Method m = clz.getMethod("set" + name, String.class);
            // invoke方法传递实例对象，因为要对实例处理，而不是类
            m.invoke(obj, propertiedValue);
        }
        // int Integer类型
        if (type.equals("class java.lang.Integer")) {
            Method m = clz.getMethod("set" + name, Integer.class);
            m.invoke(obj, Integer.parseInt(propertiedValue));
        }
        if (type.equals("int")) {
            Method m = clz.getMethod("set" + name, int.class);
            m.invoke(obj, (int) Integer.parseInt(propertiedValue));
        }
        // boolean Boolean类型
        if (type.equals("class java.lang.Boolean")) {
            Method m = clz.getMethod("set" + name, Boolean.class);
            if (propertiedValue.equalsIgnoreCase("true")) {
                m.invoke(obj, true);
            }
            if (propertiedValue.equalsIgnoreCase("false")) {
                m.invoke(obj, true);
            }
        }
        if (type.equals("boolean")) {
            Method m = clz.getMethod("set" + name, boolean.class);
            if (propertiedValue.equalsIgnoreCase("true")) {
                m.invoke(obj, true);
            }
            if (propertiedValue.equalsIgnoreCase("false")) {
                m.invoke(obj, true);
            }
        }
        // long Long 数据类型
        if (type.equals("class java.lang.Long")) {
            Method m = clz.getMethod("set" + name, Long.class);
            m.invoke(obj, Long.parseLong(propertiedValue));
        }
        if (type.equals("long")) {
            Method m = clz.getMethod("set" + name, long.class);
            m.invoke(obj, Long.parseLong(propertiedValue));
        }
        // 时间数据类型
        if (type.equals("class java.util.Date")) {
            Method m = clz.getMethod("set" + name, java.util.Date.class);
            m.invoke(obj, DataConverter.convert(propertiedValue));
        }
    }

    //check if init
    private boolean initStatus() {
        boolean rs = null != applicationConfigContext.getVals() && applicationConfigContext.getVals().size() != 0;
        if (!rs)
            throw new RuntimeException("you must need to init config container" +
                    ":you can implement the method 'int setVals(List<Config> vals)' " +
                    "in class AbstractYConfigHandler to init config container");
        return true;
    }
}
