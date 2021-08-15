package com.halo.blog.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Halo
 * @date Created in 2021/07/02 下午 08:19
 * @description
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt){
        this.token = jwt;

    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
