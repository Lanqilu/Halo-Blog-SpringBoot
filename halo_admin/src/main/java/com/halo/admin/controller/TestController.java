package com.halo.admin.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 Controller
 *
 * @author HALO
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public void hello() {
        System.out.println("Hello");
    }

}
