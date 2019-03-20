package com.graduation.panda.dao;

import com.graduation.panda.model.MyOrders;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface MyOrderMapper {
    List<MyOrders> selectByUserId(String userId);

    MyOrders selectSingleOrder(Map map);
}
