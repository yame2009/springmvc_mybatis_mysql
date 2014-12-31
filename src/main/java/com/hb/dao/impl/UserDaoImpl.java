package com.hb.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.hb.dao.UserDao;
import com.hb.entity.User;

/**
 *  @version ： 1.0
 *  @author  ： 苏若年              <a href="mailto:DennisIT@163.com">发送邮件</a>
 *  @since   ： 1.0        创建时间:    2013-4-9    上午11:15:50
 *  @function： TODO        
 */

@Repository
public class UserDaoImpl implements UserDao{

    public int countAll() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(User user) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int insert(User user) {
        // TODO Auto-generated method stub
        return 0;
    }

    public List<User> selectAll() {
        List<User> list = new ArrayList<User>();
        for(int i=0; i<5; i++){
            User user = new User();
            user.setId(i);
            user.setName(UUID.randomUUID().toString().substring(0,8));
            user.setPassword(UUID.randomUUID().toString().substring(0,15));
            user.setSex(i%2);
            user.setEmail(UUID.randomUUID().toString().substring(0,8)+"@163.com");
            list.add(user);
        }
        return list;
    }

    public int update(User user) {
        // TODO Auto-generated method stub
        return 0;
    }

    public User detail(long id) {
        // TODO Auto-generated method stub
        return null;
    }

}