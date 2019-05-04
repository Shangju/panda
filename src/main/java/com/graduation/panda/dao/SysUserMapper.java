package com.graduation.panda.dao;

import com.graduation.panda.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(String id);

    List<SysUser> selectAll();

    /**
     * 更新用户信息，包括资料与密码
     * @param record
     */
    void updateByPrimaryKey(SysUser record);

    /**
     * 通过登录名adminName查询用户信息
     * @param adminName
     * @return
     */
    SysUser findByAdminName(String adminName);

    /**
     * 通过userId查询用户信息
     * @param userId
     * @return
     */
    SysUser findByUserId(String userId);

    /**
     * 管理员查询用户列表
     * @return
     */
    List<SysUser> findUserLimit(int pageNum);

    /**
     * 查询用户总数量
     * @return
     */
    int selectCount();
}