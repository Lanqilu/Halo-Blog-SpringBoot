package com.halo.blog.util;

import com.halo.blog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author Halo
 * @date Created in 2021/07/02 下午 10:01
 * @description
 */
public class ShiroUtil {
    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
