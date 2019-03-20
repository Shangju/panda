package com.graduation.panda.dao;

import com.graduation.panda.model.OrderGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderGoodsMapper {
    void insertGoods(List<OrderGoods> orderGoods);
    List<OrderGoods> findByOrderId(String orderId);
}
