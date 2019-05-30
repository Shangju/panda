package com.graduation.panda.controller;

import com.graduation.panda.model.GoodsDetail;
import com.graduation.panda.model.GoodsInfo;
import com.graduation.panda.model.OrderInfo;
import com.graduation.panda.model.SysUser;
import com.graduation.panda.service.GoodsDetailService;
import com.graduation.panda.service.GoodsInfoService;
import com.graduation.panda.utils.DateUtils;
import com.graduation.panda.utils.MakeNumberUtils;
import com.graduation.panda.utils.StringUtils;
import com.graduation.panda.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class GoodsController {
    @Autowired
    GoodsInfoService goodsInfoService;

    @Autowired
    GoodsDetailService goodsDetailService;

    /**
     * 显示商品列表
     * @param
     * @return
     */
    @PostMapping("/goodsList")
    @ResponseBody
    public HttpResult goodsList(@RequestBody HashMap map){
        if(map.get("keyword").toString() == null){
            return HttpResult.ok();
        }
        String keyword = map.get("keyword").toString();
        if (keyword.equals("")){
            return HttpResult.error("没有搜索关键字");
        }else {
            String orderBy = map.get("orderBy").toString();
            List<GoodsInfo> goods = new ArrayList<>();
            if (orderBy.equals("name asc")){
                goods = goodsInfoService.findByKeyword(keyword);
            }else if (orderBy.equals("price asc")){
                goods = goodsInfoService.findByKeywordPriceAsc(keyword);
            }else {
                goods = goodsInfoService.findByKeywordPriceDesc(keyword);
            }
            return HttpResult.ok(goods);
        }
    }

    /**
     * 获取商品详细信息
     * @param map
     * @return
     */
    @PostMapping("/loadDetail")
    @ResponseBody
    public HttpResult loadDetail(@RequestBody HashMap map){
        String goodsId = map.get("goodsId").toString();
        if(goodsId != null){
            GoodsInfo detail = goodsInfoService.findByGoodsId(goodsId);
            if(detail == null){
                return HttpResult.error("没有这个商品");
            }
            return HttpResult.ok(detail);
        }else {
            return HttpResult.error("信息错误");
        }
    }

    /**
     * 获取页数接口
     * @param
     * @return
     */
    @PostMapping("/getAllPage")
    @ResponseBody
    public HttpResult getAllPage(@RequestBody HashMap map){
        if(map.get("keyword").toString() == null){
            return HttpResult.ok();
        }
        String keyword = map.get("keyword").toString();
        if (keyword.equals("")){
            return HttpResult.error("没有搜索关键字");
        }else {
            int all = goodsInfoService.findByKeywordCount(keyword);
            all = all / 10 + 1;
            return HttpResult.ok(all);
        }
    }

    /**
     * 获取分页接口
     * @param map
     * @return
     */
    @PostMapping("/getLimitPage")
    @ResponseBody
    public HttpResult getLimitPage(@RequestBody HashMap map){
        String keyword = map.get("keyword").toString();
        if (keyword.equals("")){
            return HttpResult.error("没有搜索关键字");
        }else {
            String orderBy = map.get("orderBy").toString();
            List<GoodsInfo> goods = new ArrayList<>();
            if (orderBy.equals("name asc")){
                goods = goodsInfoService.findByKeywordLimit(map);
            }else if (orderBy.equals("price asc")){
                goods = goodsInfoService.findByKeywordPriceAscLimit(map);
            }else {
                goods = goodsInfoService.findByKeywordPriceDescLimit(map);
            }
            return HttpResult.ok(goods);
        }
    }



    /**
     * 获取商品资料
     * @param map
     * @return
     */
    @PostMapping("/findGoodsLimit")
    @ResponseBody
    public HttpResult findGoodsLimit(@RequestBody HashMap map){
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String goodsId = map.get("goodsId").toString();

        Map<String,Object> params = new HashMap<String,Object>();
        if(goodsId.equals("")){
            goodsId = "";
        }
        pageNum = (pageNum - 1) * 10 ;
        params.put("pageNum",pageNum);
        params.put("goodsId",goodsId);
        List<GoodsInfo> goodsInfos = goodsInfoService.findGoodsLimit(params);
        int totalSize = goodsInfoService.selectCount(goodsId);
        return HttpResult.ok(totalSize,goodsInfos);

    }

    /**
     * 管理用户---删除商品接口
     * @param
     * @param
     * @return
     */
    @PostMapping("/deleteGoods")
    @ResponseBody
    public HttpResult deleteGoods(@RequestBody HashMap map){
        String goodsId = map.get("goodsId").toString();
        goodsInfoService.deleteByGoodsId(goodsId);
        return HttpResult.ok();
    }

    /**
     * 管理用户---编辑商品接口
     * @param
     * @param
     * @return
     */
    @PostMapping("/updateGoods")
    @ResponseBody
    public HttpResult deleteGoods(HttpServletRequest request,@RequestBody GoodsInfo goodsInfo){
        if(goodsInfo.getGoodsId().equals("0")){
            //生成唯一用户userId
            boolean flag = true;
            while (flag){
                String goodsId = MakeNumberUtils.goodsMake();
                GoodsInfo goods = goodsInfoService.findByGoodsId(goodsId);
                if (goods == null){
                    goodsInfo.setGoodsId(goodsId);
                    flag = false;
                }
            }
            String image = StringUtils.savedPath;
            goodsInfo.setImage(image);
            goodsInfo.setSubImages("../../../static/img/details/detail1-1.jpg,../../../static/img/details/detail1-2.jpg,../../../static/img/details/detail1-3.jpg,../../../static/img/details/detail1-4.jpg");
            goodsInfo.setDetail("<p><img alt=\"miaoshu.jpg\" src=\"http://img.happymmall.com/9c5c74e6-6615-4aa0-b1fc-c17a1eff6027.jpg\" width=\"790\" height=\"444\"><br></p><p><img alt=\"miaoshu2.jpg\" src=\"http://img.happymmall.com/31dc1a94-f354-48b8-a170-1a1a6de8751b.jpg\" width=\"790\" height=\"1441\"><img alt=\"miaoshu3.jpg\" src=\"http://img.happymmall.com/7862594b-3063-4b52-b7d4-cea980c604e0.jpg\" width=\"790\" height=\"1442\"><img alt=\"miaoshu4.jpg\" src=\"http://img.happymmall.com/9a650563-dc85-44d6-b174-d6960cfb1d6a.jpg\" width=\"790\" height=\"1441\"><br></p>");
            goodsInfoService.insertGoods(goodsInfo);
        }else {
            String image1 = StringUtils.savedPath;
            goodsInfo.setImage(image1);
            goodsInfoService.updateByPrimaryKey(goodsInfo);
        }
        return HttpResult.ok();
    }
}
