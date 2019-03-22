package com.graduation.panda.dao;

import com.graduation.panda.model.MyOrders;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface MyOrderMapper {
    /**
     * 查询订单信息
     * @param userId
     * @return
     */
    List<MyOrders> selectByUserId(String userId);

    /**
     * 查询相应的详细订单
     * @param map
     * @return
     */
    MyOrders selectSingleOrder(Map map);
}
