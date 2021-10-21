package com.halo.admin.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.halo.admin.common.exception.CaptchaException;
import com.halo.admin.common.lang.Const;
import com.halo.admin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 验证码拦截器
 *
 * @author HALO
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String url = httpServletRequest.getRequestURI();
        String loginUrl = "/login";
        String post = "POST";

        // 如果来自 /login 并且是 POST 请求就校验验证码
        if (loginUrl.equals(url) && post.equals(httpServletRequest.getMethod())) {
            try {
                // 校验验证码
                validate(httpServletRequest);
            } catch (CaptchaException e) {
                // 交给认证失败处理器
                loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 校验验证码逻辑
     */
    private void validate(HttpServletRequest httpServletRequest) {

        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("key");

        // 判断是否为空
        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            throw new CaptchaException("验证码为空");
        }

        if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY, key))) {
            throw new CaptchaException("验证码错误");
        }

        // 一次性使用
        redisUtil.hdel(Const.CAPTCHA_KEY, key);
    }
}
