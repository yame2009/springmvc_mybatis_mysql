package com.hb.cache.redis.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import com.hb.cache.redis.CacheService;
import com.hb.util.commonUtil.Constants;

/**
 * Redis Cache 工具类项目 http://blog.csdn.net/hyman_xie/article/details/40504595
 * @author Administrator
 *
 */
@Service
public class CacheRedisImpl implements CacheService {

	@Autowired
	private RedisTemplate<String, Serializable> redisTemplate;

	@Override
	public String generateCacheKey(String namespace, Object... keys) {
		StringBuffer out = new StringBuffer();
		out.append(namespace);
		out.append(Constants.REDIS_NAMESPACE_SEPARATOR);
		for (int i = 0; i < keys.length; i++) {
			out.append(keys[i]);
			if (i != keys.length - 1) {
				out.append("_");
			}
		}
		return out.toString();
	}

	@Override
	public void putIntoCache(final String key, final Object value,
			final long expireTime) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				SerializeWriter out = new SerializeWriter();
				JSONSerializer serializer = new JSONSerializer(out);
				serializer.write(value);
				connection.set(key.getBytes(), out.toBytes("utf-8"));
				connection.expire(key.getBytes(), expireTime);
				return true;
			}
		});
	}

	@Override
	public void putIntoCache(final String key, final Object value) {
		putIntoCache(key, value, Constants.REDIS_DEFAULT_CACHE_TIME);
	}

	@Override
	public void clearCache(final String key) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.del(key.getBytes());
				return true;
			}
		});
	}

	@Override
	public void clearCacheStartWith(final String key) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				Set<byte[]> keySet = connection.keys((key + "*").getBytes());
				if (keySet != null) {
					for (byte[] k : keySet) {
						connection.del(k);
					}
				}
				return true;
			}
		});
	}

	@Override
	public <T> T getFromCache(final String key, final Class<T> clazz) {
		return redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] out = connection.get(key.getBytes());
				if (out == null) {
					return null;
				}
				return JSONObject.parseObject(out, clazz);
			}
		});
	}

	@Override
	public <T> List<T> getListFromCache(final List<String> keys,
			final Class<T> clazz) {
		return redisTemplate.execute(new RedisCallback<List<T>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<T> doInRedis(RedisConnection connection)
					throws DataAccessException {
				if (keys.size() == 0) {
					return new ArrayList<T>();
				}
				List<byte[]> keySet = new ArrayList<byte[]>();
				for (String key : keys) {
					keySet.add(key.getBytes());
				}
				List<T> out = new ArrayList<T>();
				if (keySet.size() <= Constants.REDIS_MGET_LIMIT) {
					List<byte[]> results = connection.mGet(keySet
							.toArray(new byte[keySet.size()][]));
					if (results == null) {
						return null;
					}
					for (byte[] result : results) {
						if (result != null) {
							T t = JSONObject.parseObject(result, clazz);
							out.add(t);
						}
					}
				} else {
					connection.openPipeline();
					int limitCount = keySet.size() / Constants.REDIS_MGET_LIMIT;
					int leftCount = keySet.size() % Constants.REDIS_MGET_LIMIT;
					for (int i = 0; i < limitCount; i++) {
						connection.mGet(keySet.subList(
								i * Constants.REDIS_MGET_LIMIT,
								(i + 1) * Constants.REDIS_MGET_LIMIT).toArray(
								new byte[Constants.REDIS_MGET_LIMIT][]));
					}
					if (leftCount > 0) {
						connection.mGet(keySet.subList(
								limitCount * Constants.REDIS_MGET_LIMIT,
								keySet.size()).toArray(new byte[leftCount][]));
					}
					List<Object> results = connection.closePipeline();
					for (Object object : results) {
						if (object != null) {
							for (byte[] o : (List<byte[]>) object) {
								if (o != null) {
									T t = JSONObject.parseObject(o, clazz);
									out.add(t);
								}
							}
						}
					}
				}
				return out;
			}
		});
	}

	public <T> List<T> getListFromCache(final String key, final Class<T> clazz) {
		return redisTemplate.execute(new RedisCallback<List<T>>() {
			@Override
			public List<T> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] out = connection.get(key.getBytes());
				if (out == null) {
					return null;
				}
				return JSONObject.parseArray(new String(out), clazz);
			}
		});
	}
}