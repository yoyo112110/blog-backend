package com.ksd.blog.controller;

import com.ksd.blog.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class EmailCaptchaController {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redis;

    // 1. 邮箱正则（类级别）
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @PostMapping("/sendEmailCaptcha")
    public Result<Void> send(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        // 2. 校验邮箱格式
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return Result.fail("邮箱格式不正确");
        }

        // 3. 生成 6 位数字验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        // 4. 写入 Redis，5 分钟过期
        redis.opsForValue().set("email:captcha:" + email, code, Duration.ofMinutes(5));

        // 5. 发送邮件
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("2125818660@qq.com");
        msg.setTo(email);
        msg.setSubject("注册验证码");
        msg.setText("您的验证码是：" + code + "，5分钟内有效。");
        mailSender.send(msg);

        return Result.ok();
    }
}