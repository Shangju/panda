package com.graduation.panda.service;

import com.graduation.panda.model.SysUserToken;
import org.springframework.stereotype.Service;


public interface SysUserTokenService {
    /**
     * 根据用户id查找
     * @param userId
     * @return
     */
    SysUserToken findByUserId(Long userId);

    /**
     * 根据token查找
     * @param token
     * @return
     */
    SysUserToken findByToken(String token);

    /**
     * 生成token
     * @param userId
     * @return
     */
    SysUserToken createToken(long userId);

    int save(SysUserToken record);
}
