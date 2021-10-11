package com.halo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.blog.entity.LinkUser;
import com.halo.blog.mapper.LinkUserMapper;
import com.halo.blog.service.LinkUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Halo
 * @since 2021-10-11
 */
@Service
public class LinkUserServiceImpl extends ServiceImpl<LinkUserMapper, LinkUser> implements LinkUserService {

    @Resource
    LinkUserMapper linkUserMapper;

    @Override
    @Cacheable(value = "HALO_LINK_MY_ALL")
    public List<LinkUser> getAllLink(Long uid) {
        QueryWrapper<LinkUser> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        return linkUserMapper.selectList(wrapper);
    }
}
