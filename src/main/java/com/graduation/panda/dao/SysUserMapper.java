package com.graduation.panda.dao;

import com.graduation.panda.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);


    SysUser selectByPrimaryKey(Long id);


    List<SysUser> selectAll();

    int updateByPrimaryKey(SysUser record);

    SysUser findByName(@Param(value="name") String name);


}