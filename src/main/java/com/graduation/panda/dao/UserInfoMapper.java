package com.graduation.panda.dao;

import com.graduation.panda.model.SysUser;
import com.graduation.panda.model.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper {
    void insert(UserInfo userInfo);

    UserInfo findById(String customerId);

    void updateByPrimaryKey(UserInfo userInfo);
}
