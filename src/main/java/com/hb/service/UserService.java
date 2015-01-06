package com.hb.service;

import java.util.List;

import com.hb.entity.StudentInfoEntity;

/**
 *  @version ： 1.0
 *  @author  ： 苏若年              <a href="mailto:DennisIT@163.com">发送邮件</a>
 *  @since   ： 1.0        创建时间:    2013-4-9    上午11:15:50
 *  @function： TODO        
 */

public interface UserService {

    public int insert(StudentInfoEntity user);
    
    public int delete(StudentInfoEntity user);
    
    public int countAll();
    
    public List<StudentInfoEntity> selectAll();
    
    public int update(StudentInfoEntity user);
    
    public StudentInfoEntity detail(long id);
    
}