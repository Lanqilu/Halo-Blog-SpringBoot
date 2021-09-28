package com.halo.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.blog.entity.User;

/**
 * @author Halo
 * @create 2021/09/28 下午 03:10
 * @description
 */
public interface AccountService extends IService<User> {
    /**
     * 发送验证码邮件
     *
     * @param email 电子邮箱地址
     * @return 是否发送成功 true: 发送成功 false: 发送失败
     */
    boolean sentAuthMail(String email);

    /**
     * 获取 Redis 验证码
     *
     * @param email 电子邮箱地址
     * @return 验证码
     */
    String getAuthCode(String email);

    /**
     * 判断邮箱是否注册
     *
     * @param email 电子邮箱地址
     * @return 该邮箱是否注册 <p>true: 该邮箱已经注册</p> <p>false: 该邮箱未注册</p>
     */
    boolean isRegisterByEmail(String email);

    /**
     * 判断用户名是否注册
     *
     * @param userName 用户名
     * @return 该用户名是否注册 <p>true: 该用户名已经注册</p> <p>false: 该用户名未注册</p>
     */
    boolean isRegisterByUserName(String userName);
}
