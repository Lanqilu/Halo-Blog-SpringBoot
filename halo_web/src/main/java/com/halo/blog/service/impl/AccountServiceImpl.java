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

import java.util.Random;

/**
 * @author Halo
 * @create 2021/09/28 下午 03:11
 * @description
 */
@Service
public class AccountServiceImpl extends ServiceImpl<UserMapper, User> implements AccountService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public boolean sentAuthMail(String email) {
        Random random = new Random();
        String code = String.valueOf(random.nextInt(99999));
        // redis 存验证码
        try {
            redisTemplate.opsForValue().set(email, code);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Halo 验证码测试邮件");
            message.setText("验证码是：" + code);
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
        return user == null;
    }

    @Override
    public boolean isRegisterByUserName(String userName) {
        QueryWrapper<User> userNameQueryWrapper = new QueryWrapper<>();
        userNameQueryWrapper.eq("username", userName);
        User user = userService.getOne(userNameQueryWrapper);
        return user == null;
    }


}
