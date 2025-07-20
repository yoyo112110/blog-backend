package com.ksd.blog.service;

import com.ksd.blog.entity.Link;

import java.util.List;

public interface ILinkService {
    // 获取所有友情链接
    List<Link> list();

    // 根据ID获取链接
    Link getById(String id);

    // 新增或修改链接
    Link save(Link link);

    // 根据ID删除链接
    void deleteById(String id);
}
