package com.graduation.panda.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.graduation.panda.model.LoginBean;
import com.graduation.panda.model.SysUser;
import com.graduation.panda.model.SysUserToken;
import com.graduation.panda.service.SysUserService;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.utils.PasswordUtils;
import com.graduation.panda.utils.ShiroUtils;
import com.graduation.panda.utils.http.HttpResult;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@RestController
public class SysLoginController {

    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;


    /**
     * 验证码接口
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到 session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 注册接口
     */

    @PostMapping(value = "/sys/register")
    public HttpResult register(@RequestBody SysUser sysUser){
        String username = sysUser.getName();
        String password = sysUser.getPassword();
        String salt = PasswordUtils.getSalt();
        //密码加密
        String xPassword = PasswordUtils.encrypte(password,salt);
        sysUser.setPassword(xPassword);
        sysUser.setSalt(salt);

        SysUser user = sysUserService.findByName(username);
        // 账号不存在、密码错误

        if (user != null) {
            return HttpResult.error("账号已经存在");
        }
        int user1 = sysUserService.insert(sysUser);
        String msg = "";
        return HttpResult.ok(msg);
    }


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
