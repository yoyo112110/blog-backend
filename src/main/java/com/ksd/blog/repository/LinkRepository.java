package com.ksd.blog.repository;

import com.ksd.blog.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, String> {
    List<Link> findByLinkId(String LinkId);
}
