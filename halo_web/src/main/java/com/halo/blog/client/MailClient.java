package com.halo.blog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Halo
 * @create 2021/09/27 下午 08:41
 * @description
 */
@FeignClient("halo-mail")
public interface MailClient {
    // TODO 远程调用有问题
    @GetMapping("/mail/{email}")
    void SimpleMailMessageTest(@PathVariable(name = "email") String email);
}
