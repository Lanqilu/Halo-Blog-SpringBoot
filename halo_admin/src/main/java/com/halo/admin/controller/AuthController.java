package com.halo.admin.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Producer;
import com.halo.admin.service.SysUserService;
import halo.base.common.HaloConst;
import halo.base.common.Result;
import halo.base.dto.RegisterDto;
import halo.base.entity.SysUser;
import halo.base.global.BaseMessageConf;
import halo.base.global.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * 登录控制器
 *
 * @author HALO
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private Producer producer;

    @Autowired
    SysUserService userService;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    /**
     * 验证码
     *
     * @return base64Img 验证码图片
     * @throws IOException IO 异常
     */
    @GetMapping("/captcha")
    public Result captcha() throws IOException {

        // 生成 Key
        String key = UUID.randomUUID().toString();
        // 生成验证码
        String code = producer.createText();

        //-----测试-------
        key = "11111";
        code = "11111";

        // 生成验证码图片
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        // BASE64 编码
        final Base64.Encoder encoder = Base64.getEncoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encodeToString(outputStream.toByteArray());
        // 放入 Redis 中, 过期时间 120s
        redisUtil.hset(HaloConst.CAPTCHA_KEY, key, code, 120);

        return Result.success(MapUtil.builder().put("key", key).put("captchaImg", base64Img).build());
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto) {

        String email = registerDto.getEmail();
        String userName = registerDto.getUsername();
        String authCode = registerDto.getAuthCode();
        String password = registerDto.getPassword();

        log.info(email + userName + authCode);

        // 验证验证码是否正确
        if (!authCode.equals(userService.getAuthCode(email))) {
            return Result.fail("验证码错误");
        }

        // 判断用户名是否已经注册
        if (userService.isRegisterByUserName(userName)) {
            return Result.fail("用户名已被注册");
        }

        // 判断邮箱是否已经注册
        if (userService.isRegisterByEmail(email)) {
            return Result.fail("邮箱已被注册");
        }

        // 构造用户类
        SysUser user = new SysUser();
        user.setUsername(userName);
        // 密码加密
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setStatus(HaloConst.STATUS_ON);
        user.setAvatar(HaloConst.DEFAULT_AVATAR);
        user.setCreated(LocalDateTime.now());

        // 保存到数据库
        userService.save(user);

        log.info(userName + " 注册成功");

        return Result.success(StatusCode.SUCCESS, BaseMessageConf.REGISTER_SUCCESS, "");
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return 是否发送成功消息
     */
    @GetMapping("/sent/{email}")
    public Result sentAuthCodeMail(@PathVariable String email) {
        QueryWrapper<SysUser> emailQueryWrapper = new QueryWrapper<>();
        emailQueryWrapper.eq("email", email);
        SysUser isRegister = userService.getOne(emailQueryWrapper);
        if (isRegister != null) {
            return Result.fail("该邮箱已注册账号");
        }
        boolean result = userService.sentAuthMail(email);
        if (!result) {
            return Result.fail().message("验证码发送失败");
        }
        return Result.success().message("验证码发送成功");
    }
}
