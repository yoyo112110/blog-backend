//package com.ksd.blog.config;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//public class SecurityConfig implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
//        String token = req.getHeader("Authorization");
//        if (token == null) { resp.setStatus(401); return false; }
//        Claims claims = JwtUtil.parse(token);
//        String role = claims.get("role", String.class);
//        String uri = req.getRequestURI();
//
//        // 游客只能读
//        if (uri.startsWith("/api/articles") && req.getMethod().equals("GET")) return true;
//        // 评论需要 USER/ADMIN
//        if (uri.startsWith("/api/comments") && req.getMethod().equals("POST") && !"USER".equals(role) && !"ADMIN".equals(role)) {
//            resp.setStatus(403); return false;
//        }
//        // 管理员接口
//        if (uri.startsWith("/api/admin") && !"ADMIN".equals(role)) {
//            resp.setStatus(403); return false;
//        }
//        return true;
//    }
//}