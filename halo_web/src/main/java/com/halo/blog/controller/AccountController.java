package com.halo.blog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.halo.blog.entity.User;
import com.halo.blog.model.dto.LoginDto;
import com.halo.blog.model.dto.RegisterDto;
import com.halo.blog.model.vo.UserVo;
import com.halo.blog.service.AccountService;
import com.halo.blog.service.UserService;
import com.halo.blog.util.JwtUtil;
import halo.base.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author Halo
 * @date Created in 2021/07/02 下午 09:26
 * @description 登录管理相关接口
 */
@RestController
@Api(value = "登录管理相关接口", tags = {"登录管理相关接口"})
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private AccountService accountService;

    @GetMapping("/mail/{email}")
    void simpleMailMessageTest(@PathVariable(name = "email") String email) {
        accountService.sentAuthMail(email);
    }

    @GetMapping("/get/auth/{email}")
    public Result getAuthCodeTest(@PathVariable String email) {
        return Result.success(accountService.getAuthCode(email));
    }

    @ApiOperation(value = "发送邮箱验证码", notes = "发送邮箱验证码")
    @GetMapping("/sent/{email}")
    public Result sentAuthCodeMail(@PathVariable String email) {
        QueryWrapper<User> emailQueryWrapper = new QueryWrapper<>();
        emailQueryWrapper.eq("email", email);
        User isRegister = userService.getOne(emailQueryWrapper);
        if (isRegister != null) {
            return Result.fail("该邮箱已注册账号");
        }
        boolean result = accountService.sentAuthMail(email);
        if (!result) {
            return Result.fail().message("验证码发送失败");
        }
        return Result.success().message("验证码发送成功");
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto) {

        String email = registerDto.getEmail();
        String userName = registerDto.getUsername();
        String authCode = registerDto.getAuthCode();

        System.out.println(email + userName + authCode);

        // 验证验证码是否正确
        if (!authCode.equals(accountService.getAuthCode(email))) {
            return Result.fail("验证码错误");
        }

        // 判断用户名是否已经注册
        if (accountService.isRegisterByUserName(userName)) {
            return Result.fail("用户名已被注册");
        }

        // 判断邮箱是否已经注册
        if (accountService.isRegisterByEmail(email)) {
            return Result.fail("邮箱已被注册");
        }

        // 构造用户类
        User user = new User();
        user.setUsername(userName);
        user.setPassword(SecureUtil.md5(registerDto.getPassword()));
        user.setEmail(email);
        user.setCreated(LocalDateTime.now());

        // 保存到数据库
        userService.save(user);

        // 从数据库中查询刚注册的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", registerDto.getEmail()).select("id", "username", "avatar", "email", "status");
        User newUser = userService.getOne(wrapper);

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(newUser, userVo);

        return Result.success(200, "注册成功", userVo);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        User user = userService.getOne(new QueryWrapper<User>().eq("email", email));
        Assert.notNull(user, "用户不存在");

        // 密码是否正确
        if (!user.getPassword().equals(SecureUtil.md5(password))) {
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
//    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}
