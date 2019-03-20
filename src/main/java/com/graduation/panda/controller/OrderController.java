package com.graduation.panda.controller;

import com.graduation.panda.model.*;
import com.graduation.panda.service.MyOrderService;
import com.graduation.panda.service.OrderService;
import com.graduation.panda.service.SysUserService;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.utils.CookieUtils;
import com.graduation.panda.utils.MakeNumberUtils;
import com.graduation.panda.utils.StringUtils;
import com.graduation.panda.utils.http.HttpResult;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    SysUserTokenService sysUserTokenService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    MyOrderService myOrderService;

    /**
     * 提交订单接口
     * @param goodsCart
     * @return
     */
    @PostMapping("/addOrder")
    @ResponseBody
    public HttpResult addOrder(HttpServletRequest request, @RequestBody List<GoodsCart> goodsCart){
        //获取Cookie里面的token，如果为空，则继续执行
        String token = CookieUtils.getCookieValue(request,"token");
        if(StringUtils.isBlank(token)){
            return HttpResult.error();
        }
        SysUserToken sysUserToken = sysUserTokenService.findByToken(token);
        String orderId = "";
        boolean flag = true;
        while (flag) {
            orderId = MakeNumberUtils.orderMake();
            OrderInfo orderInfo = orderService.findByOrderId(orderId);
            if (orderInfo == null) {
                orderService.insertOrder(goodsCart, orderId,sysUserToken.getUserId());
                orderService.insertGoods(goodsCart, orderId);
                flag = false;
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("orderId",orderId);
        session.setAttribute("userId",sysUserToken.getUserId());
        return HttpResult.ok();
    }

    /**
     *获取刚提交的订单商品
     * @param request
     * @return
     */
    @PostMapping("/getOrderGoods")
    @ResponseBody
    public HttpResult getOrderGoods(HttpServletRequest request){
        //查询用户刚提交的订单
        HttpSession session = request.getSession();
        String orderId = (String)session.getAttribute("orderId");
        List<OrderGoods> orderGoods = orderService.findGoodsByOrderId(orderId);

        //查询该用户的所有商品列表
        //获取Cookie里面的token，如果为空，则返回错误代码
//        String token = CookieUtils.getCookieValue(request,"token");
//        if(StringUtils.isBlank(token)){
//            return HttpResult.error();
//        }
//        SysUserToken sysUserToken = sysUserTokenService.findByToken(token);
//        List<OrderInfo> orderInfo = orderService.findByUserId(sysUserToken.getUserId());
//        List<OrderGoods> orderGoods = new ArrayList<>();
//        for (OrderInfo order : orderInfo){
//            List<OrderGoods> goods = orderService.findGoodsByOrderId(order.getOrderId());
//            if (goods.size() > 0){
//                orderGoods.addAll(goods);
//            }
//        }
        return HttpResult.ok(orderGoods);
    }

    /**
     * 获取所有的订单及相应的订单商品
     * @param request
     * @return
     */
    @PostMapping("/getOrders")
    @ResponseBody
    public HttpResult getOrders(HttpServletRequest request){
        //获取Cookie里面的token，如果为空，则返回错误代码
        String token = CookieUtils.getCookieValue(request,"token");
        if(StringUtils.isBlank(token)){
            return HttpResult.error();
        }
        SysUserToken sysUserToken = sysUserTokenService.findByToken(token);
        List<MyOrders> myOrders = myOrderService.selectByUserId(sysUserToken.getUserId());
        return HttpResult.ok(myOrders);
    }

    /**
     * 获取订单详情，就是指定orderId的订单及相关商品
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/getSingleOrders")
    @ResponseBody
    public HttpResult getSingleOrders(@RequestBody HashMap map,HttpServletRequest request){
        Map<String,String> hashMap = new HashMap<>();
        String orderId = map.get("orderId").toString();
        if (orderId == null){
            return HttpResult.error();
        }
        hashMap.put("orderId",orderId);
        //获取Cookie里面的token，如果为空，则返回错误代码
        String token = CookieUtils.getCookieValue(request,"token");
        if(StringUtils.isBlank(token)){
            return HttpResult.error();
        }
        SysUserToken sysUserToken = sysUserTokenService.findByToken(token);
        hashMap.put("userId",sysUserToken.getUserId());
        MyOrders myOrders =myOrderService.selectSingleOrder(hashMap);
        return HttpResult.ok(myOrders);
    }
}
