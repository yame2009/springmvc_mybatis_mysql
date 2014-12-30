package com.hb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 使用springmvc 的action拦截器功能，实现权限验证，另外还可以实现比如日志记录、请求处理时间分析等。
 * http://www.cnblogs.com/liukemng/p/3751338.html
 * 
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {
    
	/**
	 * http://localhost:8089/smm/account/login.html
	 * @return
	 */
    @RequestMapping(value="/login", method = {RequestMethod.GET})
    public String login(){
        
        return "test/login";
    }
    
}