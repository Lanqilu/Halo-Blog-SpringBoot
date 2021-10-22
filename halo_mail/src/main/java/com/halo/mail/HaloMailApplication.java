package com.halo.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Halo
 * @create 2021/10/21 下午 10:55
 * @description
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class HaloMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(HaloMailApplication.class, args);
    }
}
