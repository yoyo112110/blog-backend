package com.ksd.blog.service;

import com.ksd.blog.entity.Ad;
import com.ksd.blog.entity.AdType;

import java.util.List;

public interface IAdService {
    List<AdType> listTypes();
    List<Ad> listActiveByType(String adTypeId);

    List<Ad> findAll();
}
