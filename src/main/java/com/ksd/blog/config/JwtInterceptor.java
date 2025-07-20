package com.ksd.blog.config;

import com.ksd.blog.common.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// （拦截器）
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从请求头获取 Token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("未登录");
        }
        token = token.substring(7); // 去掉 "Bearer "

        // 2. 验证 Token 并解析用户 ID
        String userId = JwtUtils.verifyToken(token); // 验证 Token 有效性
        request.setAttribute("currentUserId", userId); // 存入请求属性

        return true;
    }
}