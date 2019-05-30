package com.graduation.panda.service.impl;

import com.graduation.panda.dao.UserAddressMapper;
import com.graduation.panda.model.UserAddress;
import com.graduation.panda.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService{
    @Autowired
    UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> findByUserId(String userId){
        return userAddressMapper.findByUserId(userId);
    }

    @Override
    public UserAddress findDefaultAddress(String userId){
        return userAddressMapper.findDefaultAddress(userId);
    }

    @Override
    public UserAddress findByAddressId(int addressId) {
        return userAddressMapper.findByAddressId(addressId);
    }

    @Override
    public void insertAddress(UserAddress address){
        Date now = new Date();
        address.setCreateTime(now);
//        address.setDefaultStatus(true);
        address.setDataFlag(true);
        userAddressMapper.insertAddress(address);
    }

    @Override
    public void updateAddress(UserAddress address){
        userAddressMapper.updateAddress(address);
    }

    @Override
    public void deleteAddress(int addressId) {
        userAddressMapper.deleteAddress(addressId);
    }
}
