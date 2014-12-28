package com.hb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.dao.UserDao;
import com.hb.entity.User;
import com.hb.service.UserService;

/**
 *  @version ： 1.0
 *  @author  ： 苏若年              <a href="mailto:DennisIT@163.com">发送邮件</a>
 *  @since   ： 1.0        创建时间:    2013-4-9    上午11:15:50
 *  @function： TODO        
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired(required = true)
    private UserDao userDao;

    @Override
    public int countAll() {
        return this.userDao.countAll();
    }

    @Override
    public int delete(User user) {
        return this.userDao.delete(user);
    }

    @Override
    public int insert(User user) {
        return this.userDao.insert(user);
    }

    @Override
    public List<User> selectAll() {
        return this.userDao.selectAll();
    }

    @Override
    public int update(User user) {
        return this.userDao.update(user);
    }

    @Override
    public User detail(long id) {
        return this.userDao.detail(id);
    }

}
