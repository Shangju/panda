package com.graduation.panda.dao;

import com.graduation.panda.model.GoodsDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsDetailMapper {
    GoodsDetail findByGoodsId(String goodsId);
}
