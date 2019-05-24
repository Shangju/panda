package com.graduation.panda.controller;

import com.google.code.kaptcha.Producer;
import com.graduation.panda.model.SysUser;
import com.graduation.panda.model.SysUserToken;
import com.graduation.panda.service.SysUserService;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.utils.CookieUtils;
import com.graduation.panda.utils.PasswordUtils;
import com.graduation.panda.utils.StringUtils;
import com.graduation.panda.utils.http.HttpResult;
import com.graduation.panda.utils.MakeNumberUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class SysLoginController {

    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    public static Logger logger = Logger.getLogger(SysLoginController.class);


    /**
     * 验证码接口
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
//        System.out.println(text);
        logger.info("生成的验证码为："+text);
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到 session
//        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        HttpSession session = request.getSession();
        session.setAttribute("key",text);
        System.out.println(session.getId());
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 注册接口
     */

    @PostMapping(value = "/sys/register")
    public HttpResult register(@RequestBody SysUser sysUser,HttpServletRequest request){
        String adminName = sysUser.getAdminName();
        String userPassword = sysUser.getUserPassword();
        String captcha = sysUser.getCaptcha();
        String salt = PasswordUtils.getSalt();

        //获取存在session的验证码
        HttpSession session = request.getSession();
        Object kaptcha = session.getAttribute("key");

        if(kaptcha == null){
            return HttpResult.error("验证码已失效");
        }
        if(!captcha.equals(kaptcha)){
            return HttpResult.error("验证码不正确");
        }

        //判断用户名是否存在
        SysUser sysUser1 = sysUserService.findByName(adminName);

        // 账号已经存在
        if (sysUser1 != null) {
            return HttpResult.error("账号已经存在");
        }

        //密码加密
        String xPassword = PasswordUtils.encrypte(userPassword,salt);
        //存入用户信息
        sysUser.setUserPassword(xPassword);
        sysUser.setUserSalt(salt);

        //生成唯一用户userId
        boolean flag = true;
        while (flag){
            String userId = MakeNumberUtils.customerMake();
            SysUser user = sysUserService.findByUserId(userId);
            //如果没有查到信息，就存入该userId
            if (user == null){
                sysUser.setUserId(userId);
                sysUser.setUserKind("普通会员");
                flag = false;
            }
        }
        int user1 = sysUserService.insert(sysUser);
        if (user1 == 1){
            logger.info("————————注册成功————————");
            String msg = "注册成功";
            return HttpResult.ok(msg);
        }else {
            return HttpResult.error();
        }

    }


    /**
     * 登录接口
     */
    @PostMapping(value = "/sys/login")
    public HttpResult login(@RequestBody SysUser sysUser,HttpServletRequest request) throws IOException {
        String adminName = sysUser.getAdminName();
        String userPassword = sysUser.getUserPassword();
        String captcha = sysUser.getCaptcha();

        // 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
        // Object kaptcha = ShiroUtils.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
        HttpSession session = request.getSession();
        Object kaptcha = session.getAttribute("key");
        System.out.println(session.getId());
//        System.out.println(session.getAttribute("key"));

        if(kaptcha == null){
            return HttpResult.error("验证码已失效");
        }
        if(!captcha.equals(kaptcha)){
            return HttpResult.error("验证码不正确");
        }

        // 用户信息
        SysUser user = sysUserService.findByName(adminName);

        // 账号不存在、密码错误
        if (user == null) {
            return HttpResult.error("账号不存在");
        }

        if (!match(user, userPassword)) {
            return HttpResult.error("密码不正确");
        }

        // 生成token，并保存到数据库
        SysUserToken data = sysUserTokenService.createToken(user.getUserId());
        return HttpResult.ok(data);
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

    /**
     * 登出接口
     *@GetMapping: @RequestMapping(method =RequestMethod.GET)
     */
    @GetMapping(value = "/sys/logout")
    public HttpResult logout(HttpServletRequest request,HttpServletResponse response) {
        //获取Cookie里面的token，如果为空，则继续执行
        String token = CookieUtils.getCookieValue(request,"token");
        if(StringUtils.isBlank(token)){
            return HttpResult.ok();
        }else {
            CookieUtils.deleteCookie(request,response,"token");
        }
        return HttpResult.ok();
    }

}
