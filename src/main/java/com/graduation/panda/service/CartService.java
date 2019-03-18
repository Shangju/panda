package com.graduation.panda.service;

import com.graduation.panda.model.GoodsCart;

import java.util.List;

public interface CartService {

    /**
     * 批量插入Cookie中的购物车
     * @param goodsCarts
     */
    public void insertCart(List<GoodsCart> goodsCarts,String userId);

    /**
     * 根据用户的userId查询购物车信息
     * @return
     */
    List<GoodsCart> selectByUserId(String userId);

    /**
     * 插入一条数据
     * @param goodsCart
     */
    void insertSingleCart(GoodsCart goodsCart);

    void updateInfo(String productId,String type,String userId);

    void deleteSelect(List<GoodsCart> goodsCart);
}
