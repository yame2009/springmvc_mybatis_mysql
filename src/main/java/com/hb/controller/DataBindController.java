package com.hb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hb.models.AccountModel;

/**
 * 页面说明地址：1.http://www.cnblogs.com/liukemng/p/3729071.html 
 * 
 *
 */
@Controller
@RequestMapping(value = "/databind")
@SessionAttributes(value = "sessionaccountmodel")
public class DataBindController {

	/**
	 * 接口访问地址：http://localhost:8089/smm/databind/parambind.html
	 * @return
	 */
    @RequestMapping(value="/parambind", method = {RequestMethod.GET})
    public ModelAndView paramBind(){
        
        ModelAndView modelAndView = new ModelAndView();  
        modelAndView.setViewName("test/parambind");  
        return modelAndView;
    }
    
    @RequestMapping(value="/parambind", method = {RequestMethod.POST})
    public ModelAndView paramBind(HttpServletRequest request, @RequestParam("urlParam") String urlParam, @RequestParam("formParam") String formParam, @RequestParam("formFile") MultipartFile formFile){
        
        //如果不用注解自动绑定，我们还可以像下面一样手动获取数据
         String urlParam1 = ServletRequestUtils.getStringParameter(request, "urlParam", null);
        String formParam1 = ServletRequestUtils.getStringParameter(request, "formParam", null);
        MultipartFile formFile1 = ((MultipartHttpServletRequest) request).getFile("formFile"); 
        
        ModelAndView modelAndView = new ModelAndView();  
        modelAndView.addObject("urlParam", urlParam);  
        modelAndView.addObject("formParam", formParam);  
        modelAndView.addObject("formFileName", formFile.getOriginalFilename());  
        
        modelAndView.addObject("urlParam1", urlParam1);  
        modelAndView.addObject("formParam1", formParam1);  
        modelAndView.addObject("formFileName1", formFile1.getOriginalFilename());  
        modelAndView.setViewName("test/parambindresult");  
        return modelAndView;
    }
    
    /**
     *  接口访问地址：  http://localhost:8089/smm/databind/modelautobind.html
     * 
     * 上面我们演示了如何把数据绑定到单个变量，但在实际应用中我们通常需要获取的是model对象，别担心，
     * 我们不需要把数据绑定到一个个变量然后在对model赋值，
     * 只需要把model加入相应的action参数（这里不需要指定绑定数据的注解）
     * Spring MVC会自动进行数据转换并绑定到model对象上，
     * 一切就是这么简单。测试如下：
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/modelautobind", method = {RequestMethod.GET})
    public String modelAutoBind(HttpServletRequest request, Model model){
        
        model.addAttribute("accountmodel", new AccountModel());
        return "test/modelautobind";
    }

//    @RequestMapping(value="/modelautobind", method = {RequestMethod.POST})
//    public String modelAutoBind(HttpServletRequest request, Model model, AccountModel accountModel){
//        
//        model.addAttribute("accountmodel", accountModel);
//        return "test/modelautobindresult";
//    }
    
    @RequestMapping(value="/modelautobind", method = {RequestMethod.POST})
    public String modelAutoBind(HttpServletRequest request, @ModelAttribute("accountmodel") AccountModel accountModel){
        
        return "test/modelautobindresult";
    }
    
    /**
     * 功能点说明网址： http://www.cnblogs.com/liukemng/p/3736948.html
     * 在系列（4）中我们介绍了如何用@RequestParam来绑定数据，下面我们来看一下其它几个数据绑定注解的使用方法。
		1.@PathVariable 用来绑定URL模板变量值，这个我们已经在系列（3）中介绍了使用方法，这里不在赘述。
		2.@CookieValue 用来绑定Cookie中的数据。下面我们用获取Cookie中的sessionId做测试：
		在DataBindController添加cookiebind action，代码如下：
		
		注：@CookieValue 与@RequestParam 一样也有3个参数，其含义与的@RequestParam 参数含义相同。
		
		action访问地址：http://localhost:8089/test/databind/cookiebind.html
     * @param request
     * @param model
     * @param jsessionId
     * @return
     */
  //@CookieValue Test
    @RequestMapping(value="/cookiebind", method = {RequestMethod.GET})
    public String cookieBind(HttpServletRequest request, Model model, @CookieValue(value="JSESSIONID", defaultValue="") String jsessionId){
        
        model.addAttribute("jsessionId", jsessionId);
        return "test/cookiebindresult";
    }
    
    /**
     * 功能点说明网址： http://www.cnblogs.com/liukemng/p/3736948.html
     * @RequestHeader 用来绑定请求头中的数据，我们用@RequestHeader获取User-Agent 来做演示：
			在DataBindController添加requestheaderbind action，代码如下：
     *  注：@RequestHeader 与@RequestParam 一样也有3个参数，其含义与的@RequestParam 参数含义相同。
     * 
     *   action访问地址：http://localhost:8089/test/databind/requestheaderbind.html
     *   
     * @param request
     * @param model
     * @param userAgent
     * @return
     */
  //@RequestHeader Test
    @RequestMapping(value="/requestheaderbind", method = {RequestMethod.GET})
    public String requestHeaderBind(HttpServletRequest request, Model model, @RequestHeader(value="User-Agent", defaultValue="") String userAgent){
        
        model.addAttribute("userAgent", userAgent);
        return "test/requestheaderbindresult";
    }
    
    /***
     *  * 5. http://www.cnblogs.com/liukemng/p/3736948.html
	 * Model中的数据作用域是Request级别的，也就是说在一个Request请求中是获取不到其它Request请求的Model的数据的。
	 * 但我们可以用@SessionAttributes 把数据存储到session中，来保持多次请求间数据，
	 * 这样就可以来实现比如分步骤提交表单等需求。下面我们来看如何分2步把数据绑定到AccountModel中：
     * @return
     */
  //@SessionAttributes Test
    @ModelAttribute("sessionaccountmodel")
    public AccountModel initAccountModel(){
        
        return new AccountModel();
    }

    @RequestMapping(value="/usernamebind", method = {RequestMethod.GET})
    public String userNameBind( Model model, AccountModel accountModel){
        
        model.addAttribute("sessionaccountmodel", new AccountModel());
        return "test/usernamebind";
    }

    @RequestMapping(value="/usernamebind", method = {RequestMethod.POST})
    public String userNameBindPost( @ModelAttribute("sessionaccountmodel") AccountModel accountModel){
        
        //重定向到密码绑定测试
        return "redirect:test/passwordbind";
    }

    @RequestMapping(value="/passwordbind", method = {RequestMethod.GET})
    public String passwordBind(@ModelAttribute("sessionaccountmodel") AccountModel accountModel){
        
        return "test/passwordbind";
    }

    @RequestMapping(value="/passwordbind", method = {RequestMethod.POST})
    public String passwordBindPost(@ModelAttribute("sessionaccountmodel") AccountModel accountModel, SessionStatus status){
        
        //销毁@SessionAttributes存储的对象
        status.setComplete();
        //显示绑定结果
        return "test/sessionmodelbindresult";
    }
    
    /**
     * http://www.cnblogs.com/liukemng/p/3736948.html
     * 6.@RequestBody 调用合适的MessageConvert来把非application/x-www-form-urlencoded请求中的内容转换为指定的对象它通常与@ResponseBody合用，@ResponseBody与.@RequestBody刚好相反，他把指定的对象转换为合适的内容（请求头为Accept:application/json 则返回json数据）并返回。这里我们用一个ajax请求做演示：
	 *	由于Spring默认解析json用的是Jackson，所以我们这里要把jackson-core-asl-1.9.13.jar和jackson-mapper-asl-1.9.13.jar两个包添加到我们项目。
	 *		修改AccountModel让其继承Serializable接口，并添加一个空的构造函数（为了Jackson做转换）。
	 *		在DataBindController添加requestBodyBindaction，代码如下：
     * @param model
     * @return
     */
  //@RequestBody Test
    @RequestMapping(value="/requestbodybind", method = {RequestMethod.GET})
    public String requestBodyBind(Model model){
        
        model.addAttribute("accountmodel", new AccountModel());
        return "test/requestbodybind";
    }

    @RequestMapping(value="/requestbodybind", method = {RequestMethod.POST})
    public @ResponseBody AccountModel requestBodyBind(@RequestBody AccountModel accountModel){
                
        return accountModel;
    }
    
    
    
    
        
}