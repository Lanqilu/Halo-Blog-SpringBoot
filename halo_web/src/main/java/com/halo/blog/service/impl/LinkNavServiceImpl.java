package com.halo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.blog.entity.LinkNav;
import com.halo.blog.mapper.LinkNavMapper;
import com.halo.blog.service.LinkNavService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 链接导航 服务实现类
 * </p>
 *
 * @author Halo
 * @since 2021-10-09
  */
@Service
public class LinkNavServiceImpl extends ServiceImpl<LinkNavMapper, LinkNav> implements LinkNavService {

    @Resource
    LinkNavMapper linkNavMapper;

    @Override
    //@Cacheable(value = "HALO_LINK_PUBLIC_ALL")
    public List<LinkNav> getAllPublicLink() {
        QueryWrapper<LinkNav> wrapper = new QueryWrapper<>();
        // 返回已发布并且状态正常的链接
        wrapper.eq("is_publish", 1).eq("link_status", 0);
        return linkNavMapper.selectList(wrapper);
    }
}
