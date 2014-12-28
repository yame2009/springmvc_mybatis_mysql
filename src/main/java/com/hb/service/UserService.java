package com.hb.service;

import java.util.List;

import com.hb.entity.User;

/**
 *  @version ： 1.0
 *  @author  ： 苏若年              <a href="mailto:DennisIT@163.com">发送邮件</a>
 *  @since   ： 1.0        创建时间:    2013-4-9    上午11:15:50
 *  @function： TODO        
 */

public interface UserService {

    public int insert(User user);
    
    public int delete(User user);
    
    public int countAll();
    
    public List<User> selectAll();
    
    public int update(User user);
    
    public User detail(long id);
    
}