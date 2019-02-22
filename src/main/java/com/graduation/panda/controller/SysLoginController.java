package com.graduation.panda.controller;

import com.graduation.panda.model.LoginBean;
import com.graduation.panda.model.SysUser;
import com.graduation.panda.model.SysUserToken;
import com.graduation.panda.service.SysUserService;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.utils.PasswordUtils;
import com.graduation.panda.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SysLoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    /**
     * 登录接口
     */
    @PostMapping(value = "/sys/login")
    public HttpResult login(@RequestBody LoginBean loginBean) throws IOException {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();

        // 用户信息
        SysUser user = sysUserService.findByName(username);

        // 账号不存在、密码错误
        if (user == null) {
            return HttpResult.error("账号不存在");
        }

        if (!match(user, password)) {
            return HttpResult.error("密码不正确");
        }

        // 账号锁定
//        if (user.getStatus() == 0) {
//            return HttpResult.error("账号已被锁定,请联系管理员");
//        }

        // 生成token，并保存到数据库
        SysUserToken data = sysUserTokenService.createToken(user.getId());
        return HttpResult.ok(data);
    }

    /**
     * 验证用户密码
     * @param user
     * @param password
     * @return
     */
    public boolean match(SysUser user, String password) {
        return user.getPassword().equals(PasswordUtils.encrypte(password, user.getSalt()));
    }
}
