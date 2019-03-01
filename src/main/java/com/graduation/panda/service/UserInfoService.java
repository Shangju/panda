package com.graduation.panda.service;

import com.graduation.panda.model.UserInfo;

public interface UserInfoService {
    /**
     * 添加用户详细信息
     * @param userInfo
     * @return
     */
    void insert(UserInfo userInfo);

    /**
     * 根据id查找该顾客
     * @param customerId
     * @return
     */
    UserInfo findById(String customerId);

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    void updateByPrimaryKey(UserInfo userInfo);

}
