package com.halo.blog.controller;


import com.halo.blog.common.Result;
import com.halo.blog.entity.Blog;
import com.halo.blog.entity.User;
import com.halo.blog.service.UserService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/index")
    public Result index() {
        User user = userService.getById(1L);
        return Result.success(user);
    }

    @RequiresAuthentication
    @GetMapping("/index2")
    public Result index2() {
        User user = userService.getById(1L);
        return Result.success(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {
        return Result.success(user);
    }

    // 通过 userId 查询用户信息
    @PostMapping("/userId/{userId}")
    public Result getUserInfo(@PathVariable(name = "userId") Long userId) {
        User user = userService.getById(userId);
        Assert.notNull(user, "该用户不存在或已被删除");
        // TODO 密码也会一起返回
        return Result.success(user);
    }
}
