package com.graduation.panda.controller;

import com.graduation.panda.model.UserAddress;
import com.graduation.panda.service.UserAddressService;
import com.graduation.panda.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
            address.setDefaultStatus(true);
            addressService.insertAddress(address);
        }
        return HttpResult.ok();
    }

    @PostMapping("/insertAddress")
    @ResponseBody
    public HttpResult insertAddress(@RequestBody UserAddress address){
        address.setDefaultStatus(false);
        addressService.insertAddress(address);
        List<UserAddress> userAddresses = addressService.findByUserId(address.getUserId());
        return HttpResult.ok(userAddresses);
    }

    @PostMapping("/deleteAddress")
    @ResponseBody
    public HttpResult deleteAddress(@RequestBody UserAddress address){
        addressService.deleteAddress(address.getAddressId());
        List<UserAddress> userAddresses = addressService.findByUserId(address.getUserId());
        return HttpResult.ok(userAddresses);
    }
}
