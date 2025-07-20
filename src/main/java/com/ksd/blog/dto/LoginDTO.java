package com.ksd.blog.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String userName;
    private String userPassword;
}