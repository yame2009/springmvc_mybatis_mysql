package com.hb.entity;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.github.abel533.mapper.Mapper;

/**
 * http://git.oschina.net/free/Mapper
 * 
 * http://git.oschina.net/free/Mapper/blob/master/HowToExtendMapper.md
 * 
 * @author 338342
 *
 * @param <T>
 */
public interface HsqldbMapper<T> extends Mapper<T> {

	/**
	 * 单表分页查询
	 * 
	 * @param object
	 * @param offset
	 * @param limit
	 * @return List<?>
	 */
	@SelectProvider(type = HsqldbProvider.class, method = "dynamicSQL")
	List<?> selectPage(@Param("entity") T object, @Param("offset") int offset,
			@Param("limit") int limit);

}