package com.hb.dao.redis;

import java.util.List;

import com.hb.models.UserModel;

public interface UserModelDao {
	 /**
     * 添加对象
     */
    public boolean add(final UserModel member) ;

    /**
     * 添加集合
     */
    public boolean add(final List<UserModel> list);
    
    /**
     * 删除对象 ,依赖key
     */
    public void delete(String key) ;
  
    /**
     * 删除集合 ,依赖key集合
     */
    public void delete(List<String> keys);
    
    /**
     * 修改对象 
     */
    public boolean update(final UserModel member) ;
    
    /**
     * 根据key获取对象
     */
    public UserModel get(final String keyId); 
}
