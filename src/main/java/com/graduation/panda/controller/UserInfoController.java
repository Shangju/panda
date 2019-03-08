package com.graduation.panda.controller;

import com.graduation.panda.model.LoginBean;
import com.graduation.panda.model.SysUser;
import com.graduation.panda.model.SysUserToken;
import com.graduation.panda.model.UserInfo;
import com.graduation.panda.service.SysUserService;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.service.UserInfoService;
import com.graduation.panda.utils.PasswordUtils;
import com.graduation.panda.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    SysUserTokenService tokenService;

    @Autowired
    SysUserService sysUserService;

    /**
     * 编辑用户信息接口
     * @param userInfo
     * @return
     */
    @PostMapping(value = "/updateUserInfo")
    @ResponseBody
    public HttpResult insertInfo(@RequestBody UserInfo userInfo){
        String customerId = userInfo.getCustomerId();

        UserInfo user = userInfoService.findById(customerId);
        if(user == null){
            userInfoService.insert(userInfo);
        }else {
            userInfoService.updateByPrimaryKey(userInfo);
        }
        UserInfo userNew = userInfoService.findById(customerId);
        return HttpResult.ok(userNew);
    }

    /**
     * 获取用户信息接口
     * @param token
     * @return
     */
    @PostMapping("/getUserInfo")
    @ResponseBody
    public HttpResult getUserInfo(@CookieValue(value = "token")String token){
        SysUserToken userToken = tokenService.findByToken(token);
        UserInfo user = userInfoService.findById(userToken.getUserId());
        if(user == null){
            SysUser sysUser = sysUserService.findByUserId(userToken.getUserId());
            return HttpResult.ok("未记录用户资料",sysUser);
        }else {
            return HttpResult.ok(user);
        }
    }

    /**
     * 更新密码接口
     * @param map
     * @param token
     * @return
     */
    @PostMapping("/updatePassword")
    @ResponseBody
    public HttpResult updatePassword(@RequestBody HashMap map,@CookieValue(value = "token")String token){
        String passwordOld = map.get("passwordOld").toString();
        String passwordNew = map.get("passwordNew").toString();
        String passwordConfirm = map.get("passwordConfirm").toString();
        if (!passwordNew.equals(passwordConfirm)){
            return HttpResult.error("两次密码不一致，请从新输入！");
        }
        SysUserToken userToken = tokenService.findByToken(token);
        SysUser user = sysUserService.findByUserId(userToken.getUserId());
        String password = user.getPassword();
        String salt = user.getSalt();
        String xpassword = PasswordUtils.encrypte(passwordOld,salt);
        if (!password.equals(xpassword)){
            return HttpResult.error("密码输入错误！");
        }
        user.setPassword(PasswordUtils.encrypte(passwordNew,salt));
        sysUserService.updateByPrimaryKey(user);
        return HttpResult.ok("密码修改成功，请重新登录。");
    }
}
