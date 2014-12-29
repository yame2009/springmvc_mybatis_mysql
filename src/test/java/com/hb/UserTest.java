package com.hb;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {
// "classpath:config/applicationContext.xml.xml",
// "classpath:config/redis-context.xml" })
@ContextConfiguration(locations = { "classpath:config/applicationContext.xml" })
public class UserTest {

//	@Autowired
//	private UserServiceImpl userService;
	
	@Autowired
//	private UserMapper userMapper;

	@Test
	public void testMybatisPageable()
	{
		int page = 1; //页号
		int pageSize = 20; //每页数据条数
		String sortString = "age.asc,gender.desc";//如果你想排序的话逗号分隔可以排序多列
		PageBounds pageBounds = new PageBounds(page, pageSize , Order.formString(sortString));
//		PageList<User> pageList = userMapper.findPageByFirstName("李四",pageBounds);
//		//获得结果集条总数
//		System.out.println("totalCount: " + pageList.getPaginator().getTotalCount());
		
//		System.out.println(userMapper.queryUserById("2"));
		
	}
	
	@Test
	public void testMybatisPageable2()
	{
		int page = 1; //页号
		int pageSize = 20; //每页数据条数
		String sortString = "age.asc,gender.desc";//如果你想排序的话逗号分隔可以排序多列
		PageBounds pageBounds = new PageBounds(page, pageSize , Order.formString(sortString));
//		PageList<User> pageList = userMapper.findPage(pageBounds);
//		//获得结果集条总数
//		System.out.println("totalCount: " + pageList.getPaginator().getTotalCount());
		
	}
	
	@Test
	public void testMybatisPageable3()
	{
//		System.out.println(userMapper.queryUserById("2"));
		
//		List<User> queryAllUser = userMapper.queryAllUser();
//		System.out.println(queryAllUser);
		
	}


}
