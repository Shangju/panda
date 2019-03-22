package com.graduation.panda.service;

import com.graduation.panda.model.SysUserToken;
import org.springframework.stereotype.Service;


public interface SysUserTokenService {
    /**
     * 根据用户userId查找token
     * @param userId
     * @return
     */
    SysUserToken findByUserId(String userId);

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
    SysUserToken createToken(String userId);

    /**
     * 新增一条token存入
     * @param record
     * @return
     */
    int save(SysUserToken record);

    /**
     * 更新token
     * @param record
     * @return
     */
    int update(SysUserToken record);
}
