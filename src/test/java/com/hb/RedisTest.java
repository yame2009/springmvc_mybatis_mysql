package com.hb;

import java.util.ArrayList;
import java.util.List;



import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.hb.dao.redis.UserModelDao;
import com.hb.models.UserModel;


/** 
 * 测试
 * @author http://blog.csdn.net/java2000_wl 
 * @version <b>1.0</b> 
 */  
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//"classpath:config/applicationContext.xml.xml",
//"classpath:config/redis-context.xml" })
@ContextConfiguration(locations = { "classpath:config/applicationContext.xml" })
public class RedisTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private  UserModelDao  userDao;
	
	/**
	 * 新增
	 * <br>------------------------------<br>
	 */
	@Test
	public void testAddUser() {
		UserModel BaseModel = new UserModel();
		BaseModel.setId("user1");
		BaseModel.setName("java2000_wl");
		boolean result = userDao.add(BaseModel);
		Assert.assertTrue(result);
	}
	
	/**
	 * 批量新增 普通方式
	 * <br>------------------------------<br>
	 */
	@Test
	public void testAddUsers1() {
		List<UserModel> list = new ArrayList<UserModel>();
		for (int i = 10; i < 50000; i++) {
			UserModel BaseModel = new UserModel();
			BaseModel.setId("user" + i);
			BaseModel.setName("java2000_wl" + i);
			list.add(BaseModel);
		}
		long begin = System.currentTimeMillis();
		for (UserModel BaseModel : list) {
			userDao.add(BaseModel);
		}
		System.out.println(System.currentTimeMillis() -  begin);
	}
	
	/**
	 * 批量新增 pipeline方式
	 * <br>------------------------------<br>
	 */
	@Test
	public void testAddUsers2() {
		List<UserModel> list = new ArrayList<UserModel>();
		for (int i = 10; i < 1500000; i++) {
			UserModel BaseModel = new UserModel();
			BaseModel.setId("user" + i);
			BaseModel.setName("java2000_wl" + i);
			list.add(BaseModel);
		}
		long begin = System.currentTimeMillis();
		boolean result = userDao.add(list);
		System.out.println(System.currentTimeMillis() - begin);
		Assert.assertTrue(result);
	}
	
	/**
	 * 修改
	 * <br>------------------------------<br>
	 */
	@Test
	public void testUpdate() {
		UserModel BaseModel = new UserModel();
		BaseModel.setId("user1");
		BaseModel.setName("new_password");
		boolean result = userDao.update(BaseModel);
		Assert.assertTrue(result);
	}
	
	/**
	 * 通过key删除单个
	 * <br>------------------------------<br>
	 */
	@Test
	public void testDelete() {
		String key = "user1";
		userDao.delete(key);
	}
	
	/**
	 * 批量删除
	 * <br>------------------------------<br>
	 */
	@Test
	public void testDeletes() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add("user" + i);
		}
		userDao.delete(list);
	}
	
	/**
	 * 获取
	 * <br>------------------------------<br>
	 */
	@Test
	public void testGetUser() {
		String id = "user1";
		UserModel BaseModel = userDao.get(id);
		Assert.assertNotNull(BaseModel);
		Assert.assertEquals(BaseModel.getName(), "java2000_wl");
	}

	/**
	 * 设置userDao
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserModelDao userDao) {
		this.userDao = userDao;
	}
}