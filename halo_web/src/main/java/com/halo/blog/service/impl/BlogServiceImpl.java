package com.halo.blog.service.impl;

import com.halo.blog.entity.Blog;
import com.halo.blog.mapper.BlogMapper;
import com.halo.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
