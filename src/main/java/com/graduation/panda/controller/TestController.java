package com.graduation.panda.controller;

import com.graduation.panda.service.TestService;
import com.graduation.panda.service.impl.TestServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TestController {

    @Autowired
    TestService testService;

    @PostMapping(value="/findAll")
    @ResponseBody
    public Object findAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        return testService.findAll();
    }

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }
}
