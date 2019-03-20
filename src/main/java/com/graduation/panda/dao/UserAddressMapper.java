package com.graduation.panda.dao;

import com.graduation.panda.model.UserAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressMapper {
    /**
     * 通过userId查询用户的收货地址
     * @param userId
     * @return
     */
    List<UserAddress> findByUserId(String userId);
}
