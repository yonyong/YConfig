package top.yonyong.yconfig.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yonyong.yconfig.config.ConfigContext;
import top.yonyong.yconfig.config.aop.MyConfig;
import top.yonyong.yconfig.utils.MySpringContext;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/version")
@Data
public class VersionController {

    @Autowired
    ConfigContext applicationConfigContext;
    @MyConfig("opcl.url")
    public String url = "1";
    @Autowired
    MySpringContext mySpringContext;

    @GetMapping(value="/serial", produces = "application/json;charset=utf-8")
    public Object getVersion(){
        Map<String,String> map = new HashMap(1);
        map.put("version","1.0.0");
        return map;
    }

    @GetMapping(value="/get", produces = "application/json;charset=utf-8")
    public Object fun(){
        Map<String,Object> map = new HashMap(1);
        map.put("val",applicationConfigContext);
        return map;
    }

    @GetMapping(value="/get/{key}", produces = "application/json;charset=utf-8")
    public Object fun1(@PathVariable String key){
        Map<String,Object> map = new HashMap(1);
        map.put("val",applicationConfigContext.getValue(key));
        return map;
    }

    @GetMapping(value="/test", produces = "application/json;charset=utf-8")
    @MyConfig(clazz = VersionController.class)
    public Object test(){
        VersionController versionController = mySpringContext.getBean("versionController");
        System.out.println(this == versionController);
        return url;
    }

    @GetMapping(value="/test1", produces = "application/json;charset=utf-8")
    public Object test1(){
        VersionController ver = mySpringContext.getBean("versionController");
        System.out.println(ver.getUrl());
        System.out.println(ver.url);
        return ver.getUrl();
    }

    public String getUrl(){
        return this.url;
    }

}
