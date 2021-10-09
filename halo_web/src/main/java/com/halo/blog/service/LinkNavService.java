package com.halo.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.blog.entity.LinkNav;

import java.util.List;

/**
 * 链接导航表 服务类
 *
 * @author Halo
 * @since 2021-10-09
 */
public interface LinkNavService extends IService<LinkNav> {
    /**
     * 获取所有链接列表
     */
    List<LinkNav> getAllPublicLink();
}
