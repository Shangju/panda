package com.graduation.panda.dao;

import com.graduation.panda.model.GoodsInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GoodsInfoMapper {
    /**
     * 根据关键词keyword查找商品列表
     * @param keyword
     * @return
     */
    List<GoodsInfo> findByKeyword(String keyword);

    List<GoodsInfo> findByKeywordPriceAsc(String keyword);

    List<GoodsInfo> findByKeywordPriceDesc(String keyword);

    //分页
    int findByKeywordCount(String keyword);

    List<GoodsInfo> findByKeywordLimit(Map map);

    List<GoodsInfo> findByKeywordPriceAscLimit(Map map);

    List<GoodsInfo> findByKeywordPriceDescLimit(Map map);




}
