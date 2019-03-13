package com.graduation.panda.filter;

import com.graduation.panda.model.SysUser;
import com.graduation.panda.model.SysUserToken;
import com.graduation.panda.service.SysUserTokenService;
import com.graduation.panda.service.impl.SysUserTokenServiceImpl;
import com.graduation.panda.utils.CookieUtils;
import com.graduation.panda.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SysInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    SysUserTokenService sysUserTokenService;
    /**
     * 进入拦截器后首先进入的方法
     * 返回false则不再继续执行
     * 返回true则继续执行
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)throws Exception {
        //获取Cookie里面的token，如果为空，则继续执行
        String token = CookieUtils.getCookieValue(request,"token");
        if(StringUtils.isBlank(token)){
            return true;
        }
        //有token则将token存入request
//        SysUserToken user = sysUserTokenService.findByToken(token);
//        if(user == null){
//            return true;
//        }
        request.setAttribute("token", token);
        return  true;
    }
    /**
     * 生成视图时执行，可以用来处理异常，并记录在日志中
     */
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object arg2, Exception exception){
        //-----------------//
    }

    /** -
     * 生成视图之前执行，可以修改ModelAndView
     */
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object arg2, ModelAndView arg3)
            throws Exception{
        //----------------------------//
    }
}
