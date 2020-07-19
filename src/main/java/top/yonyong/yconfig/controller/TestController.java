package top.yonyong.yconfig.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    VersionController versionController;

    @GetMapping("/1")
    public Object getUrl(){
        System.out.println(versionController.getUrl());
        System.out.println(versionController.url);
        return versionController.getUrl();
    }
}
