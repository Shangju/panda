package com.graduation.panda.controller;

import com.graduation.panda.model.GoodsInfo;
import com.graduation.panda.model.SysUser;
import com.graduation.panda.model.SysUserToken;
import com.graduation.panda.model.UserAddress;
import com.graduation.panda.service.SysUserService;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.service.UserAddressService;
import com.graduation.panda.utils.CookieUtils;
import com.graduation.panda.utils.MakeNumberUtils;
import com.graduation.panda.utils.PasswordUtils;
import com.graduation.panda.utils.http.HttpResult;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SysUserController {
    @Autowired
    SysUserTokenService tokenService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 赋给主页登录信息
     * @param request
     * @return
     */
    @PostMapping("/userInfo")
    @ResponseBody
    public HttpResult userInfo(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
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
        UserAddress userAddress = userAddressService.findDefaultAddress(userToken.getUserId());
        if(userAddress != null){
            String address = userAddress.getCityName()+" "+userAddress.getAreaName()+" "+userAddress.getUserAddress();
            user.setUserAddress(address);
        }
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

    /**
     * 管理用户---分页查询接口
     * @param
     * @param
     * @return
     */
    @PostMapping("/findUserLimit")
    @ResponseBody
    public HttpResult findUserLimit(@RequestBody HashMap map){
        int pageNum = Integer.parseInt(map.get("pageNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String adminName = map.get("adminName").toString();

        Map<String,Object> params = new HashMap<String,Object>();
        if(adminName.equals("")){
            adminName = "";
        }
        pageNum = (pageNum - 1) * 10 ;
        params.put("pageNum",pageNum);
        params.put("adminName",adminName);
        List<SysUser> sysUsers = sysUserService.findUserLimit(params);
        int totalSize = sysUserService.selectCount(adminName);
        return HttpResult.ok(totalSize,sysUsers);
    }



    /**
     * 管理用户---新增编辑接口
     * @param
     * @param
     * @return
     */
    @PostMapping("/editSubmit")
    @ResponseBody
    public HttpResult editSubmit(@RequestBody SysUser sysUser){
        if(sysUser.getUserId().equals("0")){
            //生成唯一用户userId
            boolean flag = true;
            while (flag){
                String userId = MakeNumberUtils.customerMake();
                SysUser user = sysUserService.findByUserId(userId);
                //如果没有查到信息，就存入该userId
                if (user == null){
                    sysUser.setUserId(userId);
                    flag = false;
                }
            }
            String salt = PasswordUtils.getSalt();
            //默认密码加密
            String xPassword = PasswordUtils.encrypte("111111",salt);
            //存入用户信息
            sysUser.setUserPassword(xPassword);
            sysUser.setUserSalt(salt);

            sysUserService.insert(sysUser);
        }else {
            sysUserService.updateByPrimaryKey(sysUser);
        }
        return HttpResult.ok();
    }

    /**
     * 管理用户---删除用户接口
     * @param
     * @param
     * @return
     */
    @PostMapping("/deleteUser")
    @ResponseBody
    public HttpResult deleteUser(@RequestBody HashMap map){
        String userId = map.get("userId").toString();
        sysUserService.deleteByPrimaryKey(userId);
        return HttpResult.ok();
    }

    /**
     * 管理员登录
     * @param
     * @param
     * @return
     */
    @PostMapping("/adminLogin")
    @ResponseBody
    public HttpResult adminLogin(@RequestBody HashMap map,HttpServletRequest request){
        String adminName = map.get("adminName").toString();
        String password = map.get("password").toString();
        String captcha = map.get("captcha").toString();

        String userKind = map.get("userKind").toString();

        HttpSession session = request.getSession();
        Object kaptcha = session.getAttribute("key");
        System.out.println(kaptcha);
        System.out.println(session.getId());
//        System.out.println(session.getAttribute("key"));

//        if(kaptcha == null){
//            return HttpResult.error("验证码已失效");
//        }
//        if(!captcha.equals(kaptcha)){
//            return HttpResult.error("验证码不正确");
//        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userKind",userKind);
        params.put("adminName",adminName);
        SysUser sysUser = sysUserService.findByUserKind(params);
        // 账号不存在、密码错误
        if (sysUser == null) {
            return HttpResult.error("账号不存在");
        }
        if (!match(sysUser, password)) {
            return HttpResult.error("密码不正确");
        }
        return HttpResult.ok();
    }

    /**
     * 获取用户信息接口
     * @param
     * @return
     */
    @PostMapping("/getAdminInfo")
    @ResponseBody
    public HttpResult getAdminInfo(@RequestBody Map map){
        String userName = map.get("userName").toString();
        String userKind = map.get("userKind").toString();
        Map<String,Object> params = new HashMap<>();
        params.put("adminName",userName);
        params.put("userKind",userKind);
        SysUser sysUser = sysUserService.findByUserKind(params);
        return HttpResult.ok(sysUser);
    }

    /**
     * 管理员更新密码接口
     * @param map
     * @return
     */
    @PostMapping("/adminPassword")
    @ResponseBody
    public HttpResult adminPassword(@RequestBody HashMap map){
        String userName = map.get("userName").toString();
        String userKind = map.get("userKind").toString();
        Map<String,Object> params = new HashMap<>();
        params.put("adminName",userName);
        params.put("userKind",userKind);
        SysUser user = sysUserService.findByUserKind(params);

        String passwordOld = map.get("passwordOld").toString();
        String passwordNew = map.get("passwordNew").toString();
        String passwordConfirm = map.get("passwordConfirm").toString();
        if (!passwordNew.equals(passwordConfirm)){
            return HttpResult.error("两次密码不一致，请从新输入！");
        }
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

    /**
     * 验证用户密码
     * @param user
     * @param password
     * @return
     */
    public boolean match(SysUser user, String password) {
        return user.getUserPassword().equals(PasswordUtils.encrypte(password, user.getUserSalt()));
    }
}
