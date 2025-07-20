package com.ksd.blog.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {
    // 1. 密钥（使用 byte[] 类型，长度至少 256 位（32 字节），避免弱密钥）
    private static final String SECRET_STRING = "your-256-bit-secret-key-which-should-be-32-bytes-long!"; // 32字节示例
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes()); // 转换为 SecretKey

    // 过期时间（2小时，单位：毫秒）
    private static final long EXPIRATION = 7200000;

    // 生成 Token（使用新的签名方法）
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 存储用户名（可替换为用户ID等唯一标识）
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // 过期时间
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 新的签名方式：使用 SecretKey + 算法
                .compact();
    }

    // 验证 Token 并获取用户名（若验证失败会抛出异常）
    public static String verifyToken(String token) {
        return Jwts.parserBuilder() // 注意：使用 parserBuilder() 而非 parser()
                .setSigningKey(SECRET_KEY) // 设置密钥
                .build() // 构建解析器
                .parseClaimsJws(token) // 解析 Token
                .getBody()
                .getSubject(); // 获取存储的用户名
    }
}