package com.graduation.panda.dao;

import com.graduation.panda.model.SysUser;
import com.graduation.panda.model.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper {
    /**
     * 插入用户信息
     * @param userInfo
     */
    void insert(UserInfo userInfo);

    /**
     * 通过用户Id进行查询用户的信息
     * @param customerId
     * @return
     */
    UserInfo findById(String customerId);

    /**
     * 更新用户信息
     * @param userInfo
     */
    void updateByPrimaryKey(UserInfo userInfo);
}
