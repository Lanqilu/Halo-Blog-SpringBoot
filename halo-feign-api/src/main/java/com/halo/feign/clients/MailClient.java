package com.halo.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Halo
 * @create 2021/09/28 上午 12:23
 * @description
 */
@FeignClient("halo-mail")
public interface MailClient {
    @GetMapping("/mail/{email}")
    void SimpleMailMessageTest(@PathVariable(name = "email") String email);
}