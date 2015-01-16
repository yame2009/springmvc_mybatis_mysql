package com.hb.util.json.jackson;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.ser.StdSerializerProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.map.ser.std.NullSerializer;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hb.util.commonUtil.StringUtil;

/**
 * JSON的工具类
 *
 * <h3>Here is an example:</h3>
 *
 * <pre>
 *     // 将json通过类型转换成对象
 *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
 * </pre>
 * <hr />
 * 
 * <pre>
 *     // 传入转换的引用类型
 *     {@link JsonUtil JsonUtil}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
 * </pre>
 * <hr />
 * 
 * <pre>
 *     // 将对象转换成json
 *     {@link JsonUtil JsonUtil}.toJson(user);
 * </pre>
 * <hr />
 * 
 * <pre>
 *     // 将对象转换成json, 可以设置输出属性
 *     {@link JsonUtil JsonUtil}.toJson(user, {@link Inclusion Inclusion.ALWAYS});
 * </pre>
 * <hr />
 * 
 * <pre>
 *     // 将对象转换成json, 传入配置对象
 *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
 *     mapper.setSerializationInclusion({@link Inclusion Inclusion.ALWAYS});
 *     mapper.configure({@link Feature Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
 *     mapper.configure({@link Feature Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
 *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd HH:mm:ss"));
 *     {@link JsonUtil JsonUtil}.toJson(user, mapper);
 * </pre>
 * <hr />
 * 
 * <pre>
 *     // 获取Mapper对象
 *     {@link JsonUtil JsonUtil}.mapper();
 * </pre>
 *
 * @see JsonUtil JsonUtil
 * @see Feature Feature
 * @see ObjectMapper ObjectMapper
 * @see Inclusion Inclusion
 * @see IOException IOException
 * @see SimpleDateFormat SimpleDateFormat
 *
 */
public class JacksonJSONUtils {

	private static final Logger log = LoggerFactory
			.getLogger(JacksonJSONUtils.class);

	static boolean isPretty = false;

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
			DEFAULT_DATE_FORMAT);

	public static ConcurrentLinkedQueue<ObjectMapper> mapperQueue = new ConcurrentLinkedQueue<ObjectMapper>();

	private JacksonJSONUtils() {
	}

	/**
	 * 将json通过类型转换成对象
	 *
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
	 * </pre>
	 *
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            泛型类型
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		ObjectMapper objectMapper = getObjectMapper();

		try {
			return clazz.equals(String.class) ? (T) json : objectMapper
					.readValue(json, clazz);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(objectMapper);
		}

		return null;
	}

	/**
	 * 将json通过类型转换成对象
	 *
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
	 * </pre>
	 *
	 * @param json
	 *            json字符串
	 * @param typeReference
	 *            引用类型,例如: new TypeReference< List<User> >(){}
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> T fromJson(String json, TypeReference<?> typeReference) {
		ObjectMapper objectMapper = getObjectMapper();

		try {
			return (T) (typeReference.getType().equals(String.class) ? json
					: objectMapper.readValue(json, typeReference));
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(objectMapper);
		}

		return null;
	}
	
	/**
	 * 将json通过类型转换成对象
	 *
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
	 * </pre>
	 *
	 * @param jsonFile
	 *            jsonFile
	 * @param clazz
	 *            泛型类型
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> T fromJson(File jsonFile, Class<T> clazz) {
		ObjectMapper objectMapper = getObjectMapper();

		try {
			return objectMapper.readValue(jsonFile, clazz);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(objectMapper);
		}

		return null;
	}
	
	/**
	 * 将json通过类型转换成对象
	 *
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
	 * </pre>
	 *
	 * @param jsonURL
	 *            json  URL
	 * @param clazz
	 *            泛型类型
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> T fromJson(URL jsonURL, Class<T> clazz) {
		ObjectMapper objectMapper = getObjectMapper();

		try {
			return objectMapper.readValue(jsonURL, clazz);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(objectMapper);
		}

		return null;
	}
	
	/**
	 * 将json通过类型转换成对象
	 *
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
	 * </pre>
	 *
	 * @param jsonFile
	 *            json File
	 * @param valueType
	 *            JavaType  , getCollectionType(List.class, beanClass)
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> T fromJson(File jsonFile, JavaType valueType) {
		ObjectMapper objectMapper = getObjectMapper();

		try {
			return objectMapper.readValue(jsonFile, valueType);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(objectMapper);
		}

		return null;
	}

	/**
	 * 将对象转换成json
	 *
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.toJson(user);
	 * </pre>
	 *
	 * @param src
	 *            对象
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T src) {

		ObjectMapper objectMapper = getObjectMapper();
		
		try {
			return src instanceof String ? (String) src : objectMapper
					.writeValueAsString(src);//写到控制台
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(objectMapper);
		}

		return null;
	}
	
	/**
	 * 生成文件
	 * @param pathFile
	 * @param src
	 * @return
	 */
	public static <T> String toJson(File pathFile,T src)
	{
		if(pathFile == null)
		{
			return null;
		}
		ObjectMapper objectMapper = getObjectMapper();
		
		try {
			objectMapper.writeValue(pathFile,src);
			
			 String resultJson = src instanceof String ? (String) src : objectMapper
					.writeValueAsString(src);
			 return resultJson;
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(objectMapper);
		}

		return null;
	
	}

	/**
	 * 将对象转换成json, 可以设置输出属性
	 *
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.toJson(user, {@link Inclusion Inclusion.ALWAYS});
	 * </pre>
	 *
	 * {@link Inclusion Inclusion 对象枚举}
	 * <ul>
	 * <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
	 * </ul>
	 *
	 * @param src
	 *            对象
	 * @param inclusion
	 *            传入一个枚举值, 设置输出属性
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T src, Inclusion inclusion) {
		if (src instanceof String) {
			return (String) src;
		} else {
			ObjectMapper objectMapper = getObjectMapper(inclusion);
			try {
				return objectMapper.writeValueAsString(src);
			} catch (Exception e) {
				log.error(e.getMessage());
			} finally {
				returnMapper(objectMapper);
			}

			return null;
		}
	}

	/**
	 * 将对象转换成json, 传入配置对象
	 *
	 * <pre>
	 *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
	 *     mapper.setSerializationInclusion({@link Inclusion Inclusion.ALWAYS});
	 *     mapper.configure({@link Feature Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
	 *     mapper.configure({@link Feature Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
	 *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd HH:mm:ss"));
	 *     {@link JsonUtil JsonUtil}.toJson(user, mapper);
	 * </pre>
	 *
	 * {@link ObjectMapper ObjectMapper}
	 *
	 * @see ObjectMapper
	 *
	 * @param src
	 *            对象
	 * @param mapper
	 *            配置对象
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T src, ObjectMapper mapper)
			throws IOException {
		if (null != mapper) {
			if (src instanceof String) {
				return (String) src;
			} else {
				return mapper.writeValueAsString(src);
			}
		} else {
			return null;
		}
	}

	/**
	 * 通过Inclusion创建ObjectMapper对象
	 *
	 * {@link Inclusion Inclusion 对象枚举}
	 * <ul>
	 * <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
	 * </ul>
	 *
	 * @param inclusion
	 *            传入一个枚举值, 设置输出属性
	 * @param inclusion
	 * 			  设置null转换""  
	 * @return 返回ObjectMapper对象
	 */
	public static ObjectMapper generateObjectMapper(Inclusion inclusion,
			SerializerProvider sp) {

		ObjectMapper customMapper = new ObjectMapper();

		// 设置输出时包含属性的风格
		customMapper.setSerializationInclusion(inclusion);

		customMapper.setSerializerProvider(sp);

		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		customMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// 禁止使用int代表Enum的order()來反序列化Enum,非常危險
		customMapper.configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

		// 所有日期格式都统一为以下样式
		customMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));

		return customMapper;
	}

	/**
	 * 通过Inclusion创建ObjectMapper对象
	 *
	 * {@link Inclusion Inclusion 对象枚举}
	 * <ul>
	 * <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
	 * </ul>
	 *
	 * @param inclusion
	 *            传入一个枚举值, 设置输出属性
	 * @return 返回ObjectMapper对象
	 */
	public static ObjectMapper getObjectMapper(Inclusion inclusion) {
		ObjectMapper mapper = mapperQueue.poll();
		if (mapper == null) {

			StdSerializerProvider sp = new StdSerializerProvider();
			sp.setNullValueSerializer(NullSerializer.instance);//设置null转换""  

			mapper = generateObjectMapper(inclusion, sp);
		}
		return mapper;
	}

	/**
	 * 默认的映射对象
	 * 
	 * @return
	 */
	public static ObjectMapper getObjectMapper() {
		return getObjectMapper(Inclusion.ALWAYS);
	}

	public static void returnMapper(ObjectMapper mapper) {
		if (mapper != null) {
			mapperQueue.offer(mapper);
		}
	}

	public static boolean isPretty() {
		return isPretty;
	}

	public static void setPretty(boolean isPretty) {
		JacksonJSONUtils.isPretty = isPretty;
	}

	/**
	 * JSON串转换为Java泛型对象，可以是各种类型，此方法最为强大。用法看测试用例。
	 * 
	 * @param <T>
	 * @param jsonString
	 *            JSON字符串
	 * @param tr
	 *            TypeReference,例如: new TypeReference< List<FamousUser> >(){}
	 * @return List对象列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> T json2GenericObject(String jsonString,
			TypeReference<T> tr, String dateFormat) {
		if (StringUtil.isNotEmpty(jsonString)) {
			ObjectMapper objectMapper = getObjectMapper();
			try {

				objectMapper
						.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

				if (StringUtil.isBlank(dateFormat)) {
					objectMapper.setDateFormat(defaultDateFormat);
				} else {
					SimpleDateFormat sdf = (SimpleDateFormat) defaultDateFormat
							.clone();
					sdf.applyPattern(dateFormat);
					objectMapper.setDateFormat(sdf);
				}
				return (T) objectMapper.readValue(jsonString, tr);
			} catch (Exception e) {
				log.error(e.getMessage());
			} finally {
				returnMapper(objectMapper);
			}
		}
		return null;
	}
	
	/**
	 * Json字符串转Java对象
	 * @param json      json字符串
	 * @param beanClass 对象Class
	 * @return Object
	 */
	public static <T> T json2Object(String json, Class<T> beanClass) {
		return json2Object(json,beanClass,null);
	}

	/**
	 * Json字符串转Java对象
	 * 
	 * @param jsonString
	 * @param c
	 * @return
	 */
	public static <T> T json2Object(String jsonString, Class<T> c,
			String dateFormat) {
		if (StringUtil.isNotEmpty(jsonString)) {
			ObjectMapper mapper = getObjectMapper();
			try {

				// ObjectMapper objectMapper = new ObjectMapper(null, sp, null);
				mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
				if (StringUtil.isBlank(dateFormat)) {
					mapper.setDateFormat(defaultDateFormat);
				} else {
					SimpleDateFormat sdf = (SimpleDateFormat) defaultDateFormat
							.clone();
					sdf.applyPattern(dateFormat);
					mapper.setDateFormat(sdf);
				}
				return (T) mapper.readValue(jsonString, c);
			} catch (Exception e) {
				log.error(e.getMessage());
			} finally {
				returnMapper(mapper);
			}
		}
		return null;
	}
	
	

	/**
	 * Java对象转Json字符串
	 * 
	 * @param object
	 *            目标对象
	 * @param executeFields
	 *            排除字段
	 * @param includeFields
	 *            包含字段
	 * @param dateFormat
	 *            时间格式化
	 * @param isPretty
	 *            是否格式化打印 default false
	 * @return
	 */
	public static String toJson(Object object, String[] executeFields,
			String[] includeFields, String dateFormat) {
		String jsonString = "";
		ObjectMapper mapper = getObjectMapper();
		try {
			BidBeanSerializerFactory bidBeanFactory = BidBeanSerializerFactory.instance;
			if (StringUtil.isBlank(dateFormat)) {
				mapper.setDateFormat(defaultDateFormat);
			} else {
				SimpleDateFormat sdf = (SimpleDateFormat) defaultDateFormat
						.clone();
				sdf.applyPattern(dateFormat);
				mapper.setDateFormat(sdf);
			}
			if (includeFields != null) {
				String filterId = "includeFilter";
				mapper.setFilters(new SimpleFilterProvider().addFilter(
						filterId, SimpleBeanPropertyFilter
								.filterOutAllExcept(includeFields)));
				bidBeanFactory.setFilterId(filterId);
				mapper.setSerializerFactory(bidBeanFactory);

			} else if (includeFields == null && executeFields != null) {
				String filterId = "executeFilter";
				mapper.setFilters(new SimpleFilterProvider().addFilter(
						filterId, SimpleBeanPropertyFilter
								.serializeAllExcept(executeFields)));
				bidBeanFactory.setFilterId(filterId);
				mapper.setSerializerFactory(bidBeanFactory);
			}
			if (isPretty) {
				jsonString = mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(object);
			} else {
				jsonString = mapper.writeValueAsString(object);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(mapper);
		}
		return jsonString;
	}


	public static String map2Json(Map map) {
		ObjectMapper mapper = getObjectMapper();
		try {
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(mapper);
		}
		return "";
	}

	public static String list2Json(List list) {
		ObjectMapper mapper = getObjectMapper();
		try {
			return mapper.writeValueAsString(list);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(mapper);
		}
		return "";
	}

	/**
	 * json2List( jsonListString , Person.class)
	 * @param json
	 * @param beanClass
	 * @return
	 */
	public static <T> List<T> json2List(String json, Class<T> beanClass) {
		ObjectMapper mapper = getObjectMapper();
		try {

			return (List<T>) mapper.readValue(json,
					getCollectionType(List.class, beanClass));
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(mapper);
		}
		return null;
	}
	
	/**
	 * json2List( jsonListString , Person.class)
	 * @param json
	 * @param beanClass
	 * @return
	 */
	public static <T> List<T> json2List(File jsonFile, Class<T> beanClass) {
		ObjectMapper mapper = getObjectMapper();
		try {

			return (List<T>) mapper.readValue(jsonFile,
					getCollectionType(List.class, beanClass));
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(mapper);
		}
		return null;
	}

	/**
	 * json2Map(jsonMapString)
	 * @param json
	 * @return
	 */
	public static Map json2Map(String json) {
		ObjectMapper mapper = getObjectMapper();
		try {

			return (Map) mapper.readValue(json, Map.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			returnMapper(mapper);
		}
		return null;
	}

	public static JavaType getCollectionType(Class<?> collectionClass,
			Class<?>... elementClasses) {
		ObjectMapper mapper = getObjectMapper();
		return mapper.getTypeFactory().constructParametricType(collectionClass,
				elementClasses);
	}

}
