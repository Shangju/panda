package com.graduation.panda.service.impl;

import com.graduation.panda.dao.UserAddressMapper;
import com.graduation.panda.model.UserAddress;
import com.graduation.panda.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService{
    @Autowired
    UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> findByUserId(String userId){
        return userAddressMapper.findByUserId(userId);
    }
}
