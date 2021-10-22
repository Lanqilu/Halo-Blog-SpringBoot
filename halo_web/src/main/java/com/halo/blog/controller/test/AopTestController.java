package com.halo.blog.controller.test;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Halo
 * @create 2021/10/22 下午 08:11
 * @description
 */
@RestController
@Api(value = "AOP 测试接口", tags = {"AOP 测试接口"})
@RequestMapping("/test")
public class AopTestController {
    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("hello");
        return "hello";
    }
}
