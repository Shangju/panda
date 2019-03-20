package com.graduation.panda.service;

import com.graduation.panda.model.MyOrders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MyOrderService {
    /**
     * 查询所有与订单信息
     * @param userId
     * @return
     */
    List<MyOrders> selectByUserId(String userId);

    /**
     * 查询一条订单的信息
     * @param map
     * @return
     */
    MyOrders selectSingleOrder(Map map);

}
