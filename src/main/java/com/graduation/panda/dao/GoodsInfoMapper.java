package com.graduation.panda.dao;

import com.graduation.panda.model.GoodsInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsInfoMapper {
    List<GoodsInfo> findByKeyword(String keyword);
}
