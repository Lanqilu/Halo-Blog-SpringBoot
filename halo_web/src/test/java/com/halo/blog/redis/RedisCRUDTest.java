package com.halo.blog.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * @author Halo
 * @create 2021/10/11 下午 09:43
 * @description
 */
@SpringBootTest
public class RedisCRUDTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 增加
     */
    @Test
    public void c() {
        String key = "testRedis";
        String value = "HaloRedis";
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 读取
     */
    @Test
    public void r() {
        String key = "testRedis";
        Object value = redisTemplate.opsForValue().get(key);
        System.out.println(value);
    }

    /**
     * 更新同增加
     */
    @Test
    public void u() {
        String key = "testRedis";
        String value = "NewHaloRedis";
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 删除
     */
    @Test
    public void d() {
        String key = "testRedis";
        redisTemplate.delete(key);
    }
}
