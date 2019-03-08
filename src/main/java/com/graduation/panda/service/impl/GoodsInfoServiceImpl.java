package com.graduation.panda.service.impl;

import com.graduation.panda.dao.GoodsInfoMapper;
import com.graduation.panda.model.GoodsInfo;
import com.graduation.panda.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsInfoServiceImpl implements GoodsInfoService{
    @Autowired
    GoodsInfoMapper goodsInfoMapper;

    @Override
    public List<GoodsInfo> findByKeyword(String keyword){
        return goodsInfoMapper.findByKeyword(keyword);
    }
}
