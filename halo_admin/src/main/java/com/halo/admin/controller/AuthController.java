package com.halo.admin.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.halo.admin.common.lang.Const;
import com.halo.admin.common.lang.Result;
import com.halo.admin.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

/**
 * 登录控制器
 *
 * @author HALO
 */
@RestController
public class AuthController extends BaseController {

    @Autowired
    Producer producer;

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

        //-------测试---------
        // key = "halo";
        // code = "11111";

        // 生成验证码图片
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        // BASE64 编码
        final Base64.Encoder encoder = Base64.getEncoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encodeToString(outputStream.toByteArray());
        // 放入 Redis 中, 过期时间 120s
        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);

        return Result.succ(MapUtil.builder().put("key", key).put("captchaImg", base64Img).build());
    }

    /**
     * 获取用户信息接口
     */
    @GetMapping("/sys/userInfo")
    public Result userInfo(Principal principal) {

        SysUser sysUser = sysUserService.getByUsername(principal.getName());

        return Result.succ(MapUtil.builder()
                .put("id", sysUser.getId())
                .put("username", sysUser.getUsername())
                .put("avatar", sysUser.getAvatar())
                .put("created", sysUser.getCreated())
                .map()
        );
    }


}
