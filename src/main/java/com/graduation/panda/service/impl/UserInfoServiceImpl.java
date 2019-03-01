package com.graduation.panda.service.impl;

import com.graduation.panda.dao.UserInfoMapper;
import com.graduation.panda.model.UserInfo;
import com.graduation.panda.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public void insert(UserInfo userInfo){
        userInfoMapper.insert(userInfo);
    }

    @Override
    public UserInfo findById(String customerId){
        return userInfoMapper.findById(customerId);
    }

    @Override
    public void updateByPrimaryKey(UserInfo userInfo){
        userInfoMapper.updateByPrimaryKey(userInfo);
    }

}
