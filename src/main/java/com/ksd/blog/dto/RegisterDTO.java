package com.ksd.blog.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String userName;
    private String userPassword;
    private String userEmail;
    private String captcha;
}
