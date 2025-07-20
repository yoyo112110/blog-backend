package com.ksd.blog.controller;

import com.ksd.blog.common.Result;
import com.ksd.blog.entity.User;
import com.ksd.blog.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor   // ① Lombok 帮你生成构造函数
@RequestMapping("/api/users")
public class UserController {
//    @Autowired
//    private UserService userService;
//
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    private final IUserService userService;   // 面向接口编程
//    @PostMapping("/register")
//    public Result<User> register(@RequestBody User user) {
//        return Result.ok(userService.register(user));
//    }

//    @PostMapping("/login")
//    public Result<User> login(@RequestBody Map<String,String> param) {
//        return Result.ok(userService.login(param.get("userName"), param.get("userPassword")));
//    }

    @GetMapping("/current")
    public Result<User> current(@RequestParam String userName){
        return Result.ok(userService.getByUserName(userName));
    }


    @GetMapping("/{userName}")
    public User getUser(@PathVariable String userName) {
        return userService.getByUserName(userName);
    }

}
