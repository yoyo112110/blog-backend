package com.ksd.blog.repository;

import com.ksd.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
//    User findByUserName(String userName);
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserEmail(String email);

    boolean existsByUserEmail(String userEmail);
}