package com.halo.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.halo.blog.common.Result;
import com.halo.blog.entity.Blog;
import com.halo.blog.service.BlogService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Halo
 */
@RestController
@Api(tags = "博客接口")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        return Result.success(blogService.getAllBlog(currentPage));
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        return Result.success(blogService.getBlogById(id));
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Long blogId = blog.getId();
        // 判断是新建还是编辑
        if (blogId == null) {
            return blogService.newBlog(blog);
        } else {
            return blogService.editBlog(blog);
        }
    }

    /**
     * 根据文章ID，给对应文章点赞
     */
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

    /**
     * 获取点赞数最高的文章
     */
    @GetMapping("/blog/most/like/{blogCount}")
    public Result mostLike(@PathVariable(name = "blogCount") int blogCount) {
        return Result.success(blogService.getMostLikeBlog(blogCount));
    }

    /**
     * 根据用户 ID 查询，该用户文章的数目
     */
    @GetMapping("/blog/number/{userId}")
    public Result blogNumber(@PathVariable String userId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        int count = blogService.count(queryWrapper);
        return Result.success(count);
    }

    /**
     * 根据用户 ID 查询，返回该用户所有文章
     */
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
        Boolean res = blogService.deleteBlog(blogId);
        return res ? Result.success().message("删除成功") : Result.fail().message("删除失败");
    }

}
