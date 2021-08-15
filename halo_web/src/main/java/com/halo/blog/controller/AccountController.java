package com.halo.blog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.halo.blog.common.Result;
import com.halo.blog.common.dto.LoginDto;
import com.halo.blog.entity.User;
import com.halo.blog.service.UserService;
import com.halo.blog.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author Halo
 * @date Created in 2021/07/02 下午 09:26
 * @description
 */
@RestController
@Api(value = "登录管理相关接口", tags = {"登录管理相关接口"})
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody User userVO) {

        QueryWrapper<User> userNameQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> emailQueryWrapper = new QueryWrapper<>();

        userNameQueryWrapper.eq("username", userVO.getUsername());
        User user = userService.getOne(userNameQueryWrapper);
        if (user != null) {
            return Result.fail("用户名已被注册");
        }

        emailQueryWrapper.eq("email", userVO.getEmail());
        User email = userService.getOne(emailQueryWrapper);
        if (email != null) {
            return Result.fail("邮箱已被注册");
        }

        user = new User();
        user.setUsername(userVO.getUsername());
        user.setPassword(SecureUtil.md5(userVO.getPassword()));
        user.setEmail(userVO.getEmail());
        user.setCreated(LocalDateTime.now());

        userService.save(user);

        // TODO 发送邮件，进行账号激活

        return Result.success(200, "注册成功", user);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("email", loginDto.getEmail()));
        Assert.notNull(user, "用户不存在");

        // 密码是否正确
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("密码不正确");
        }

        // 生成 JWT
        String jwt = jwtUtil.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @ApiOperation(value = "管理员登录", notes = "管理员登录")
    @PostMapping("/login/admin")
    public Result loginAdmin(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("email", loginDto.getEmail()));
        Assert.notNull(user, "用户不存在");
        // 密码是否正确
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("密码不正确");
        }
        // 生成 JWT
        String jwt = jwtUtil.generateToken(user.getId());
        return Result.success(MapUtil.builder()
                .put("token", jwt)
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}
