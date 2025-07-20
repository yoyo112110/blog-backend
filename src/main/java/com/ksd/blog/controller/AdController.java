package com.ksd.blog.controller;

import com.ksd.blog.entity.Ad;
import com.ksd.blog.service.impl.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

//    @GetMapping("/all")
//    public List<AdVo> all() {
//        return adService.listTypes().stream()
//                .map(type -> new AdVo(
//                        type.getAdTypeId(),
//                        type.getAdTypeTitle(),
//                        adService.listActiveByType(type.getAdTypeId())))
//                .toList();
//    }

    @GetMapping("/all")
    public List<Ad> getAll() {
        return adService.findAll();
    }
}
