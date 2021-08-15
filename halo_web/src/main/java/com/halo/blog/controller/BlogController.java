package com.halo.blog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.halo.blog.util.ShiroUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.util.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.blog.common.Result;
import com.halo.blog.entity.Blog;
import com.halo.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@RestController
@Api(tags = "博客接口")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.success(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客不存在或已被删除");
        System.out.println("成功返回博客：" + id);
        return Result.success(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp;
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            Assert.isTrue((temp.getUserId() == ShiroUtil.getProfile().getId()), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        blog.setUpdateTime(LocalDateTime.now());

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "blogLike", "create_time");
        blogService.saveOrUpdate(temp);

        return Result.success(null);
    }

    // 根据文章ID，给对应文章点赞
    @PostMapping("/blog/like/{blogId}")
    public Result like(@PathVariable(name = "blogId") Long blogId) {
        // TODO 一个用户只能点一个赞
        Blog blog = blogService.getById(blogId);
        Integer like = blog.getBlogLike();
        like++;
        blog.setBlogLike(like);
        blogService.saveOrUpdate(blog);
        return Result.success(200, "点赞成功", like);
    }

    // 获取点赞数最高的文章
    @GetMapping("/blog/most/like/{blogCount}")
    public Result mostLike(@PathVariable(name = "blogCount") Long blogCount) {
        Page page = new Page(1, blogCount);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("blog_like"));
        System.out.println(pageData);
        return Result.success(pageData);
    }

    // 根据用户 ID 查询，该用户文章的数目
    @GetMapping("/blog/number/{userId}")
    public Result blogNumber(@PathVariable String userId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        int count = blogService.count(queryWrapper);
        return Result.success(count);
    }

    // 根据用户 ID 查询，返回该用户所有文章
    @GetMapping("/blog/article/{userId}")
    public Result blogArticle(@PathVariable String userId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Blog> list = blogService.list(queryWrapper);
        return Result.success(list);
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/blog/delete/{blogId}")
    public Result deleteBlogByBlogId(@PathVariable Long blogId) {
        boolean res = blogService.removeById(blogId);
        return Result.success(200, "删除成功", res);
    }

}
