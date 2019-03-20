package com.graduation.panda.controller;

import com.alipay.api.AlipayApiException;
import com.graduation.panda.model.AlipayBean;
import com.graduation.panda.service.PayService;
import com.graduation.panda.service.TestService;
import com.graduation.panda.service.impl.TestServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * 测试controller
 */
@Controller
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    private PayService payService;

    /**
     * 阿里支付
     * @param
     * @param
     * @return
     * @throws AlipayApiException
     */
    @PostMapping(value = "alipay")
    @ResponseBody
    public String alipay(@RequestBody HashMap map) throws AlipayApiException {
        String outTradeNo = (String)map.get("outTradeNo");
        String subject = (String)map.get("subject");
        String totalAmount = (String)map.get("totalAmount");
        String body = (String)map.get("body");
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(outTradeNo);
        alipayBean.setSubject(subject);
        alipayBean.setTotal_amount(totalAmount);
        alipayBean.setBody(body);
        System.out.println(payService.aliPay(alipayBean));
        return payService.aliPay(alipayBean);
    }

    @PostMapping(value="/findAll")
    @ResponseBody
    public Object findAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        //        System.out.println(session.getId());
        System.out.println(session.getAttribute("key"));
        System.out.println(session.getAttribute("userId"));
        System.out.println(session.getAttribute("orderId"));
        session.setMaxInactiveInterval(30*60);
        // 获取session中所有的键值
        Enumeration<String> attrs = session.getAttributeNames();
        // 遍历attrs中的
        while(attrs.hasMoreElements()){
        // 获取session键值
            String name = attrs.nextElement().toString();
            // 根据键值取session中的值
            Object vakue = session.getAttribute(name);
            // 打印结果
            System.out.println("------" + name + ":" + vakue +"--------\n");
        }
        return testService.findAll();

    }

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }

    @RequestMapping("/cookie")
    @ResponseBody
    public String cookie(@CookieValue(value = "token")String token){
        System.out.println(token);
        return token;
    }
}
