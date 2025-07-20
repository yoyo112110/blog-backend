package com.ksd.blog.service.impl;

import com.ksd.blog.entity.Ad;
import com.ksd.blog.entity.AdType;
import com.ksd.blog.entity.Article;
import com.ksd.blog.repository.AdRepository;
import com.ksd.blog.repository.AdTypeRepository;
import com.ksd.blog.service.IAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService implements IAdService {

    private final AdTypeRepository adTypeRepository;
    private final AdRepository adRepository;

    public List<AdType> listTypes() {
        return adTypeRepository.findAll();
    }

//    public List<Ad> listActiveByType(String adTypeId) {
//        LocalDateTime now = LocalDateTime.now();
//        return adRepository.findByAdTypeIdAndAdBeginTimeLessThanEqualAndAdEndTimeGreaterThanEqualOrderByAdSortAsc(
//                adTypeId, now, now);
//    }
//@Override
//public List<Ad> listActiveByType(String adTypeId) {
//    return adRepository.findActiveByTypeId(adTypeId, LocalDateTime.now());
//}
    @Override
    public List<Ad> listActiveByType(String adTypeId) {
        return adRepository.findAll(); // 暴力全查
    }

    @Override
    public List<Ad> findAll() {
        return adRepository.findAll();
    }
}
