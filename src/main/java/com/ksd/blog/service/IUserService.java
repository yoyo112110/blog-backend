package com.ksd.blog.service;

import com.ksd.blog.dto.RegisterDTO;
import com.ksd.blog.dto.UserInfo;
import com.ksd.blog.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
//    User saveUser(User user);
//    User getUserByUserName(String userName);
    /** 注册/保存用户 */
//    User register(User user);

    /** 根据用户名登录 */
//    User login(String userName, String rawPassword);

    /** 根据用户名查询用户 */
    User getByUserName(String userName);

    /** 模糊搜索用户名 */
    List<User> search(String keyword);

    /** 删除用户 */
    void delete(String uid);


    //=============================================================
//    boolean register(String userName, String rawPwd, String email);
//    String login(String userName, String rawPwd);
//Optional<UserInfo> login(String userName, String rawPwd);
    boolean register(RegisterDTO dto);//原注册
//    UserInfo login(String email, String rawPwd);
//    void register(RegisterDTO dto);
Optional<UserInfo> loginByUserEmail(String userName, String rawPwd);
Optional<UserInfo> loginByUserName(String userName, String rawPwd);

//    User findByUserName(String userName);
}