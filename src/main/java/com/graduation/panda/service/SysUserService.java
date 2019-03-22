package com.graduation.panda.service;

import com.graduation.panda.model.SysUser;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface SysUserService {

    /**
     * 通过用户名查找信息，判断用户名是否存在
     * @param adminName
     * @return
     */
    SysUser findByName(String adminName);

    /**
     * 查找用户的菜单权限标识集合
     * @param
     * @return
     */
//    Set<String> findPermissions(String userName);

    SysUser findById(String id);

    //注册插入
    int insert(SysUser record);

    /**
     * 根据userId查找用户信息
     * @param userId
     * @return
     */
    SysUser findByUserId(String userId);

    /**
     * 更新用户信息
     * @param record
     */
    void updateByPrimaryKey(SysUser record);

}
