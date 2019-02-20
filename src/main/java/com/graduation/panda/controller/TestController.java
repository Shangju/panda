package com.graduation.panda.controller;

import com.graduation.panda.service.TestService;
import com.graduation.panda.service.impl.TestServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping(value="/findAll")
    @ResponseBody
    public Object findAll() {
        return testService.findAll();
    }

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }
}
