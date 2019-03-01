package com.graduation.panda.controller;

import com.graduation.panda.model.UserInfo;
import com.graduation.panda.service.UserInfoService;
import com.graduation.panda.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @PostMapping(value = "/sys/userInfo")
    public HttpResult insertInfo(@RequestBody UserInfo userInfo){
        String customerId = userInfo.getCustomerId();
//        String customerName = userInfo.getCustomerName();
//        String customerNumber = userInfo.getCustomerNumber();
//        String customerPhone = userInfo.getCustomerPhone();
//        String customerAddress = userInfo.getCustomerAddress();

        UserInfo user = userInfoService.findById(customerId);
        if(user == null){
            userInfoService.insert(userInfo);
        }else {
            userInfoService.updateByPrimaryKey(userInfo);
        }
        return HttpResult.ok("资料更新成功");
    }
}
