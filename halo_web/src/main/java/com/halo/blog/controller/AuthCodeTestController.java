package com.halo.blog.controller;

import com.halo.blog.entity.AuthCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author Halo
 * @create 2021/09/28 下午 03:41
 * @description 登录管理相关测试接口
 */
@RestController
@Api(value = "登录管理相关测试接口", tags = {"登录管理相关测试接口"})
public class AuthCodeTestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/set")
    public void setAuthCode(@RequestBody AuthCode authCode) {
        redisTemplate.opsForValue().set("email", authCode);
    }

    @GetMapping("/get/{key}")
    @ApiOperation(value = "根据key获取redis的值", notes = "根据key获取redis的值")
    public Object getAuthCode(@PathVariable String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @DeleteMapping("/delete/{key}")
    public boolean delete(@PathVariable String key) {
        redisTemplate.delete(key);
        return !redisTemplate.hasKey(key);
    }
}
