package com.halo.admin.security;

import cn.hutool.core.util.StrUtil;
import com.halo.admin.service.SysUserService;
import halo.base.entity.SysUser;
import halo.base.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器
 *
 * @author HALO
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    SysUserService sysUserService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 获取 JWT
        String jwt = request.getHeader(jwtUtils.getHeader());
        // 判断是否为空
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }
        // 解析 JWT
        Claims claim = jwtUtils.getClaimByToken(jwt);
        if (claim == null) {
            throw new JwtException("token 异常");
        }
        // 判断 JWT 是否过期
        if (jwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token 已过期");
        }

        // 获取用户名
        String username = claim.getSubject();

        // 获取用户的权限等信息
        SysUser sysUser = sysUserService.getByUsername(username);
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(username, null, userDetailService.getUserAuthority(sysUser.getId()));

        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }
}
