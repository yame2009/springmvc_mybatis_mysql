package com.hb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hb.exception.BusinessException;
import com.hb.exception.SystemException;
import com.hb.web.auth.AuthPassport;


@Controller
public class WelcomeController {

	/**
	 * jump into the index page
	 * 
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value={"/index.html","/hello.html"})
	public String indexPage() {
		return "index";
	}
	
	@AuthPassport
	@RequestMapping(value={"/index","/hello"})
	public ModelAndView index(){
	    
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.addObject("message", "Hello World!");  
	    modelAndView.setViewName("index");  
	    return modelAndView;
	}
	
	/**
	 * 其中value="/detail/{id}",中的{id}为占位符表示可以映射请求为/detail/xxxx 的URL如：/detail/123等。
	 * 方法的参数@PathVariable(value="id") Integer id 用于将URL中占位符所对应变量映射到参数id上，@PathVariable(value="id") 中value的值要和占位符/{id}大括号中的值一致。
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/detail/{id}", method = {RequestMethod.GET})
	public ModelAndView getDetail(@PathVariable(value="id") Integer id){
	    
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.addObject("id", id);  
	    modelAndView.setViewName("detail");  
	    return modelAndView;
	}
	
	/**
	 * pring MVC还支持正则表达式方式的映射配置,URL正则表达式映射
	 * GET:请求  http://localhost:8089/test/helloworld/Hanmeimei-18
	 * 
	 * @param name
	 * @param age
	 * @return
	 */
	@RequestMapping(value="/helloworld/{name:\\w+}-{age:\\d+}", method = {RequestMethod.GET})
	public ModelAndView regUrlTest(@PathVariable(value="name") String name, @PathVariable(value="age") Integer age){
	    
	    ModelAndView modelAndView = new ModelAndView();   
	    modelAndView.addObject("name", name); 
	    modelAndView.addObject("age", age); 
	    modelAndView.setViewName("show");  
	    return modelAndView;
	}

	/**
	 * get the json Exception
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/josonException.html") 
	public @ResponseBody Map<String, Object> getjson() throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("content", "123");
			map.put("result", true);
			map.put("account", 1);
			throw new Exception();
		} catch (Exception e) {
			throw new BusinessException("this is the detail of ajax exception information");
		}
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/SystemException.html")
	public void TestSystemException() {
		throw new SystemException("this is system error ");
	}

	/**
	 * @throws com.jason.exception.BusinessException
	 * 
	 */
	@RequestMapping(value = "/BusinessException.html")
	public void TestBusinessException() throws BusinessException {
		throw new BusinessException("this is Business error ");
	}
	
	
}
