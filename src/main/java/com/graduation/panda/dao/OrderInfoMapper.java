package com.graduation.panda.dao;

import com.graduation.panda.model.OrderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInfoMapper {
    void insertOrder(OrderInfo orderInfo);

    OrderInfo findByOrderId(String orderId);

    List<OrderInfo> findByUserId(String userId);
}
