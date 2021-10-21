package com.halo.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.blog.entity.User;
import com.halo.blog.mapper.UserMapper;
import com.halo.blog.service.AccountService;
import com.halo.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

/**
 * @author Halo
 * @create 2021/09/28 下午 03:11
 * @description
 */
@Service
public class AccountServiceImpl extends ServiceImpl<UserMapper, User> implements AccountService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public boolean sentAuthMail(String email) {
        // 先从 Redis 中查询
        String authCode = getAuthCode(email);
        // 如果 Redis 中没有，才发送
        if (Objects.equals(authCode, "")) {
            Random random = new Random();
            authCode = String.valueOf(random.nextInt(99999));
            redisTemplate.opsForValue().set(email, authCode);
        }
        // redis 存验证码
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Halo 验证码测试邮件");
            message.setText("验证码是：" + authCode);
            message.setTo(email);
            message.setFrom("1379978893@qq.com");
            mailSender.send(message);
            System.out.println("邮件发送成功");
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public String getAuthCode(String email) {
        Object o = redisTemplate.opsForValue().get(email);
        if (o != null) {
            return o.toString();
        } else {
            return "";
        }
    }

    @Override
    public boolean isRegisterByEmail(String email) {
        QueryWrapper<User> emailQueryWrapper = new QueryWrapper<>();
        emailQueryWrapper.eq("email", email);
        User user = userService.getOne(emailQueryWrapper);
        return user != null;
    }

    @Override
    public boolean isRegisterByUserName(String userName) {
        QueryWrapper<User> userNameQueryWrapper = new QueryWrapper<>();
        userNameQueryWrapper.eq("username", userName);
        User user = userService.getOne(userNameQueryWrapper);
        return user != null;
    }


}
