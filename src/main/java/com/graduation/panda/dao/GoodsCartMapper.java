package com.graduation.panda.dao;

import com.graduation.panda.model.GoodsCart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsCartMapper {
    List<GoodsCart> selectByUserId(String userId);
    void insertCart(List<GoodsCart> goodsCarts);
    void deleteByKey(GoodsCart goodsCart);
    void insertSingleCart(GoodsCart goodsCart);
    void updateByKey(GoodsCart goodsCart);
}
