package com.graduation.panda.service.impl;

import com.graduation.panda.dao.SysUserMapper;
import com.graduation.panda.model.SysUser;
import com.graduation.panda.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SysUserServiceImpl implements SysUserService{
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser findByName(String name) {
        return sysUserMapper.findByName(name);
    }

//    @Override
//    public Set<String> findPermissions(String userName) {
//        Set<String> perms = new HashSet<>();
//        List<SysMenu> sysMenus = sysMenuService.findByUser(userName);
//        for(SysMenu sysMenu:sysMenus) {
//            perms.add(sysMenu.getPerms());
//        }
//        return perms;
//    }

    @Override
    public SysUser findById(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(SysUser record){
        return sysUserMapper.insert(record);
    }
}
