package com.hb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hb.entity.StudentInfoEntity;
import com.hb.service.UserService;

/**
 *  UserController.java   
 *  @version ： 1.1
 *  @author  ：  huangbing   <a href="mailto:hb0504511129@126.com">发送邮件</a>
 *  @since   ： 1.0        创建时间:    2013-4-13  下午06:21:34
 */
@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	  
    private Map<String,StudentInfoEntity> users = new HashMap<String, StudentInfoEntity>();
    
  //@RequestMapping("/user.html")是说明这个方法处理user.do这个请求
    @RequestMapping(value="/insert.html")
    public String insert(ModelMap modelMap, StudentInfoEntity user){
        return "";
    }
    
    @RequestMapping(value="/list.html")
    public String list(ModelMap modelMap){
        modelMap.put("users", this.userService.selectAll());
        System.out.println("控制器将执行结果转发给试图/注意转发试图结果同dispatcher-servlet.xml对比");
        return "user/userlist";
    }
    
    public UserController(){
        users.put("suruonian", new StudentInfoEntity("suruonian","suruonian","suruonian@demo.com"));
        users.put("linyunxi", new StudentInfoEntity("linyunxi","linyunxi","linyunxi@163.com"));
        users.put("dennisit", new StudentInfoEntity("dennisit","dennisit","dennisit@163.com"));
        users.put("moshaobai", new StudentInfoEntity("moshaobai","bing_he","1325103287@qq.com"));
    }
    
    /**
     * 
     *
     * Description:    构建REST风格 /user/users的GET请求时才执行该方法的操作RequestMethod.GET表示
     *                 只处理GET请求
     * @param model    用于上下文参数传递
     * @return        视图页面 user/list  结合user-servlet.xml中配置的视图模型匹配视图页面
     *                 实例中方法返回表示/WEB-INF/jsp/user/list.jsp页面
     *
     */
    @RequestMapping(value="/users",method=RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("users", users);
        return "user/list";
    }
    

    /**
     * 
     *
     * Description:    链接到页面时是GET请求,执行该方法 <a href="add">添加</a>
     * @return        返回给用户添加页面
     *
     */
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("user",new StudentInfoEntity());    //开启ModelDriven 跳转到增加页面时使用该Model
        return "user/add";
    }
    
    /**
     * 
     * Description: 添加操作 请求/user/add  form表单提交时使用的post请求调用该方法
     * @param user    添加的User对象
     * @param br    验证绑定
     * @return        视图页面
     *                         添加成功 请求重定向redirect:/user/users 表示执行操作结束后请求定向为/user/users
     *                         添加失败 页面转到/WEB-INF/jsp/add.jsp 这里有验证绑定,将在视图页面展示验证错误信息
     *
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@Validated StudentInfoEntity user,BindingResult br){
        //需要说明的是BindingResult形参一定要跟@Validated修饰的形参后面写验证
        if(br.hasErrors()){        //如果有错误,直接跳转到添加视图
            return "user/add";            //服务端跳转 该跳转会自动在前面增加 forward
        }
        users.put(user.getName(), user);
        return "redirect:/user/users";    //客户端跳转 使用 redirect
    }
    
    
    /**
     * 
     *
     * Description:     查看操作 根据用户名查看  REST风格: /detail/查看的用户名
     * @param username    带查看的用户名@PathVariable 修饰username 表示用请求路径中的username作为 形参
     * @param model        携带数据的Model
     * @return            视图页面 /WEB-INF/jsp/user/detail.jsp页面
     *
     */
    @RequestMapping(value="/{username}",method=RequestMethod.GET)
    public String detail(@PathVariable String username, Model model){
        System.out.println("获取到传入的参数值为:" + username);
        model.addAttribute("user", users.get(username));
        return "user/detail";
    }
    
    /**
     * 
     *
     * Description:        预更新操作根据用户名查询用户信息 然后数据交给携带体 展示到视图    REST风格: /更新的用户的用户名/update
     * @param username    @PathVariable修饰 表示形参同URL中的请求参数
     * @param model        携带数据的Model
     * @return            视图页面/WEB-INF/jsp/user/update页面
     *
     */
    @RequestMapping(value="/{username}/update",method=RequestMethod.GET)
    public String update(@PathVariable String username, Model model){
        System.out.println("获取到传入的参数值为:" + username);
        model.addAttribute(users.get(username));
        return "user/update";
    }
    
    /**
     * 
     *
     * Description:        真正更新的操作    REST风格：    /更新的用户的用户名/update
     * @param username    带更新的用户的用户名    
     * @param user        带更新的用户的信息对象    @Validated修饰表示信息需要被验证
     * @param br        验证信息绑定对象 必须紧跟在待验证的信息形参后面
     * @return            视图页面    
     *                              更新成功  请求重定向 /user/users
     *                              更新失败      转到/WEB-INF/jsp/user/update.jsp页面
     *
     */
    @RequestMapping(value="/{username}/update",method=RequestMethod.POST)
    public String update(@PathVariable String username, @Validated StudentInfoEntity user,BindingResult br){
        if(br.hasErrors()){        //如果有错误,直接跳转到修改视图
            return "user/update";
        }
        users.put(username, user);
        return "redirect:/user/users";
    }
    
    /**
     * 
     *
     * Description:        删除操作 REST风格:/删除的用户名/delete
     * @param username    删除的用户名        类似表主键,可以标记到整个记录信息
     * @return            视图页面    
     *                               请求重定向到 /user/users
     *
     */
    @RequestMapping(value="/{username}/delete",method=RequestMethod.GET)
    public String delete(@PathVariable String username){
        System.out.println("获取到传入的参数值为:" + username);
        users.remove(username);
        return "redirect:/user/users";
    }
}
