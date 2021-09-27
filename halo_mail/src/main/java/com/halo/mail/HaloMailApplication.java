package com.halo.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

//@EnableAsync // 异步调用
@SpringBootApplication
public class HaloMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaloMailApplication.class, args);
    }

}
