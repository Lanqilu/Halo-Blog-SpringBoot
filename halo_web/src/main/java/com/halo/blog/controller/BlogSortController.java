package com.halo.blog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.halo.blog.common.Result;
import com.halo.blog.entity.BlogSort;
import com.halo.blog.service.BlogSortService;
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
 * <p>
 * 博客分类表 前端控制器
 * </p>
 *
 * @author Halo
 * @since 2021-08-13
 */
@RestController
@Api(tags = "博客分类接口")
@RequestMapping("/blog/sort")
public class BlogSortController {

    @Autowired
    BlogSortService blogSortService;

    // 获取全部分类
    @GetMapping("/all")
    public Result getAllSort() {
        List<BlogSort> list = blogSortService.list(new QueryWrapper<BlogSort>().orderByDesc("sort"));
        return Result.success(list);
    }

    /**
     * 获取该用户的全部分类
     *
     * @param uerId
     * @return
     */
    @GetMapping("user/{uerId}")
    public Result getSortByUserId(@PathVariable(name = "uerId") Long uerId) {
        QueryWrapper<BlogSort> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", uerId).orderByDesc("sort");
        List<BlogSort> list = blogSortService.list(queryWrapper);
        return Result.success(list);
    }

    @GetMapping("/{sortId}")
    public Result getSortById(@PathVariable(name = "sortId") Long sortId) {
        BlogSort blogSort = blogSortService.getById(sortId);
        return Result.success(blogSort);
    }


    /**
     * 编辑分类
     *
     * @param blogSort
     * @return
     */
    @PostMapping("/edit")
    public Result addBlogSort(@Validated @RequestBody BlogSort blogSort) {
        BlogSort temp;
        String msg = "";
        int code = 200;
        if (blogSort.getId() != null) {
            temp = blogSortService.getById(blogSort.getId());
            msg = "编辑成功";
            Assert.isTrue((Objects.equals(temp.getUserId(), ShiroUtil.getProfile().getId())), "没有权限编辑");
        } else {
            temp = new BlogSort();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreateTime(LocalDateTime.now());
            temp.setStatus(0);
            temp.setClickCount(0);
            msg = "创建成功";
            code = 201;
        }
        temp.setUpdateTime(LocalDateTime.now());

        BeanUtil.copyProperties(blogSort, temp, "id", "userId", "clickCount", "updateTime", "createTime");

        blogSortService.saveOrUpdate(temp);

        return Result.success(code, msg, temp);
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/delete/{sortId}")
    public Result deleteBlogSort(@PathVariable Long sortId) {
        boolean res = blogSortService.removeById(sortId);
        return Result.success(200, "删除成功", res);
    }

}
