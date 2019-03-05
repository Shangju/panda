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
import com.graduation.panda.utils.makeNumberUtils;
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
import java.util.Enumeration;

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
//        HttpSession session = request.getSession();

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
//        System.out.println(session.getId());
//        System.out.println(session.getAttribute("key"));
//        session.setMaxInactiveInterval(30*60);
//        // 获取session中所有的键值
//        Enumeration<String> attrs = session.getAttributeNames();
//        // 遍历attrs中的
//        while(attrs.hasMoreElements()){
//        // 获取session键值
//            String name = attrs.nextElement().toString();
//            // 根据键值取session中的值
//            Object vakue = session.getAttribute(name);
//            // 打印结果
//            System.out.println("------" + name + ":" + vakue +"--------\n");
//        }

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 注册接口
     */

    @PostMapping(value = "/sys/register")
    public HttpResult register(@RequestBody SysUser sysUser,HttpServletRequest request){
        String username = sysUser.getName();
        String password = sysUser.getPassword();
        String mobile = sysUser.getMobile();
        String captcha = sysUser.getCaptcha();
        String salt = PasswordUtils.getSalt();

        HttpSession session = request.getSession();
        Object kaptcha = session.getAttribute("key");
//        System.out.println(session.getId());
//        System.out.println(session.getAttribute("key"));

        if(kaptcha == null){
            return HttpResult.error("验证码已失效");
        }
        if(!captcha.equals(kaptcha)){
            return HttpResult.error("验证码不正确");
        }

        //密码加密
        String xPassword = PasswordUtils.encrypte(password,salt);
        sysUser.setPassword(xPassword);
        sysUser.setSalt(salt);
        String userId = makeNumberUtils.customerMake();
        sysUser.setUserId(userId);

        SysUser user = sysUserService.findByName(username);

        // 账号已经存在
        if (user != null) {
            return HttpResult.error("账号已经存在");
        }
        int user1 = sysUserService.insert(sysUser);
        String msg = "注册成功";
        return HttpResult.ok(msg);
    }


    /**
     * 登录接口
     */
    @PostMapping(value = "/sys/login")
    public HttpResult login(@RequestBody LoginBean loginBean,HttpServletRequest request) throws IOException {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        String captcha = loginBean.getCaptcha();

        // 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
        // Object kaptcha = ShiroUtils.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
        HttpSession session = request.getSession();
        Object kaptcha = session.getAttribute("key");
//        System.out.println(session.getId());
//        System.out.println(session.getAttribute("key"));

        if(kaptcha == null){
            return HttpResult.error("验证码已失效");
        }
        if(!captcha.equals(kaptcha)){
            return HttpResult.error("验证码不正确");
        }

        // 用户信息
        SysUser user = sysUserService.findByName(username);

        // 账号不存在、密码错误
        if (user == null) {
            return HttpResult.error("账号不存在");
        }

        if (!match(user, password)) {
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
        return user.getPassword().equals(PasswordUtils.encrypte(password, user.getSalt()));
    }

    /**
     * 登出接口
     *@GetMapping: @RequestMapping(method =RequestMethod.GET)
     */
    @GetMapping(value = "/sys/logout")
    public HttpResult logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("key");
//        ShiroUtils.logout();
        return HttpResult.ok();
    }
}
