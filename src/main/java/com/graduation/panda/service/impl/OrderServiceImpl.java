package com.graduation.panda.service.impl;

import com.graduation.panda.dao.OrderGoodsMapper;
import com.graduation.panda.dao.OrderInfoMapper;
import com.graduation.panda.model.GoodsCart;
import com.graduation.panda.model.OrderGoods;
import com.graduation.panda.model.OrderInfo;
import com.graduation.panda.service.OrderService;
import com.graduation.panda.utils.MakeNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderGoodsMapper goodsMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Override
    public void insertOrder(List<GoodsCart> goodsCart,String orderId,String userId){
        double totalPrice = 0;
        for (GoodsCart cart : goodsCart){
            if (cart.isChecked()){
                totalPrice = totalPrice + cart.getQuantity() * cart.getProductPrice();
            }
        }
        OrderInfo orderInfo = new OrderInfo();

        Date now = new Date();

        orderInfo.setOrderId(orderId);
        orderInfo.setUserId(userId);
        orderInfo.setTotalPrice(totalPrice);
        orderInfo.setPay(false);
        orderInfo.setReceipt(false);
        orderInfo.setStatus(true);
        orderInfo.setCreateTime(now);
        orderInfo.setOrderType("海运");
        orderInfoMapper.insertOrder(orderInfo);
    }

    @Override
    public void insertGoods(List<GoodsCart> goodsCart,String orderId){
        List<OrderGoods> orderGoods = new ArrayList<OrderGoods>();
        for (GoodsCart cart : goodsCart){
            OrderGoods order = new OrderGoods();
            if (cart.isChecked()){
                order.setOrderId(orderId);
                order.setProductId(cart.getProductId());
                order.setQuantity(cart.getQuantity());
                order.setProductName(cart.getProductName());
                order.setProductPrice(cart.getProductPrice());
                order.setMainImage(cart.getMainImage());
                order.setStatus(true);
                orderGoods.add(order);
            }

        }
        goodsMapper.insertGoods(orderGoods);
    }

    @Override
    public OrderInfo findByOrderId(String orderId){
        return orderInfoMapper.findByOrderId(orderId);
    }

    @Override
    public List<OrderInfo> findByUserId(String userId){
        return orderInfoMapper.findByUserId(userId);
    }

    @Override
    public List<OrderGoods> findGoodsByOrderId(String orderId){
        return goodsMapper.findByOrderId(orderId);
    }

    @Override
    public void updateByOrderId(OrderInfo orderInfo){
        orderInfoMapper.updateByOrderId(orderInfo);
    }

    @Override
    public List<OrderInfo> findOrderLimit(Map map) {
        return orderInfoMapper.findOrderLimit(map);
    }

    @Override
    public int selectCount(String orderId) {
        return orderInfoMapper.selectCount(orderId);
    }

}
