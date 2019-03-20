package com.graduation.panda.service;

import com.graduation.panda.model.UserAddress;

import java.util.List;

public interface UserAddressService {
    /**
     * 通过userId查询用户的收货地址
     * @param userId
     * @return
     */
    List<UserAddress> findByUserId(String userId);
}
