package com.graduation.panda.service;

import com.graduation.panda.model.GoodsCart;
import com.graduation.panda.model.OrderGoods;
import com.graduation.panda.model.OrderInfo;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void insertOrder(List<GoodsCart> goodsCart,String orderId,String userId);
    void insertGoods(List<GoodsCart> goodsCart,String orderId);
    OrderInfo findByOrderId(String orderId);
    List<OrderInfo> findByUserId(String userId);
    List<OrderGoods> findGoodsByOrderId(String orderId);

    /**
     * 插入订单的运输方式
     * @param orderInfo
     */
    void updateByOrderId(OrderInfo orderInfo);

    /**
     * 管理员查询用户列表
     * @return
     */
    List<OrderInfo> findOrderLimit(Map map);

    /**
     * 查询用户总数量
     * @return
     */
    int selectCount(String orderId);

    /**
     * 删除订单接口
     * @param orderId
     */
    void deleteByOrderId(String orderId);

    /**
     * 编辑订单
     * @param orderInfo
     */
    void updateByPrimaryKey(OrderInfo orderInfo);

}
