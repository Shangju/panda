package com.graduation.panda.service;

import com.graduation.panda.model.SysUser;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface SysUserService {
    SysUser findByName(String username);

    /**
     * 查找用户的菜单权限标识集合
     * @param
     * @return
     */
//    Set<String> findPermissions(String userName);

    SysUser findById(Long id);

    //注册插入
    int insert(SysUser record);
}
