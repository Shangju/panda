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

    /**
     * 通过userId查找用户的默认收货地址
     * @param userId
     * @return
     */
    UserAddress findDefaultAddress(String userId);

    /**
     * 插入用户的收货地址
     * @param userAddress
     */
    void insertAddress(UserAddress userAddress);

    /**
     * 更新用户的默认收货地址
     * @param userAddress
     */
    void updateAddress(UserAddress userAddress);
}
