package com.graduation.panda.service;

import com.graduation.panda.model.GoodsInfo;

import java.util.List;

public interface GoodsInfoService {
    List<GoodsInfo> findByKeyword(String keyword);
}
