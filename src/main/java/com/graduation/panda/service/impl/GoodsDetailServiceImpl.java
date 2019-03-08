package com.graduation.panda.service.impl;

import com.graduation.panda.dao.GoodsDetailMapper;
import com.graduation.panda.model.GoodsDetail;
import com.graduation.panda.service.GoodsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsDetailServiceImpl implements GoodsDetailService{
    @Autowired
    GoodsDetailMapper goodsDetail;

    @Override
    public GoodsDetail findByGoodsId(String goodsId){
        return goodsDetail.findByGoodsId(goodsId);
    }

}
