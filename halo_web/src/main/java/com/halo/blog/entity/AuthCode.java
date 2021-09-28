package com.halo.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Halo
 * @create 2021/09/28 下午 03:38
 * @description 验证码，需要实现序列化接口负责无法存入 Redis 数据库
 */
@Data
public class AuthCode implements Serializable {
    private String email;
    private String code;
}
