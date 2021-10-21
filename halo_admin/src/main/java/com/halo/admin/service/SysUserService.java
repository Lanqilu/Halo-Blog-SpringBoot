package com.halo.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.admin.entity.SysUser;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-04-05
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    /**
     * 获取权限
     *
     * @param userId 用户Id
     * @return 权限字符串逗号隔开, 例如 ROLE_admin,ROLE_normal,sys:user:list
     */
    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Long roleId);

    void clearUserAuthorityInfoByMenuId(Long menuId);


}
