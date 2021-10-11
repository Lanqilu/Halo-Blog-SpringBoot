package com.halo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.blog.entity.Blog;
import com.halo.blog.mapper.BlogMapper;
import com.halo.blog.service.BlogService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-07-02
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Resource
    BlogMapper blogMapper;

    @Override
    @Cacheable(value = "HALO_BLOG_ALL")
    public IPage<Blog> getAllBlog(int currentPage) {
        Page<Blog> page = new Page<>(currentPage, 5);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        // 排除内容列
        wrapper.select("id", "user_id", "blog_title", "description", "created", "status", "blog_cover", "blog_like", "update_time");
        wrapper.orderByDesc("update_time");
        return blogMapper.selectPage(page, wrapper);
    }

    @Override
    @Cacheable(value = "HALO_BLOG_LIKE")
    public IPage<Blog> getMostLikeBlog(int blogCount) {
        Page<Blog> page = new Page<>(1, blogCount);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "user_id", "blog_title", "description", "created", "status", "blog_cover", "blog_like", "update_time");
        wrapper.orderByDesc("blog_like");
        return blogMapper.selectPage(page, wrapper);
    }

    @Override
    @Cacheable(value = "HALO_BLOG_BYID")
    public Blog getBlogById(long blogId) {
        Blog blog = blogMapper.selectById(blogId);
        Assert.notNull(blog, "该博客不存在或已被删除");
        return blog;
    }
}
