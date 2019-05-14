package com.graduation.panda.dao;

import com.graduation.panda.model.OrderInfo;
import com.graduation.panda.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderInfoMapper {
    void insertOrder(OrderInfo orderInfo);

    OrderInfo findByOrderId(String orderId);

    List<OrderInfo> findByUserId(String userId);

    /**
     * 插入订单的运输方式
     * @param orderInfo
     */
    void updateByOrderId(OrderInfo orderInfo);

    /**
     * 管理员查询用户列表
     * @return
     */
    List<OrderInfo> findOrderLimit(Map map);

    /**
     * 查询用户总数量
     * @return
     */
    int selectCount(@Param(value="orderId") String orderId);


    /**
     * 删除订单接口
     * @param orderId
     */
    void deleteByOrderId(String orderId);

    /**
     * 编辑订单
     * @param orderInfo
     */
    void updateByPrimaryKey(OrderInfo orderInfo);
}
