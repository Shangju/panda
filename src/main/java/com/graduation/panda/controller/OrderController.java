package com.graduation.panda.controller;

import com.alipay.api.AlipayApiException;
import com.graduation.panda.model.*;
import com.graduation.panda.service.*;
import com.graduation.panda.utils.CookieUtils;
import com.graduation.panda.utils.MakeNumberUtils;
import com.graduation.panda.utils.StringUtils;
import com.graduation.panda.utils.http.HttpResult;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.models.auth.In;
import org.apache.catalina.User;
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


    @Autowired
    UserAddressService userAddressService;

    @Autowired
    PayService payService;

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
     * 提交订单运输方式接口
     * @param map
     * @return
     */
    @PostMapping("/addOrderType")
    @ResponseBody
    public HttpResult addOrderType(HttpServletRequest request, @RequestBody HashMap map){
        int price = Integer.parseInt(map.get("shipType").toString());
        String orderType = "";
        if (price == 50){
            orderType = "海运";
        }else {
            orderType = "空运";
        }
        HttpSession session = request.getSession();
        String orderId = (String)session.getAttribute("orderId");
        OrderInfo orderInfo = orderService.findByOrderId(orderId);
        orderInfo.setOrderType(orderType);
        orderInfo.setTotalPrice(orderInfo.getTotalPrice() + price);
        orderService.updateByOrderId(orderInfo);
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
        String userId = (String)session.getAttribute("userId");
        HashMap map = new HashMap();
        map.put("orderId",orderId);
        map.put("userId",userId);
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            System.out.println(e);
        }
        MyOrders myOrders = myOrderService.selectSingleOrder(map);

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
        return HttpResult.ok(myOrders);
    }

    /**
     *获取用户的收货地址
     * @param request
     * @return
     */
    @PostMapping("/getAddressList")
    @ResponseBody
    public HttpResult getAddressList(HttpServletRequest request){
        //查询用户的收货地址
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        List<UserAddress> user= userAddressService.findByUserId(userId);
        return HttpResult.ok(user);
    }

    /**
     * 创建支付订单
     * @param request
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/createOrderPay")
    @ResponseBody
    public HttpResult createOrderPay(HttpServletRequest request) throws AlipayApiException {
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        String orderId = (String)session.getAttribute("orderId");
        OrderInfo orderInfo = orderService.findByOrderId(orderId);
        List<OrderGoods> orderGoods = orderService.findGoodsByOrderId(orderId);
        StringBuilder subject = new StringBuilder("");
        for (OrderGoods goods : orderGoods){
            subject.append(goods.getProductName());
        }
        String totalPrice = String.valueOf(orderInfo.getTotalPrice());
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(orderId);
        alipayBean.setSubject(subject.toString());
        alipayBean.setTotal_amount(totalPrice);
        alipayBean.setBody(subject.toString());

        String msg = payService.aliPay(alipayBean);
        return HttpResult.ok(msg);
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
        Map<String,Object> hashMap = new HashMap<>();
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


    /**
     * 获取所有订单
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/findOrderLimit")
    @ResponseBody
    public HttpResult findOrderLimit(@RequestBody HashMap map,HttpServletRequest request){
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String orderId = map.get("orderNum").toString();
//        System.out.println(orderNum);
        Map<String,Object> params = new HashMap<String,Object>();
        if(orderId.equals("")){
            orderId = "";
        }
        pageNum = (pageNum - 1) * 10 ;
        params.put("pageNum",pageNum);
        params.put("orderId",orderId);
        List<OrderInfo> orderInfos = orderService.findOrderLimit(params);
        int totalSize = orderService.selectCount(orderId);
        return HttpResult.ok(totalSize,orderInfos);
    }

    /**
     * 管理用户---删除订单接口
     * @param
     * @param
     * @return
     */
    @PostMapping("/deleteOrder")
    @ResponseBody
    public HttpResult deleteGoods(@RequestBody HashMap map){
        String orderId = map.get("orderId").toString();
        orderService.deleteByOrderId(orderId);
        return HttpResult.ok();
    }

    /**
     * 管理用户---编辑订单接口
     * @param
     * @param
     * @return
     */
    @PostMapping("/updateOrder")
    @ResponseBody
    public HttpResult deleteGoods(@RequestBody OrderInfo orderInfo){
        orderService.updateByPrimaryKey(orderInfo);
        return HttpResult.ok();
    }

    /**
     * 管理用户---编辑订单接口
     * @param
     * @param
     * @return
     */
    @PostMapping("/payMoney")
    @ResponseBody
    public HttpResult payMoney(@RequestBody OrderInfo orderInfo){
        orderService.updateByPrimaryKey(orderInfo);
        return HttpResult.ok();
    }

    /**
     * 更新订单状态
     * @param
     * @param
     * @return
     */
    @PostMapping("/updatePay")
    @ResponseBody
    public HttpResult updatePay(@RequestBody HashMap map){
        String orderId = map.get("orderId").toString();
        int addressId = Integer.parseInt(map.get("addressId").toString());
        OrderInfo orderInfo = orderService.findByOrderId(orderId);
        UserAddress address = userAddressService.findByAddressId(addressId);
        String userAddress = address.getCityName()+address.getAreaName()+
                address.getUserAddress();
        orderInfo.setPay(true);
        orderInfo.setUserAddress(userAddress);
        orderService.updateByPrimaryKey(orderInfo);
        return HttpResult.ok();
    }
}
