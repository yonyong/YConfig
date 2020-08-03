*_[EN](https://github.com/yonyong/YConfig/blob/master/readme.md) | 中文_*

# YConfig - 非侵入性配置中心插件

## 介绍
> 与nacos配置中心相似，可以动态的crud属性的值并实时更新。

## 优势：
> 支持注解注入配置，用法简单，最大程度降低代码的侵入性。

## 使用

### 1.引入依赖
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```
如果使用aop配置，需要额外添加依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
### 2.引入本插件jar包或依赖

### 3.配置文件写好配置环境
```properties
config.center.group=defalut_env
```
### 4.启动类添加注解开关开启插件
```java
@SpringBootApplication
@EnableYConfigConfiguration
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
```
如果需要aop配置功能，需要将```@EnableYConfigConfiguration```注解替换为```@EnableYConfigAopConfiguration```

### 5.初始化demo
```java
@Controller
public class InitJob{

    @Resource
    private DefaultYConfigHandlerFactory defaultYConfigHandlerFactory;

    public Object init(){
        List<Config> list = new ArrayList<>();
        Config config = Config.builder().keyName("opcl.url").keyValue("localhost:8080/opcl2.0").build();
        Config config2 = Config.builder().keyName("3A.url").keyValue("localhost//3A.url").build();
        Config config3 = Config.builder().keyName("MDM.url").keyValue("localhost:8888/MDM").build();
        list.add(config);
        list.add(config2);
        list.add(config3);
        
        defaultYConfigHandlerFactory.setVals(list);
        
        //或者
        defaultYConfigHandlerFactory.setV(list);
        defaultYConfigHandlerFactory.commit();
        return "SUC";
    }
}
```
### 获取配置
#### 方式一
```java
@RestController
@RequestMapping("/test")
public class TestController {

    //1. 注入配置容器applicationConfigContext，注意名称是我写死的，不能变
    @Resource
    ConfigContext applicationConfigContext;

    @GetMapping("get")
    public Object get(){
        //2.获取当前分组的方式（config.center.group=defalut_env）
        final String group = applicationConfigContext.getGroup();
        return group;
    }

    @GetMapping("getval/{val}")
    public Object getVal(@PathVariable String val){
        //3.获取普通配置的方式
        final String group = applicationConfigContext.getValue(val);
        return group;
    }
}
```
#### 方式二，此方法需要开启注解配置功能
```java
@RestController
@RequestMapping("/test")
@Data //1.添加data注解，为参数构造set方法
public class TestController {

    //2. 为配置添加注解，注解值为该配置对应的的keyname
    @MyConfig("opcl.url")
    String url;

    //3. 将当前对象传入注解
    @MyConfig(clazz = TestController.class)
    @GetMapping("1")
    public Object test(){
        return "val:" + url;
    }
}
```
## 博客
> blog地址： https://www.cnblogs.com/yonyong/p/13339583.html
