package com.ksd.blog.controller;

import com.ksd.blog.common.Result;
import com.ksd.blog.entity.Link;
import com.ksd.blog.service.ILinkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    private final ILinkService linkService;

    public LinkController(ILinkService linkService) {
        this.linkService = linkService;
    }

    // 获取所有
    @GetMapping
    public Result<List<Link>> list() {
        return Result.ok(linkService.list());
    }

    // 根据 id 获取
    @GetMapping("/{id}")
    public Result<Link> get(@PathVariable String id) {
        Link link = linkService.getById(id);
        return link == null ? Result.fail("数据不存在") : Result.ok(link);
    }

    // 新增或修改
    @PostMapping
    public Result<Link> save(@RequestBody Link link) {
        return Result.ok(linkService.save(link));
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        linkService.deleteById(id);
        return Result.ok(null);
    }
}