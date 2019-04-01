package com.graduation.panda.controller;

import com.graduation.panda.model.UserAddress;
import com.graduation.panda.service.UserAddressService;
import com.graduation.panda.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserAddressController {
    @Autowired
    UserAddressService addressService;

    @PostMapping("/updateAddress")
    @ResponseBody
    public HttpResult updateAddress(@RequestBody UserAddress address){
        UserAddress userAddress = addressService.findDefaultAddress(address.getUserId());
        if (userAddress != null){
            addressService.updateAddress(address);
        }else {
            addressService.insertAddress(address);
        }
        return HttpResult.ok();
    }
}
