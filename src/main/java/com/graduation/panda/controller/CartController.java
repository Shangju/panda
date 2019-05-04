package com.graduation.panda.controller;

import com.graduation.panda.model.GoodsCart;
import com.graduation.panda.model.SysUserToken;
import com.graduation.panda.service.CartService;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.utils.CookieUtils;
import com.graduation.panda.utils.JSONUtils;
import com.graduation.panda.utils.StringUtils;
import com.graduation.panda.utils.http.HttpResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class CartController {

    private static Logger logger = Logger.getLogger(CartController.class);

    @Autowired
    CartService cartService;

    @Autowired
    SysUserTokenService sysUserTokenService;


    /**
     * 加入购物车接口
     * @param request
     * @param response
     * @param cartInfo
     */
    @PostMapping("/cart/addCart")
    @ResponseBody
    public HttpResult addCart(HttpServletRequest request, HttpServletResponse response, @RequestBody GoodsCart cartInfo){
        //判断用户是否登录，登录了就将信息存入数据库，没有登录就在Cookie中操作购物车
        SysUserToken userToken = (SysUserToken)request.getAttribute("user");
        if(userToken != null){
            SysUserToken user = sysUserTokenService.findByToken(userToken.getToken());
            cartInfo.setUserId(user.getUserId());
            cartService.insertSingleCart(cartInfo);
            return HttpResult.ok();
        }else {
            //获取购物车列表
            List<GoodsCart> cartList = getCartListFromCookie(request);
            //判断购物车中是否有该商品
            boolean flag = false;
            for (GoodsCart cart : cartList) {
                if (cart.getProductId().equals(cartInfo.getProductId())) {
                    flag = true;
                    //存在该商品,数量相加
                    cart.setQuantity(cart.getQuantity() + cartInfo.getQuantity());
                    //跳出循环
                    break;
                }
            }
            if (!flag) {
                cartList.add(cartInfo);
            }
            for (int i = 0; i < cartList.size(); i++) {
                logger.info("商品id为：" + cartList.get(i).getProductId() + "   数量为：" + cartList.get(i).getQuantity());
            }
            //购物车信息写入cookie
            CookieUtils.setCookie(request, response, "cart1",
                    JSONUtils.objectToJson(cartList), 7 * 24 * 60 * 60, true);
            return HttpResult.ok();
        }
    }

    /**
     * 获取购物车的信息
     * @param request
     * @return
     */
    @PostMapping("/cart/getCartInfo")
    @ResponseBody
    public HttpResult getCartInfo(HttpServletRequest request,HttpServletResponse response){
        //判断用户是否登录
        SysUserToken userToken = (SysUserToken)request.getAttribute("user");
        if(userToken != null){
            SysUserToken user = sysUserTokenService.findByToken(userToken.getToken());
            List<GoodsCart> cartList1 = getCartListFromCookie(request);
            if(cartList1.size()>0){
                //将userId存入
                for(GoodsCart cart :cartList1){
                    cart.setUserId(user.getUserId());
                }
                cartService.insertCart(cartList1,user.getUserId());
                //删除Cookie
                CookieUtils.deleteCookie(request, response, "cart1");
            }
            List<GoodsCart> cartList = cartService.selectByUserId(user.getUserId());
            if (cartList.size() > 0){
                return HttpResult.ok(cartList);
            }else {
                return HttpResult.error("购物车没有物品");
            }
        }else {
            //未登录则获取Cookie中的购物车信息
            List<GoodsCart> list = getCartListFromCookie(request);
            //list的长度大于0，则显示购物车信息
            if (list.size() > 0){
                return HttpResult.ok(list);
            }else {
                //list的长度为空，则还没有物品加入购物车
                return HttpResult.error("购物车没有物品");
            }
        }
    }

    /**
     * 购物车操作更新
     * @return request
     */
    @PostMapping("/cart/actionCart")
    @ResponseBody
    public HttpResult actionCart(HttpServletRequest request,HttpServletResponse response,@RequestBody HashMap map){
        String productId = map.get("productId").toString();
        String type = map.get("type").toString();

        //判断用户是否登录
        SysUserToken userToken = (SysUserToken)request.getAttribute("user");
        if(userToken != null){
            SysUserToken user = sysUserTokenService.findByToken(userToken.getToken());
            cartService.updateInfo(productId,type,user.getUserId());
            List<GoodsCart> cartList = cartService.selectByUserId(user.getUserId());
            if (cartList.size() > 0){
                return HttpResult.ok(cartList);
            }else {
                return HttpResult.error("购物车没有物品");
            }
        }else {
            List<GoodsCart> cartList = getCartListFromCookie(request);
            for (GoodsCart cart : cartList) {
                if (cart.getProductId().equals(productId)) {
                    if (type.equals("minus_count")) {
                        if (cart.getQuantity() == 1) {
                            break;
                        } else {
                            cart.setQuantity(cart.getQuantity() - 1);
                        }
                    } else if (type.equals("plus_count")) {
                        if (cart.getQuantity() == cart.getProductStock()) {
                            break;
                        } else {
                            cart.setQuantity(cart.getQuantity() + 1);
                        }
                    } else {
                        cartList.remove(cart);
                    }
                    break;
                }
            }
            if (cartList.size() < 1) {
                CookieUtils.deleteCookie(request, response, "cart1");
                return HttpResult.error("没有了物品");
            } else {
                //购物车信息写入cookie
                CookieUtils.setCookie(request, response, "cart1",
                        JSONUtils.objectToJson(cartList), 7 * 24 * 60 * 60, true);
                return HttpResult.ok(cartList);
            }
        }
    }

    /**
     * 删除选中的商品
     * @param request
     * @param response
     * @param cartInfos
     * @return
     */
    @PostMapping("/cart/deleteSelectProduct")
    @ResponseBody
    public HttpResult deleteSelectProduct(HttpServletRequest request,HttpServletResponse response,@RequestBody List<GoodsCart> cartInfos){
        SysUserToken userToken = (SysUserToken)request.getAttribute("user");
        if(userToken != null){
            SysUserToken user = sysUserTokenService.findByToken(userToken.getToken());
            cartService.deleteSelect(cartInfos);
            List<GoodsCart> cartList = cartService.selectByUserId(user.getUserId());
            if (cartList.size() > 0){
                return HttpResult.ok(cartList);
            }else {
                return HttpResult.error("购物车没有物品");
            }
        }else {
            if (cartInfos.size() < 1) {
                return HttpResult.error("没有商品");
            }
            for (int i = 0; i < cartInfos.size(); i++) {
                if (cartInfos.get(i).isChecked()) {
                    cartInfos.remove(i);
                    i--;
                }
            }
            if (cartInfos.size() < 1) {
                CookieUtils.deleteCookie(request, response, "cart1");
                return HttpResult.error("没有了物品");
            } else {
                //购物车信息写入cookie
                CookieUtils.setCookie(request, response, "cart1",
                        JSONUtils.objectToJson(cartInfos), 7 * 24 * 60 * 60, true);
                return HttpResult.ok(cartInfos);
            }
        }
    }

    /**
     * 从cookie中获取购物车列表
     * @param request
     * @return
     */
    public List<GoodsCart> getCartListFromCookie(HttpServletRequest request){
        String string = CookieUtils.getCookieValue(request, "cart1", true);
        //判断是否为空
        if(StringUtils.isBlank(string)){
            //空的话也不能返回null
            return new ArrayList<>();
        }
        //转为商品列表
        List<GoodsCart> list = JSONUtils.jsonToList(string, GoodsCart.class);
        return list;
    }
}
