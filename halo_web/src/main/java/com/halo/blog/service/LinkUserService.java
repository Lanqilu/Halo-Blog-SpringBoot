package com.halo.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.blog.entity.LinkUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Halo
 * @since 2021-10-11
 */
public interface LinkUserService extends IService<LinkUser> {

    /**
     * 根据用户ID查询该用户创建的所有链接
     *
     * @param uid 用户ID
     * @return LinkUser 列表
     */
    List<LinkUser> getAllLink(Long uid);
}
