package com.graduation.panda.service.impl;

import com.graduation.panda.dao.MyOrderMapper;
import com.graduation.panda.dao.OrderGoodsMapper;
import com.graduation.panda.model.MyOrders;
import com.graduation.panda.model.OrderGoods;
import com.graduation.panda.service.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrderServiceImpl implements MyOrderService{
    @Autowired
    MyOrderMapper myOrderMapper;

    @Autowired
    OrderGoodsMapper orderGoodsMapper;

    @Override
    public List<MyOrders> selectByUserId(String userId){
        //查询所有订单的信息
        List<MyOrders> myOrders = myOrderMapper.selectByUserId(userId);
        for (MyOrders orders : myOrders){
            if (!orders.isPay()){
                orders.setStatus("未付款");
            }else {
                orders.setStatus("已付款");
            }
            List<OrderGoods> orderGoods = orderGoodsMapper.findByOrderId(orders.getOrderId());
            if (orderGoods.size() > 0){
                orders.setOrderGoods(orderGoods);
            }
        }
        return myOrders;
    }

    @Override
    public MyOrders selectSingleOrder(Map map){
        MyOrders myOrders = myOrderMapper.selectSingleOrder(map);
        if (!myOrders.isPay()){
            myOrders.setStatus("未付款");
        }else {
            myOrders.setStatus("已付款");
        }
        List<OrderGoods> orderGoods = orderGoodsMapper.findByOrderId(myOrders.getOrderId());
        if (orderGoods.size() > 0){
            myOrders.setOrderGoods(orderGoods);
        }
        return myOrders;
    }
}
