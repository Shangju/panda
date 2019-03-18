package com.graduation.panda.service.impl;

import com.graduation.panda.dao.GoodsCartMapper;
import com.graduation.panda.model.GoodsCart;
import com.graduation.panda.service.CartService;
import com.graduation.panda.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    GoodsCartMapper cartMapper;

    @Override
    public void insertCart(List<GoodsCart> goodsCarts,String userId){
        List<GoodsCart> cartList = cartMapper.selectByUserId(userId);
        //如果用户本身购物车有内容，就进行合并
        if (cartList.size() > 0) {
            for (GoodsCart cart : goodsCarts) {
                for (GoodsCart cart1 : cartList) {
                    //如果两条记录同一种商品，就将将数量相加，并删除数据库的那一条记录
                    if (cart.getProductId().equals(cart1.getProductId())) {
                        cart.setQuantity(cart.getQuantity() + cart1.getQuantity());
                        cartMapper.deleteByKey(cart1);
                    }
                }
            }
        }
        cartMapper.insertCart(goodsCarts);

    }

    @Override
    public List<GoodsCart> selectByUserId(String userId){
        return cartMapper.selectByUserId(userId);
    }

    @Override
    public void insertSingleCart(GoodsCart goodsCart){
        List<GoodsCart> cartList = cartMapper.selectByUserId(goodsCart.getUserId());
        if (cartList.size() > 0) {
            for (GoodsCart cart : cartList) {
                if (cart.getProductId().equals(goodsCart.getProductId())) {
                    goodsCart.setQuantity(cart.getQuantity() + goodsCart.getQuantity());
                    cartMapper.updateByKey(goodsCart);
                    break;
                }
            }
        }else {
            cartMapper.insertSingleCart(goodsCart);
        }
    }

    @Override
    public void updateInfo(String productId,String type,String userId){
        List<GoodsCart> cartList = cartMapper.selectByUserId(userId);
        for(GoodsCart cart : cartList){
            if(cart.getProductId().equals(productId)){
                if (type.equals("minus_count")) {
                    if (cart.getQuantity() == 1) {
                        break;
                    } else {
                        cart.setQuantity(cart.getQuantity() - 1);
                        cartMapper.updateByKey(cart);
                    }
                } else if (type.equals("plus_count")) {
                    if (cart.getQuantity() == cart.getProductStock()) {
                        break;
                    } else {
                        cart.setQuantity(cart.getQuantity() + 1);
                        cartMapper.updateByKey(cart);
                    }
                } else {
                    cartMapper.deleteByKey(cart);
                }
                break;
            }
        }
    }

    @Override
    public void deleteSelect(List<GoodsCart> goodsCart){
        for (GoodsCart cart : goodsCart){
            if (cart.isChecked()){
                cartMapper.deleteByKey(cart);
            }
        }
    }
}
