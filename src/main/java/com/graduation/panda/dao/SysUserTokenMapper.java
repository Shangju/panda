package com.graduation.panda.dao;

import com.graduation.panda.model.SysUserToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserTokenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserToken record);

    int insertSelective(SysUserToken record);

    SysUserToken selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserToken record);

    int updateByPrimaryKey(SysUserToken record);

    SysUserToken findByUserId(@Param(value="userId") Long userId);

    SysUserToken findByToken(@Param(value="token") String token);
}
