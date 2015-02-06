/* 
 * Copyright (c) 2015, S.F. Express Inc. All rights reserved.
 */
package com.hb.util.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 描述： 阿里巴巴数据源  JDBC封装类
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2015年2月6日      338342         Create
 * ****************************************************************************
 * </pre>
 * 
 * @author
 * @since 1.0
 */
public class DruidJDBCUtils {

	private static DruidDataSource dataSource = new DruidDataSource();
	// 声明线程共享变量
	public static ThreadLocal<Connection> container = new ThreadLocal<Connection>();
	// 配置说明，参考官方网址
	// http://blog.163.com/hongwei_benbear/blog/static/1183952912013518405588/
	static {
		dataSource
				.setUrl("jdbc:mysql://182.92.222.140:3306/idotest?useUnicode=true&characterEncoding=UTF-8");
		dataSource.setUsername("username");// 用户名
		dataSource.setPassword("password");// 密码
		dataSource.setInitialSize(2);
		dataSource.setMaxActive(20);
		dataSource.setMinIdle(0);
		dataSource.setMaxWait(60000);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setTestOnBorrow(false);
		dataSource.setTestWhileIdle(true);
		dataSource.setPoolPreparedStatements(false);
	}

	/**
	 * 获取数据连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			System.out.println(Thread.currentThread().getName()
					+ "连接已经开启......");
			container.set(conn);
		} catch (Exception e) {
			System.out.println("连接获取失败");
			e.printStackTrace();
		}
		return conn;
	}

	/*** 获取当前线程上的连接开启事务 */
	public static void startTransaction() {
		Connection conn = container.get();// 首先获取当前线程的连接
		if (conn == null) {// 如果连接为空
			conn = getConnection();// 从连接池中获取连接
			container.set(conn);// 将此连接放在当前线程上
			System.out.println(Thread.currentThread().getName()
					+ "空连接从dataSource获取连接");
		} else {
			System.out.println(Thread.currentThread().getName() + "从缓存中获取连接");
		}
		try {
			conn.setAutoCommit(false);// 开启事务
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 提交事务
	public static void commit() {
		try {
			Connection conn = container.get();// 从当前线程上获取连接if(conn!=null){//如果连接为空，则不做处理
			if (null != conn) {
				conn.commit();// 提交事务
				System.out.println(Thread.currentThread().getName()
						+ "事务已经提交......");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*** 回滚事务 */
	public static void rollback() {
		try {
			Connection conn = container.get();// 检查当前线程是否存在连接
			if (conn != null) {
				conn.rollback();// 回滚事务
				container.remove();// 如果回滚了，就移除这个连接
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*** 关闭连接 */
	public static void close() {
		try {
			Connection conn = container.get();
			if (conn != null) {
				conn.close();
				System.out.println(Thread.currentThread().getName() + "连接关闭");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			try {
				container.remove();// 从当前线程移除连接切记
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// 简单使用方式
	public static void main(String[] args) throws SQLException {
		// select查询
		/*
		 * Connection conn = JDBCUtils.getConnection(); PreparedStatement ps =
		 * conn.prepareStatement("SELECT 1"); ResultSet rs = ps.executeQuery();
		 * JDBCUtils.close();
		 */

		// update,insert,delete操作
		Connection conn2 = DruidJDBCUtils.getConnection();
		// 开启事务1
		DruidJDBCUtils.startTransaction();
		System.out.println("执行事务操作111111111111111....");
		DruidJDBCUtils.commit();
		// 开启事务2
		DruidJDBCUtils.startTransaction();
		System.out.println("执行事务操作222222222222....");
		DruidJDBCUtils.commit();
		DruidJDBCUtils.close();
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {

				public void run() {
					Connection conn2 = DruidJDBCUtils.getConnection();
					for (int i = 0; i < 2; i++) {
						DruidJDBCUtils.startTransaction();
						System.out.println(conn2);
						System.out.println(Thread.currentThread().getName()
								+ "执行事务操作。。。。。。。。。。。。。");
						DruidJDBCUtils.commit();
					}
					DruidJDBCUtils.close();
				}
			}).start();
		}

	}
}
