package com.ksd.blog.service.impl;

//import cn.hutool.system.UserInfo;
import com.ksd.blog.common.Role;
import com.ksd.blog.dto.RegisterDTO;
import com.ksd.blog.dto.UserInfo;
import com.ksd.blog.entity.User;
import com.ksd.blog.repository.UserRepository;
import com.ksd.blog.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    public List<User> getUsers() {
        return userRepository.findAll();
    }

private final UserRepository userRepository;

    @Override
    public User getByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    @Override
    public List<User> search(String keyword) {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getUserName().contains(keyword))
                .toList();
    }

    @Override
    @Transactional
    public void delete(String uid) {
        userRepository.deleteById(uid);
    }


    // ==================================================================
//    新登录注册
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
//    public boolean register(RegisterDTO dto){
//
////        if (repo.existsByUserName(userName)) return false;
////
////        User user = new User();
////        user.setUserId(UUID.randomUUID().toString());
////        user.setUserName(userName);
////        // 关键：存哈希！
////        user.setUserPassword(encoder.encode(rawPwd));
////        user.setUserEmail(email);
////        user.setUserRole("USER");
////        user.setUserRegisterTime(LocalDateTime.now());
////        repo.save(user);
////        return true;
//        if (repo.findByUserEmail(dto.getUserEmail()).isPresent()) return false;
//
//        User u = new User();
//        u.setUserId(UUID.randomUUID().toString());
//        u.setUserName(dto.getUserName());
//        u.setUserPassword(encoder.encode(dto.getUserPassword()));
//        u.setUserEmail(dto.getUserEmail());
////        u.setUserRole("");
//        u.setUserRole(Role.USER);
//        u.setUserRegisterTime(LocalDateTime.now());
//        repo.save(u);
//        return true;
//    }
    public boolean register(RegisterDTO dto) {
        // 1. 校验邮箱是否已注册（正确）
        if (repo.findByUserEmail(dto.getUserEmail()).isPresent()) {
            return false; // 邮箱已存在，注册失败
        }

        // 2. 创建新用户实体（不手动设置 userId）
        User u = new User();
        // u.setUserId(UUID.randomUUID().toString()); // 移除这行！由数据库/框架自动生成
        u.setUserName(dto.getUserName());
        u.setUserPassword(encoder.encode(dto.getUserPassword())); // 密码加密（正确）
        u.setUserEmail(dto.getUserEmail());
        u.setUserRole(Role.USER); // 设置默认角色（正确）
        u.setUserRegisterTime(LocalDateTime.now()); // 设置注册时间（正确）

        // 3. 保存用户（此时 userId 为 null，Hibernate 会执行 INSERT 新增操作）
        repo.save(u);
        return true;
    }

@Override
public Optional<UserInfo> loginByUserEmail(String email, String rawPwd) {
    return repo.findByUserEmail(email)
            .filter(u -> encoder.matches(rawPwd, u.getUserPassword()))
            .map(u -> new UserInfo(u.getUserId(), u.getUserName(), u.getUserRole().name()));
}

public Optional<UserInfo> loginByUserName(String userName, String rawPwd) {
        return repo.findByUserName(userName)
                .filter(u -> encoder.matches(rawPwd, u.getUserPassword()))
                .map(u -> new UserInfo(u.getUserId(), u.getUserName(), u.getUserRole().name()));
    }

}
