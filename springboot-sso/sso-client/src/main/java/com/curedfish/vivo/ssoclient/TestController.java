package com.curedfish.vivo.ssoclient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping(value = "/test")
    public void test(){
        int a =1;
        System.out.println("测试成功");
    }
}
