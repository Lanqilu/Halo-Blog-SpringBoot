package com.halo.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.blog.common.Result;
import com.halo.blog.entity.Blog;
import com.halo.blog.mapper.BlogMapper;
import com.halo.blog.service.BlogService;
import com.halo.blog.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Set;

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

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 删除全局 Redis 缓存
     */
    private void cleanRedis() {
        // 根据前缀匹配 keys
        Set<String> allKeys = redisTemplate.keys("HALO_BLOG_ALL" + "*");
        if (allKeys != null) {
            redisTemplate.delete(allKeys);
        }
        Set<String> likeKeys = redisTemplate.keys("HALO_BLOG_LIKE" + "*");
        if (likeKeys != null) {
            redisTemplate.delete(likeKeys);
        }
    }

    /**
     * 删除指定博客的 Redis 缓存
     *
     * @param blogId 博客ID
     */
    private void cleanRedis(Long blogId) {
        redisTemplate.delete("HALO_BLOG::" + blogId.toString());
    }


    @Override
    @Cacheable(cacheNames = "HALO_BLOG_ALL")
    public IPage<Blog> getAllBlog(int currentPage) {
        Page<Blog> page = new Page<>(currentPage, 5);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        // 排除内容列
        wrapper.select("id", "user_id", "blog_title", "description", "created", "status", "blog_cover", "blog_like", "update_time");
        wrapper.orderByDesc("update_time");
        return blogMapper.selectPage(page, wrapper);
    }

    @Override
    @Cacheable(cacheNames = "HALO_BLOG_LIKE")
    public IPage<Blog> getMostLikeBlog(int blogCount) {
        Page<Blog> page = new Page<>(1, blogCount);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "user_id", "blog_title", "description", "created", "status", "blog_cover", "blog_like", "update_time");
        wrapper.orderByDesc("blog_like");
        return blogMapper.selectPage(page, wrapper);
    }

    @Override
    @Cacheable(cacheNames = "HALO_BLOG", key = "#blogId")
    public Blog getBlogById(long blogId) {
        Blog blog = blogMapper.selectById(blogId);
        Assert.notNull(blog, "该博客不存在或已被删除");
        return blog;
    }


    @Override
    public Result editBlog(Blog blog) {
        Blog temp = blogMapper.selectById(blog.getId());
        Assert.isTrue((temp.getUserId().equals(ShiroUtil.getProfile().getId())), "没有权限编辑");
        blog.setUpdateTime(LocalDateTime.now());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "blogLike", "create_time");
        int insert = blogMapper.updateById(temp);
        // 从 Redis 中删除该博客缓存
        cleanRedis(blog.getId());
        return insert == 1 ? Result.success(200, "编辑成功", "") : Result.fail();
    }

    @Override
    public Result newBlog(Blog blog) {
        Blog temp = new Blog();
        temp.setUserId(ShiroUtil.getProfile().getId());
        temp.setCreated(LocalDateTime.now());
        temp.setCreateTime(LocalDateTime.now());
        temp.setCollectCount(0);
        temp.setStatus(0);
        blog.setUpdateTime(LocalDateTime.now());
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "blogLike", "create_time");
        int insert = blogMapper.insert(temp);
        // 删除 Redis 缓存
        cleanRedis();
        return insert == 1 ? Result.success(200, "创建成功", "") : Result.fail();
    }

    @Override
    @CacheEvict(cacheNames = "HALO_BLOG", key = "#blogId")
    public Boolean deleteBlog(long blogId) {
        return blogMapper.deleteById(blogId) == 1;
    }

    @Override
    public Integer giveLike(Long blogId) {
        // 数据库中查询点赞数
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("id", blogId).select("blog_like");
        Blog blog = blogMapper.selectOne(wrapper);
        // 点赞数加一
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", blogId).set("blog_like", blog.getBlogLike() + 1);
        blogMapper.update(null, updateWrapper);

        return blog.getBlogLike() + 1;
    }
}
