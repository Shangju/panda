package com.graduation.panda.service.impl;

import com.graduation.panda.dao.SysUserTokenMapper;
import com.graduation.panda.model.SysUserToken;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysUserTokenServiceImpl implements SysUserTokenService{
    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;
    // 7天后过期
    private final static int EXPIRE = 60 * 60 * 24;

    @Override
    public SysUserToken findByUserId(String userId) {
        return sysUserTokenMapper.findByUserId(userId);
    }

    @Override
    public SysUserToken findByToken(String token) {
        return sysUserTokenMapper.findByToken(token);
    }

    @Override
    public int save(SysUserToken record) {
        return sysUserTokenMapper.insertSelective(record);
    }

    @Override
    public int update(SysUserToken record) {
        return sysUserTokenMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SysUserToken createToken(String userId) {
        // 生成一个token
        String token = "";
        while (true) {
            token = TokenGenerator.generateToken();
            //判断生成的token是否已经存在
            SysUserToken userToken = findByToken(token);
            if (userToken == null) {
                break;
            }
        }
        // 当前时间
        Date now = new Date();
        // 过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000 * 7);
        // 判断用户是否生成过token，没有则新建，有则更新
        SysUserToken sysUserToken = findByUserId(userId);
        if(sysUserToken == null){
            sysUserToken = new SysUserToken();
            sysUserToken.setUserId(userId);
            sysUserToken.setToken(token);
            sysUserToken.setCreateTime(now);
            sysUserToken.setLastUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);
            // 用户第一次生成token并保存，这里选择保存到数据库，也可以放到Redis或Session之类可存储的地方
            save(sysUserToken);
        } else{
            sysUserToken.setUserId(userId);
            sysUserToken.setToken(token);
            sysUserToken.setLastUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);
            // 如果用户token已经生成，则更新token和过期时间
            update(sysUserToken);
        }
        return sysUserToken;
    }
}
