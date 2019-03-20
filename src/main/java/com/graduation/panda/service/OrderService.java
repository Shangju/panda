package com.graduation.panda.service;

import com.graduation.panda.model.GoodsCart;
import com.graduation.panda.model.OrderGoods;
import com.graduation.panda.model.OrderInfo;

import java.util.List;

public interface OrderService {
    void insertOrder(List<GoodsCart> goodsCart,String orderId,String userId);
    void insertGoods(List<GoodsCart> goodsCart,String orderId);
    OrderInfo findByOrderId(String orderId);
    List<OrderInfo> findByUserId(String userId);
    List<OrderGoods> findGoodsByOrderId(String orderId);
}
