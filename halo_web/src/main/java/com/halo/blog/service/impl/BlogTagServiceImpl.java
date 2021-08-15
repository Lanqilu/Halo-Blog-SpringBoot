package com.halo.blog.service.impl;

import com.halo.blog.entity.BlogTag;
import com.halo.blog.mapper.BlogTagMapper;
import com.halo.blog.service.BlogTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Halo
 * @since 2021-08-13
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {

}
