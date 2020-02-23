package cn.enn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Administrator
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("test1")
    public String Test(){
        return "测试";
    }
}
