package com.hb.web.auth;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 实现登录权限校验功能
 * 
 * SpringMVC学习系列（9） 之 实现注解式权限验证  http://www.cnblogs.com/liukemng/p/3751338.html
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter implements AuthPassport{
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
            
            //没有声明需要权限,或者声明不验证权限
                if(authPassport == null || authPassport.validate() == false)
                return true;
            else{                
                //在这里实现自己的权限验证逻辑
                if(validate())//如果验证成功返回true（这里直接写false来模拟验证失败的处理）
                {
                    return true;
                }
                else//如果验证失败
                {
                    //返回到登录界面
                    response.sendRedirect("test/login");
                    return false;
                }       
            }
        }
        else
            return true;   
     }

	@Override
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}
}