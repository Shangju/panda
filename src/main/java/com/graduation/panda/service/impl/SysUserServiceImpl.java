package com.graduation.panda.service.impl;

import com.graduation.panda.dao.SysUserMapper;
import com.graduation.panda.model.SysUser;
import com.graduation.panda.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl implements SysUserService{
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser findByName(String adminName) {
        return sysUserMapper.findByAdminName(adminName);
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
    public SysUser findById(String id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(SysUser record){
        return sysUserMapper.insert(record);
    }

    @Override
    public SysUser findByUserId(String userId){
        return sysUserMapper.findByUserId(userId);
    }

    @Override
    public void updateByPrimaryKey(SysUser record){
         sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysUser> findUserLimit(int pageNum) {
        return sysUserMapper.findUserLimit(pageNum);
    }

    @Override
    public int selectCount() {
        return sysUserMapper.selectCount();
    }
}
