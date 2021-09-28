package com.halo.feign.clients;

import com.halo.model.request.AuthRequest;
import com.halo.model.dto.LoginDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Halo
 * @create 2021/09/28 上午 11:47
 * @description
 */
@FeignClient("halo-security")
public interface AuthClient {
    @PostMapping("/auth/login")
    String login(@RequestBody AuthRequest request);

    @PostMapping("/auth/login/test")
    String loginTest(@RequestBody LoginDto request);
}
