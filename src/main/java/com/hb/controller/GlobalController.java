package com.hb.controller;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContext;

import com.hb.models.FormatModel;



@Controller
@RequestMapping(value = "/global")
public class GlobalController {
	
    /**
     * 跳转到首页视图层
     *
     * @author <a href='mailto:dennisit@163.com'>Cn.pudp(En.dennisit)</a> Copy Right since 2013-10-9 下午12:54:54
     *                
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value={"global","global.html"},method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView windexPage(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("global");
    }
    
    /**
     * 访问地址为：localhost:8080/test/global/test/langType=zh
     * @param request
     * @param response
     * @param model
     * @param langType
     * @return
     */
    @RequestMapping(value="/test", method = {RequestMethod.GET})
    public String test(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value="langType", defaultValue="zh") String langType){
        if(!model.containsAttribute("contentModel")){
            
        	//方法2的国际化实现，基于session。
           if(langType.equals("zh")){
                Locale locale = new Locale("zh", "CN"); 
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale); 
            }
            else if(langType.equals("en")){
                Locale locale = new Locale("en", "US"); 
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            }
            else 
            {
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());
            }
                
        	//方法3的国际化实现，基于Cookie。
//            if(langType.equals("zh")){
//                Locale locale = new Locale("zh", "CN"); 
//                //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
//                (new CookieLocaleResolver()).setLocale (request, response, locale);
//            }
//            else if(langType.equals("en")){
//                Locale locale = new Locale("en", "US"); 
//                //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
//                (new CookieLocaleResolver()).setLocale (request, response, locale);
//            }
//            else 
//                //request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());
//                (new CookieLocaleResolver()).setLocale (request, response, LocaleContextHolder.getLocale());
            
            //从后台代码获取国际化信息
            RequestContext requestContext = new RequestContext(request);
            model.addAttribute("money", requestContext.getMessage("money"));
            model.addAttribute("date", requestContext.getMessage("date"));

            
            FormatModel formatModel=new FormatModel();

            formatModel.setMoney(12345.678);
            formatModel.setDate(new Date());
            
            model.addAttribute("contentModel", formatModel);
        }
        return "globaltest";
    }
    
    
}