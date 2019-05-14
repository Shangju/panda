package com.graduation.panda.controller;

import com.graduation.panda.model.GoodsDetail;
import com.graduation.panda.model.GoodsInfo;
import com.graduation.panda.model.OrderInfo;
import com.graduation.panda.service.GoodsDetailService;
import com.graduation.panda.service.GoodsInfoService;
import com.graduation.panda.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public HttpResult deleteGoods(@RequestBody GoodsInfo goodsInfo){
        goodsInfoService.updateByPrimaryKey(goodsInfo);
        return HttpResult.ok();
    }
}
