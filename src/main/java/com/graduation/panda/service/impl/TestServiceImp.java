package com.graduation.panda.service.impl;

import com.graduation.panda.dao.SysUserMapper;
import com.graduation.panda.model.SysUser;
import com.graduation.panda.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImp implements TestService{
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.selectAll();
    }
}
