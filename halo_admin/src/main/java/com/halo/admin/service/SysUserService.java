package com.halo.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import halo.base.entity.SysUser;


/**
 * @author HALO
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser getByUsername(String username);

    /**
     * 获取权限
     *
     * @param userId 用户Id
     * @return 权限字符串逗号隔开, 例如 ROLE_admin,ROLE_normal,sys:user:list
     */
    String getUserAuthorityInfo(Long userId);

    /**
     * 发送验证码邮件
     *
     * @param email 电子邮箱地址
     * @return 是否发送成功 true: 发送成功 false: 发送失败
     */
    boolean sentAuthMail(String email);

    /**
     * 获取 Redis 验证码
     *
     * @param email 电子邮箱地址
     * @return 验证码
     */
    String getAuthCode(String email);

    /**
     * 判断邮箱是否注册
     *
     * @param email 电子邮箱地址
     * @return 该邮箱是否注册 <p>true: 该邮箱已经注册</p> <p>false: 该邮箱未注册</p>
     */
    boolean isRegisterByEmail(String email);

    /**
     * 判断用户名是否注册
     *
     * @param userName 用户名
     * @return 该用户名是否注册 <p>true: 该用户名已经注册</p> <p>false: 该用户名未注册</p>
     */
    boolean isRegisterByUserName(String userName);

}
