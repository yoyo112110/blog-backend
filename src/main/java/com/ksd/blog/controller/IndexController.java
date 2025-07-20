package com.ksd.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksd.blog.entity.User;
import com.ksd.blog.service.impl.UserService;
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "success";
    }
}
