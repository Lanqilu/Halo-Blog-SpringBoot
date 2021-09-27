package com.halo.blog;

import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;

/**
 * @author Halo
 * @create 2021/09/23 下午 12:10
 * @description
 */
public class MD5Test {
    @Test
    public void me5Test(){
        String s = SecureUtil.md5("111111");
        System.out.println(s);
    }
}
