package com.graduation.panda.dao;

import com.graduation.panda.model.GoodsDetail;
import com.graduation.panda.model.GoodsInfo;
import com.graduation.panda.model.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GoodsInfoMapper {
    /**
     * 根据关键词keyword查找商品列表
     * @param keyword
     * @return
     */
    List<GoodsInfo> findByKeyword(String keyword);

    List<GoodsInfo> findByKeywordPriceAsc(String keyword);

    List<GoodsInfo> findByKeywordPriceDesc(String keyword);

    //分页
    int findByKeywordCount(String keyword);

    List<GoodsInfo> findByKeywordLimit(Map map);

    List<GoodsInfo> findByKeywordPriceAscLimit(Map map);

    List<GoodsInfo> findByKeywordPriceDescLimit(Map map);



    /**
     * 管理员查询商品列表
     * @return
     */
    List<GoodsInfo> findGoodsLimit(Map map);

    /**
     * 查询商品总数量
     * @return
     */
    int selectCount(@Param(value = "goodsId") String goodsId);

    /**
     * 删除商品接口
     * @param goodsId
     */
    void deleteByGoodsId(String goodsId);

    /**
     * 编辑商品
     */
    void updateByPrimaryKey(GoodsInfo goodsInfo);

    //商品详情
    GoodsInfo findByGoodsId(String goodsId);

    //添加商品
    void insertGoods(GoodsInfo goodsInfo);
}
