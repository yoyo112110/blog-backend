package com.ksd.blog.entity;

import com.ksd.blog.common.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`user`")
public class User{
    //	用户id
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", length = 36, nullable = false, updatable = false)
    private String userId;

    //	用户名称
    @Column(name = "user_name",unique = true)
    private String userName;

    //	用户密码
    @Column(name = "user_password")
    private String userPassword;

    //	用户注册时间
    @Column(name = "user_register_time")
    private LocalDateTime userRegisterTime;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
    private Role userRole = Role.USER;   // 默认 USER
}
