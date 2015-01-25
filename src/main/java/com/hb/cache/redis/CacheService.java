package com.hb.cache.redis;

import java.util.List;

public interface  CacheService {
	
	public  String generateCacheKey(String namespace, Object... keys);

	public void putIntoCache(final String key, final Object value);
	
	public void putIntoCache(final String key, final Object value, final long time);
	
	public void clearCache(final String key);
	
	public void clearCacheStartWith(final String key);
	
	public <T> T getFromCache(final String key, final Class<T> clazz);
	
	public <T> List<T> getListFromCache(final String key, final Class<T> clazz);
	
	public <T> List<T> getListFromCache(final List<String> keys, final Class<T> clazz);
}
