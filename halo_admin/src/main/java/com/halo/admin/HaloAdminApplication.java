package com.halo.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author HALO
 */
@SpringBootApplication
@MapperScan("com.halo.admin.mapper")
@ComponentScan(basePackages = {
        "halo.base.util",
        "com.halo.admin"
})
public class HaloAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaloAdminApplication.class, args);
    }

}
