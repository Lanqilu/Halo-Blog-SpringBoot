package com.halo.blog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.halo.blog.common.Result;
import com.halo.blog.entity.BlogTag;
import com.halo.blog.service.BlogTagService;
import com.halo.blog.util.ShiroUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Halo
 * @since 2021-08-13
 */
@RestController
@Api(tags = "博客标签接口")
@RequestMapping("/blog/tag")
public class BlogTagController {

    @Autowired
    BlogTagService blogTagService;

    @GetMapping("/all")
    public Result getAllTag() {
        List<BlogTag> list = blogTagService.list();
        return Result.success(list);
    }

    @GetMapping("/{tagId}")
    public Result getTagById(@PathVariable(name = "tagId") Long tagId) {
        BlogTag blogTag = blogTagService.getById(tagId);
        System.out.println(blogTag);
        return Result.success(blogTag);
    }

    /**
     * 编辑Tag
     */
    @PostMapping("/edit")
    public Result editBlogSort(@Validated @RequestBody BlogTag blogTag) {
        BlogTag temp;
        String msg = "";
        int code = 200;
        if (blogTag.getId() != null) {
            temp = blogTagService.getById(blogTag.getId());
            msg = "编辑成功";
            Assert.isTrue((Objects.equals(temp.getUserId(), ShiroUtil.getProfile().getId())), "没有权限编辑");
        } else {
            temp = new BlogTag();
            temp.setUserId(2L);
//            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreateTime(LocalDateTime.now());
            temp.setStatus(1);
            temp.setClickCount(0);
            msg = "创建成功";
            code = 201;
        }
        temp.setUpdateTime(LocalDateTime.now());

        BeanUtil.copyProperties(blogTag, temp, "id", "userId", "clickCount", "updateTime", "createTime");

        blogTagService.saveOrUpdate(temp);

        return Result.success(code, msg, temp);
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/delete/{tagId}")
    public Result deleteBlogTag(@PathVariable Long tagId) {
        boolean res = blogTagService.removeById(tagId);
        return Result.success(200, "删除成功", res);
    }
}
