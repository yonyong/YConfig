*_EN | [中文](https://github.com/yonyong/YConfig/blob/master/readme_zh.md)_*

# YConfig - Non-invasive Configuration Center Plugin

## Introduction
> Similar to the nacos configuration center, the value of the crud attribute can be dynamically updated and updated in real time.

## Advantage
> Support annotation injection configuration, simple usage, minimize code intrusion.

## Use

### 1.Introducing dependencies
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```
If you use AOP configuration, you need to add additional dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
### 2.Import the plug-in jar package or dependency

### 3.Configuration file write configuration environment
```properties
config.center.group=defalut_env
```
### 4.Start the class add annotation switch to open the plug-in
```java
@SpringBootApplication
@EnableYConfigConfiguration
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
```
If AOP configuration is required, the annotation '' '@ enableyconfigconfiguration' '' needs to be replaced with '' '@ enableyconfigaopconfiguration```

### 5.Demo for Initialization
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
        
        //or
        defaultYConfigHandlerFactory.setV(list);
        defaultYConfigHandlerFactory.commit();
        return "SUC";
    }
}
```
### Get configuration
#### Way 1
```java
@RestController
@RequestMapping("/test")
public class TestController {

    //1. Inject the configuration container applicationconfigcontext. Note that the name is written by me and cannot be changed
    @Resource
    ConfigContext applicationConfigContext;

    @GetMapping("get")
    public Object get(){
        //2.How to get the current group（config.center.group=defalut_env）
        final String group = applicationConfigContext.getGroup();
        return group;
    }

    @GetMapping("getval/{val}")
    public Object getVal(@PathVariable String val){
        //3.How to get normal configuration
        final String group = applicationConfigContext.getValue(val);
        return group;
    }
}
```
####  Way 2，This method needs to enable the annotation configuration function
```java
@RestController
@RequestMapping("/test")
@Data //1.Add data annotation to construct set method for parameters
public class TestController {

    //2. Add an annotation to the configuration. The annotation value is the corresponding keyname of the configuration
    @MyConfig("opcl.url")
    String url;

    //3. Passes the current object into the annotation
    @MyConfig(clazz = TestController.class)
    @GetMapping("1")
    public Object test(){
        return "val:" + url;
    }
}
```

## Blog
> blog address： https://www.cnblogs.com/yonyong/p/13339583.html
