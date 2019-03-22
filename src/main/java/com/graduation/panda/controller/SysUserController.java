package com.graduation.panda.controller;

import com.graduation.panda.model.SysUser;
import com.graduation.panda.model.SysUserToken;
import com.graduation.panda.service.SysUserService;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.utils.CookieUtils;
import com.graduation.panda.utils.PasswordUtils;
import com.graduation.panda.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

@Controller
public class SysUserController {
    @Autowired
    SysUserTokenService tokenService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 赋给主页登录信息
     * @param request
     * @return
     */
    @PostMapping("/userInfo")
    @ResponseBody
    public HttpResult getUserInfo(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies.length < 1){
            return HttpResult.error("没有Cookie");
        }
        for(Cookie c :cookies){
            if(c.getName() != null && c.getName().equals("token")){
                SysUserToken userToken = tokenService.findByToken(c.getValue());
                Date now = new Date();
                if(userToken == null || now.after(userToken.getExpireTime())){
                    CookieUtils.deleteCookie(request,response,"token");
                    return HttpResult.error("token已经过期，请重新登录");
                }
                return HttpResult.ok(userToken);
            }
        }
        return HttpResult.error("token不存在");
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
        SysUser user = sysUserService.findByUserId(userToken.getUserId());
        return HttpResult.ok(user);
    }

    /**
     * 编辑用户信息接口
     * @param sysUser
     * @return
     */
    @PostMapping(value = "/updateUserInfo")
    @ResponseBody
    public HttpResult insertInfo(@RequestBody SysUser sysUser){
        String userId = sysUser.getUserId();

        SysUser user = sysUserService.findByUserId(userId);
        sysUserService.updateByPrimaryKey(sysUser);
        SysUser userNew = sysUserService.findByUserId(userId);
        return HttpResult.ok(userNew);
    }

    /**
     * 更新密码接口
     * @param map
     * @param token
     * @return
     */
    @PostMapping("/updatePassword")
    @ResponseBody
    public HttpResult updatePassword(@RequestBody HashMap map, @CookieValue(value = "token")String token){
        String passwordOld = map.get("passwordOld").toString();
        String passwordNew = map.get("passwordNew").toString();
        String passwordConfirm = map.get("passwordConfirm").toString();
        if (!passwordNew.equals(passwordConfirm)){
            return HttpResult.error("两次密码不一致，请从新输入！");
        }
        SysUserToken userToken = tokenService.findByToken(token);
        SysUser user = sysUserService.findByUserId(userToken.getUserId());
        String password = user.getUserPassword();
        String salt = user.getUserSalt();
        String xpassword = PasswordUtils.encrypte(passwordOld,salt);
        if (!password.equals(xpassword)){
            return HttpResult.error("密码输入错误！");
        }
        user.setUserPassword(PasswordUtils.encrypte(passwordNew,salt));
        sysUserService.updateByPrimaryKey(user);
        return HttpResult.ok("密码修改成功，请重新登录。");
    }
}
