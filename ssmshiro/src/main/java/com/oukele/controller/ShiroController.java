package com.oukele.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShiroController {

    @RequestMapping(path = "/admin",produces = "application/json;charset=utf-8")
    public String admin(){
        return " 你 好 吗 ？ 朋友";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login () {
        return "please input your password.";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/policy/view")
    public String policy () {
        return "dangdezhengcehao,nianianchidebao.";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/view",produces = "application/json;charset=utf-8")
    public String test () {
        return "这里人人都可以访问。不需要权限";
    }


}
