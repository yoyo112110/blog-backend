package com.ksd.blog.controller;

import com.ksd.blog.common.Result;
import com.ksd.blog.dto.LoginDTO;
import com.ksd.blog.dto.RegisterDTO;
import com.ksd.blog.dto.UserInfo;
import com.ksd.blog.entity.User;
import com.ksd.blog.repository.UserRepository;
import com.ksd.blog.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final StringRedisTemplate redis;
    private final UserRepository userRepository;
    /**
     * 用户注册（含邮箱验证码校验）
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody RegisterDTO dto) {
        String cache = redis.opsForValue()
                .get("email:captcha:" + dto.getUserEmail());
        if (cache == null || !cache.equalsIgnoreCase(dto.getCaptcha())) {
            return Result.fail("验证码错误或已过期");
        }
        boolean ok = userService.register(dto);
        if (!ok) {
            return Result.fail("注册失败：用户名或邮箱已存在");
        }
        redis.delete("email:captcha:" + dto.getUserEmail());
        return Result.ok();
    }

    @PostMapping("/loginByUserEmail")
    public Result<UserInfo> loginByUserEmail(@RequestBody LoginDTO dto) {
        return userService.loginByUserEmail(dto.getEmail(), dto.getUserPassword())
                .map(Result::ok)
                .orElse(Result.fail("邮箱或密码错误"));
    }
//    public Result<UserInfo> loginByUserName(@RequestBody LoginDTO dto) {
//        return userService.loginByUserName(dto.getUserName(), dto.getUserPassword())
//                .map(Result::ok)
//                .orElse(Result.fail("用户名或密码错误"));
//    }
//    @PostMapping("/login")
//
//    public Result<UserInfo> loginByUserName(@RequestBody LoginDTO dto,
//                                            HttpSession session) {
//        return userService.loginByUserName(dto.getUserName(), dto.getUserPassword())
//                .map(user -> {
//                    session.setAttribute("loginUser", user); // 关键：存入 Session
//                    return Result.ok(user);                  // 原样返回
//                })
//                .orElse(Result.fail("用户名或密码错误"));
//    }
//@PostMapping("/login")
//public Result<UserInfo> loginByUserName(@RequestBody LoginDTO dto,
//                                        HttpSession session) {
//    return userService.loginByUserName(dto.getUserName(), dto.getUserPassword())
//            .map(info -> {
//                // 1. 根据 DTO 再查实体
//                User user = userRepository.findById(info.getUserId())
//                        .orElseThrow(() -> new RuntimeException("用户不存在"));
//                // 2. 真正存 Session
//                session.setAttribute("loginUser", user);
//                return Result.ok(info);   // 3. 仍返回 DTO 给前端
//            })
//            .orElse(Result.fail("用户名或密码错误"));
//}

    @PostMapping("/login")
    public Result<UserInfo> loginByUserName(@RequestBody LoginDTO dto,
                                            HttpSession session) {
        return userService.loginByUserName(dto.getUserName(), dto.getUserPassword())
                .map(info -> {
                    // 1. 根据 DTO 里的 userId 查实体
                    User user = userRepository.findById(info.getUserId())
                            .orElseThrow(() -> new RuntimeException("用户不存在"));
                    // 2. 把实体塞进 Session
                    session.setAttribute("loginUser", user);
                    // 3. 仍返回 DTO 给前端
                    return Result.ok(info);
                })
                .orElse(Result.fail("用户名或密码错误"));
    }

    // 退出登录（删除 token）
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        // 删除 Redis / Cookie / Session 中的 token
        return Result.ok();
    }

//    // 刷新 token
//    @PostMapping("/refresh")
//    public Result<String> refresh(@RequestHeader("Authorization") String oldToken) { ... }
//
//    // 发送邮箱验证码
//    @PostMapping("/sendEmailCaptcha")
//    public Result<Void> sendEmailCaptcha(@RequestBody Map<String,String> body) { ... }
}