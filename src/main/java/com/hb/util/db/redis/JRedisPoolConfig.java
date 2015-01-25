package com.hb.util.db.redis;

/**
 * radis 数据库链接参数
 * @author Administrator
 *
 */
public class JRedisPoolConfig {
	/**
	 * maxTotal 控制一个pool可分配多少个jedis实例
	 */
	public static final int MAX_ACTIVE =  300;
	/**
	 * #控制一个pool最多有多少个状态为idle的jedis实例；
	 */
	public static final int MAX_IDLE = 0;
	/**
	 * #表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
	 */
	public static final long MAX_WAIT = 100000;
	/**
	 * #在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
	 */
	public static final boolean TEST_ON_BORROW = false;
	/**
	 * #在return给pool时，是否提前进行validate操作；
	 */
	public static final boolean TEST_ON_RETURN = false;
	
	/**
	 * radis 数据库IP
	 */
	public static final String REDIS_IP = "127.0.0.1";
	
	/**
	 * radis 数据库端口号
	 */
	public static final int REDIS_PORT = 6379;
	/**
	 * radis 数据库密码，默认可不设置。
	 */
	public static final String REDIS_PASSWORD = null;
	
	
}
