package com.halo.admin.security;


import com.halo.admin.service.SysUserService;
import halo.base.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询用户密码进行比较
 *
 * @author HALO
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户资料
        SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码不正确~");
        }
        return new AccountUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), getUserAuthority(sysUser.getId()));
    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     *
     * @param userId 用户Id
     * @return 权限信息
     */
    public List<GrantedAuthority> getUserAuthority(Long userId) {

        // 角色 ROLE_admin、菜单操作权限 sys:user:list
        // ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = sysUserService.getUserAuthorityInfo(userId);

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
