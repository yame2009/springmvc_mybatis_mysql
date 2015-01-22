package com.hb.util.commonUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

//import org.springframework.web.bind.ServletRequestUtils;

/**
 * 
 * 类名称：StringUtil
 * 
 * @date 2014-11-15 下午4:36:20 备注：
 */
public class StringUtil extends StringUtils {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(StringUtil.class);

	private static final int INDEX_NOT_FOUND = -1;
	private static final String EMPTY = "";
	/**
	 * <p>
	 * The maximum size to which the padding constant(s) can expand.
	 * </p>
	 */
	private static final int PAD_LIMIT = 8192;

	

	/**
	 * 功能：将半角的符号转换成全角符号.(即英文字符转中文字符)
	 * 
	 * @param str
	 *            源字符串
	 * @return String
	 * @date 2014年06月24日
	 */
	public static String changeToFull(String str) {
		String source = "1234567890!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=+\\|[];:'\",<.>/?";
		String[] decode = { "１", "２", "３", "４", "５", "６", "７", "８", "９", "０",
				"！", "＠", "＃", "＄", "％", "︿", "＆", "＊", "（", "）", "ａ", "ｂ",
				"ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ",
				"ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ",
				"Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ",
				"Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ",
				"Ｙ", "Ｚ", "－", "＿", "＝", "＋", "＼", "｜", "【", "】", "；", "：",
				"'", "\"", "，", "〈", "。", "〉", "／", "？" };
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int pos = source.indexOf(str.charAt(i));
			if (pos != -1) {
				result += decode[pos];
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 将字符串转移为ASCII码
	 * 
	 * @param cnStr
	 * @return
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// System.out.println(Integer.toHexString(bGBK[i]&0xff));
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	/**
	 * 功能：cs串中是否一个都不包含字符数组searchChars中的字符。
	 * 
	 * @param cs
	 *            字符串
	 * @param searchChars
	 *            字符数组
	 * @return boolean 都不包含返回true，否则返回false。
	 * @date 2014年06月24日
	 */
	public static boolean containsNone(CharSequence cs, char... searchChars) {
		if (cs == null || searchChars == null) {
			return true;
		}
		int csLen = cs.length();
		int csLast = csLen - 1;
		int searchLen = searchChars.length;
		int searchLast = searchLen - 1;
		for (int i = 0; i < csLen; i++) {
			char ch = cs.charAt(i);
			for (int j = 0; j < searchLen; j++) {
				if (searchChars[j] == ch) {
					if (Character.isHighSurrogate(ch)) {
						if (j == searchLast) {
							// missing low surrogate, fine, like
							// String.indexOf(String)
							return false;
						}
						if (i < csLast
								&& searchChars[j + 1] == cs.charAt(i + 1)) {
							return false;
						}
					} else {
						// ch is in the Basic Multilingual Plane
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 将字符转为 编码为Unicode，格式 '\u0020'.
	 * </p>
	 * 
	 * <pre>
	 *   CharUtils.unicodeEscaped(' ') = "\u0020" 
	 *   CharUtils.unicodeEscaped('A') = "\u0041"
	 * </pre>
	 * 
	 * @param ch
	 *            源字符串
	 * @return 转码后的字符串
	 * @date 2014年06月24日
	 */
	public static String unicodeEscaped(char ch) {
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		} else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		} else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}

	/**
	 * 计算显示的页数,特殊数据已被处理,不会报错
	 * 
	 * @param total
	 *            总数据个数
	 * @param pageSize
	 *            每页显示的个数
	 * @return
	 */
	public static int getPageNum(int total, int pageSize) {
		if (total <= 0 || pageSize <= 0) {
			return 0;
		}
		if (total % pageSize == 0) {
			return total / pageSize;
		} else {
			return total / pageSize + 1;
		}
	}

	/**
	 * 验证某字符串是否符合邮箱格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 验证某字符串是否符合手机格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		String regular = "1[3,4,5,8]{1}\\d{9}";
		Pattern pattern = Pattern.compile(regular);
		boolean flag = false;
		if (str != null) {
			Matcher matcher = pattern.matcher(str);
			flag = matcher.matches();
		}
		return flag;
	}

	/**
	 * 解决GET方式乱码问题
	 * 
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String s) throws UnsupportedEncodingException {
		if (null == s || s.length() == 0) {
			return "";
		}
		s = new String(s.getBytes("iso-8859-1"), "UTF-8");
		return s;
	}

	/**
	 * 计算当前的起始页数，特殊数据已被处理,不会报错
	 * 
	 * @param startPage
	 *            当前的起始数
	 * @param pageSize
	 *            每页个数
	 * @return
	 */
	public static int getStartPage(int startPage, int pageSize) {
		if (startPage <= 0 || pageSize <= 0) {
			return 0;
		}
		return startPage / pageSize;
	}

	/**
	 * 如果传过来个空，则返回""</br> 否则返回原对象
	 * 
	 * @param o
	 * @return
	 */
	public static Object nullToSpace(Object o) {
		if (null == o) {
			return "";
		}
		return o;
	}

	/**
	 * 提供字符串是否可转换成数值型的判断</br> 如果可转成数值，则返回false</br> 如果不可转成数值，则返回true</br> isnan
	 * == is not a number</br>
	 * 
	 * @param s
	 *            需要测试的字符串
	 * @return true or false
	 */
	public static boolean isNAN(String s) {
		if (null == s || s.length() == 0) {
			return true;
		}
		Pattern pattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+$");
		Matcher isNum = pattern.matcher(s);
		if (isNum.matches()) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isInteger(String s) {
		if (null == s || s.length() == 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(s).matches();
	}

	/**
	 * 删除掉JSON串前后的[]，某些插件不能解析带[]的json格式
	 * 
	 * @param json
	 * @return
	 */
	public static String treatJson(String json) {
		if (null == json || json.length() < 3) {
			return "";
		}
		json = json.substring(1, json.length());
		json = json.substring(0, json.length() - 1);
		return json;
	}

	/**
	 * 取随机的32位uuid
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 判断一个字符串是不是空或者为""
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNullOrSpace(String s) {
		if (null == s || s.length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 把Map<String,Object>处理成实体类
	 * 
	 * @param clazz
	 *            想要的实体类
	 * @param list
	 *            包含信息的列表
	 * @return
	 */
	public static <T> List<T> mapToList(Class<T> clazz,
			List<Map<String, Object>> list) {

		if (null == list || list.size() == 0) {
			return null;
		}
		List<T> result = new ArrayList<T>();
		Map<String, Object> map;
		for (Iterator<Map<String, Object>> iter = list.iterator(); iter
				.hasNext();) {
			map = iter.next();
			result.add(mapToObject(clazz, map));
		}
		return result;
	}

	/**
	 * 把Map<String,Object>处理成实体类
	 * 
	 * @param clazz
	 *            想要的实体类
	 * @param map
	 *            包含信息的Map对象
	 * @return
	 */
	public static <T> T mapToObject(Class<T> clazz, Map<String, Object> map) {

		if (null == map) {
			return null;
		}

		Field[] fields = clazz.getDeclaredFields(); // 取到所有类下的属性，也就是变量名
		Field field;
		T o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			String fieldName = field.getName();
			// 把属性的第一个字母处理成大写
			String stringLetter = fieldName.substring(0, 1).toUpperCase();
			// 取得set方法名，比如setBbzt
			String setName = "set" + stringLetter + fieldName.substring(1);
			// 真正取得get方法。
			Method setMethod = null;
			Class<?> fieldClass = field.getType();
			try {
				Object value = map.get(fieldName);
				if (value != null && String.valueOf(value).trim().length() > 0
						&& ReflectUtil.isHaveSuchMethod(clazz, setName)) {
					if (fieldClass == String.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						setMethod.invoke(o, String.valueOf(value));// 为其赋值
					} else if (fieldClass == Integer.class
							|| fieldClass == int.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						setMethod.invoke(o,
								Integer.parseInt(String.valueOf(value)));// 为其赋值
					} else if (fieldClass == Boolean.class
							|| fieldClass == boolean.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						setMethod.invoke(o,
								Boolean.getBoolean(String.valueOf(value)));// 为其赋值
					} else if (fieldClass == Short.class
							|| fieldClass == short.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						setMethod.invoke(o,
								Short.parseShort(String.valueOf(value)));// 为其赋值
					} else if (fieldClass == Long.class
							|| fieldClass == long.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						setMethod.invoke(o,
								Long.parseLong(String.valueOf(value)));// 为其赋值
					} else if (fieldClass == Double.class
							|| fieldClass == double.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						setMethod.invoke(o,
								Double.parseDouble(String.valueOf(value)));// 为其赋值
					} else if (fieldClass == Float.class
							|| fieldClass == float.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						setMethod.invoke(o,
								Float.parseFloat(String.valueOf(value)));// 为其赋值
					} else if (fieldClass == BigInteger.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						setMethod.invoke(o, BigInteger.valueOf(Long
								.parseLong(String.valueOf(value))));// 为其赋值
					} else if (fieldClass == BigDecimal.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						setMethod.invoke(o, BigDecimal.valueOf(Double
								.parseDouble(String.valueOf(value))));// 为其赋值
					} else if (fieldClass == Date.class) {
						setMethod = clazz.getMethod(setName, fieldClass);
						if (map.get(fieldName).getClass() == java.sql.Date.class) {
							setMethod.invoke(o, new Date(
									((java.sql.Date) value).getTime()));// 为其赋值
						} else if (map.get(fieldName).getClass() == java.sql.Time.class) {
							setMethod.invoke(o, new Date(
									((java.sql.Time) value).getTime()));// 为其赋值
						} else if (map.get(fieldName).getClass() == java.sql.Timestamp.class) {
							setMethod.invoke(o, new Date(
									((java.sql.Timestamp) value).getTime()));// 为其赋值
						}
					} else if (fieldClass == List.class) {

					}
				}
			} catch (Exception e) {
			}

		}
		return o;
	}

	// /**
	// * 本方法封装了往前台设置的header,contentType等信息
	// *
	// * @param message
	// * 需要传给前台的数据
	// * @param type
	// * 指定传给前台的数据格式,如"html","json"等
	// * @param response
	// * HttpServletResponse对象
	// * @throws IOException
	// * @createDate 2010-12-31 17:55:41
	// */
	// public static void writeToWeb(String message, String type,
	// HttpServletResponse response) throws IOException {
	// response.setHeader("Pragma", "No-cache");
	// response.setHeader("Cache-Control", "no-cache");
	// response.setContentType("text/" + type + "; charset=utf-8");
	// response.getWriter().write(message);
	// response.getWriter().close();
	// }

	// /**
	// *
	// * @param request
	// * @return
	// */
	// public static String getOrderString(HttpServletRequest request) {
	// String orderString = "";
	//
	// String sortName = ServletRequestUtils.getStringParameter(request,
	// "sort", "");
	// String sortOrder = ServletRequestUtils.getStringParameter(request,
	// "order", "");
	// if (sortName.length() > 0) {
	// orderString = sortName;
	// if (sortOrder.length() > 0) {
	// orderString += " " + sortOrder;
	// }
	// }
	// return orderString;
	// }

	// public static <T> T requestToObject(HttpServletRequest request,
	// Class<T> clazz) {
	//
	// if (null == request) {
	// return null;
	// }
	//
	// Field[] fields = clazz.getDeclaredFields(); // 取到所有类下的属性，也就是变量名
	// Field field;
	// T o = null;
	// try {
	// o = clazz.newInstance();
	// } catch (InstantiationException e1) {
	// e1.printStackTrace();
	// } catch (IllegalAccessException e1) {
	// e1.printStackTrace();
	// }
	// for (int i = 0; i < fields.length; i++) {
	// field = fields[i];
	// String fieldName = field.getName();
	// // 把属性的第一个字母处理成大写
	// String stringLetter = fieldName.substring(0, 1).toUpperCase();
	// // 取得set方法名，比如setBbzt
	// String setName = "set" + stringLetter + fieldName.substring(1);
	// // 真正取得get方法。
	// Method setMethod = null;
	// Class<?> fieldClass = field.getType();
	// try {
	// Object value = request.getParameter(fieldName);
	// Object valueArray = request.getParameterValues(fieldName);
	// if (value != null && isHaveSuchMethod(clazz, setName)) {
	// if (String.valueOf(value).trim().length() > 0) {
	// if (fieldClass == String.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o, String.valueOf(value));// 为其赋值
	// } else if (fieldClass == Integer.class
	// || fieldClass == int.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o,
	// Integer.parseInt(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Boolean.class
	// || fieldClass == boolean.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o,
	// Boolean.getBoolean(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Short.class
	// || fieldClass == short.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o,
	// Short.parseShort(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Long.class
	// || fieldClass == long.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o,
	// Long.parseLong(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Double.class
	// || fieldClass == double.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o,
	// Double.parseDouble(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Float.class
	// || fieldClass == float.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o,
	// Float.parseFloat(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == BigInteger.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o, BigInteger.valueOf(Long
	// .parseLong(String.valueOf(value))));// 为其赋值
	// } else if (fieldClass == BigDecimal.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o, BigDecimal.valueOf(Double
	// .parseDouble(String.valueOf(value))));// 为其赋值
	// } else if (fieldClass == Date.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// String tempValue = value.toString();
	// Date tempDate = null;
	// // 根据字符串长度确定要用何种形式转换
	// if (tempValue.length() > 0
	// && tempValue.length() < 12) {
	// tempDate = DateUtil.StringToDate(
	// value.toString(),
	// DateUtil.FORMATER_YYYY_MM_DD);
	// } else if (tempValue.length() >= 13
	// && tempValue.length() < 21) {
	// tempDate = DateUtil.StringToDate(
	// value.toString(),
	// DateUtil.FORMATER_YYYY_MM_DD_HH_MM_SS);
	// }
	// // 如果转换成功了，就赋值，如果不成功就让它空着吧。
	// if (null != tempDate) {
	// setMethod.invoke(o, tempDate);// 为其赋值
	// }
	// }
	// } else {
	// Object oo = null;
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o, oo);// 为其赋值
	// }
	//
	// }
	// if (valueArray != null && isHaveSuchMethod(clazz, setName)) {
	// if (fieldClass == String[].class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(o, value == null ? null : valueArray);// 为其赋值
	// }
	// }
	// } catch (Exception e) {
	// }
	//
	// }
	// return o;
	// }

	// /**
	// * 自动将传过来的参数放到实体，本方法仅适用于修改页面 本方法会把接到的空字符串也set进实体
	// *
	// * @param request
	// * @param entity
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// public static <T> T requestToObject(HttpServletRequest request, T entity)
	// {
	//
	// if (null == request || null == entity) {
	// return null;
	// }
	// Class<T> clazz = (Class<T>) entity.getClass();
	// Field[] fields = clazz.getDeclaredFields(); // 取到所有类下的属性，也就是变量名
	// Field field;
	// for (int i = 0; i < fields.length; i++) {
	// field = fields[i];
	// String fieldName = field.getName();
	// // 把属性的第一个字母处理成大写
	// String stringLetter = fieldName.substring(0, 1).toUpperCase();
	// // 取得set方法名，比如setBbzt
	// String setName = "set" + stringLetter + fieldName.substring(1);
	// // 真正取得get方法。
	// Method setMethod = null;
	// Class<?> fieldClass = field.getType();
	// try {
	// Object value = request.getParameter(fieldName);
	// Object valueArray = request.getParameterValues(fieldName);
	// if (value != null && isHaveSuchMethod(clazz, setName)) {
	// if (String.valueOf(value).trim().length() > 0) {
	// if (fieldClass == String.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity, String.valueOf(value));// 为其赋值
	// } else if (fieldClass == Integer.class
	// || fieldClass == int.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity,
	// Integer.parseInt(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Boolean.class
	// || fieldClass == boolean.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity,
	// Boolean.getBoolean(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Short.class
	// || fieldClass == short.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity,
	// Short.parseShort(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Long.class
	// || fieldClass == long.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity,
	// Long.parseLong(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Double.class
	// || fieldClass == double.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity,
	// Double.parseDouble(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == Float.class
	// || fieldClass == float.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity,
	// Float.parseFloat(String.valueOf(value)));// 为其赋值
	// } else if (fieldClass == BigInteger.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity, BigInteger.valueOf(Long
	// .parseLong(String.valueOf(value))));// 为其赋值
	// } else if (fieldClass == BigDecimal.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity, BigDecimal.valueOf(Double
	// .parseDouble(String.valueOf(value))));// 为其赋值
	// } else if (fieldClass == Date.class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// String tempValue = value.toString();
	// Date tempDate = null;
	// // 根据字符串长度确定要用何种形式转换
	// if (tempValue.length() > 0
	// && tempValue.length() < 12) {
	// tempDate = DateUtil.StringToDate(
	// value.toString(),
	// DateUtil.FORMATER_YYYY_MM_DD);
	// } else if (tempValue.length() >= 13
	// && tempValue.length() < 21) {
	// tempDate = DateUtil.StringToDate(
	// value.toString(),
	// DateUtil.FORMATER_YYYY_MM_DD_HH_MM_SS);
	// }
	// // 如果转换成功了，就赋值，如果不成功就让它空着吧。
	// if (null != tempDate) {
	// setMethod.invoke(entity, tempDate);// 为其赋值
	// }
	// }
	// } else {
	// Object oo = null;
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity, oo);// 为其赋值
	// }
	//
	// }
	// if (valueArray != null && isHaveSuchMethod(clazz, setName)) {
	// if (fieldClass == String[].class) {
	// setMethod = clazz.getMethod(setName, fieldClass);
	// setMethod.invoke(entity, value == null ? null
	// : valueArray);// 为其赋值
	// }
	// }
	// } catch (Exception e) {
	// }
	//
	// }
	// return entity;
	// }

	/**
	 * 按照给定的分隔标志，将列表封闭成字符串
	 * 
	 * @param list
	 * @param reg
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String join(List list, String reg) {
		StringBuffer sb = new StringBuffer();
		if (null == list || list.size() == 0) {
			return null;
		}
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			sb.append(iter.next()).append(reg);
		}
		int length = sb.length();
		if (length > 0) {
			sb = sb.delete(length - 1, length);
		}
		return sb.toString();
	}

	/**
	 * 生成六位随机数
	 * 
	 * @return
	 */
	public static Integer getRandom() {
		Random ran = new Random();
		int r = 0;
		m1: while (true) {
			int n = ran.nextInt(1000000);
			r = n;
			int[] bs = new int[6];
			for (int i = 0; i < bs.length; i++) {
				bs[i] = n % 10;
				n /= 10;
			}
			Arrays.sort(bs);
			for (int i = 1; i < bs.length; i++) {
				if (bs[i - 1] == bs[i]) {
					continue m1;
				}
			}
			break;
		}
		return r;
	}

	/**
	 * 将字符串转为输入流
	 * 
	 * @param sInputString
	 * @return
	 */
	public static InputStream getStringStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
						sInputString.getBytes());
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将一个输入流转化为字符串
	 */
	public static String getStreamString(InputStream tInputStream) {
		if (tInputStream != null) {
			try {
				BufferedReader tBufferedReader = new BufferedReader(
						new InputStreamReader(tInputStream));
				StringBuffer tStringBuffer = new StringBuffer();
				String sTempOneLine = new String("");
				while ((sTempOneLine = tBufferedReader.readLine()) != null) {
					tStringBuffer.append(sTempOneLine);
				}
				return tStringBuffer.toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 判断某字符串是否非空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成
	 * 
	 * StringUtils.isBlank("\t \n \f \r") = true //对于制表符、换行符、换页符和回车符
	 * 
	 * @param s
	 * @return true;s 为空白字符串
	 */
	public static boolean isBlank(String s) {
		if (isNullOrSpace(s)) {
			return true;
		}

		String trim = s.trim();
		int length = trim.length();
		if (trim.isEmpty() || length == 0) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (Character.isWhitespace(trim.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断某字符串是否不为空且长度不为0且不由空白符(whitespace) 构成，等于 !isBlank(String str)
	 * 
	 * StringUtils.isBlank("\t \n \f \r") = false //对于制表符、换行符、换页符和回车符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * <p>
	 * 进行tostring操作，如果传入的是null，返回空字符串。
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.toString(null)         = "" 
	 * ObjectUtils.toString("")           = "" 
	 * ObjectUtils.toString("bat")        = "bat" 
	 * ObjectUtils.toString(Boolean.TRUE) = "true"
	 * </pre>
	 * 
	 * @param obj
	 *            源
	 * @return String
	 */
	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	/**
	 * <p>
	 * 进行tostring操作，如果传入的是null，返回指定的默认值。
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.toString(null, null)           = null 
	 * ObjectUtils.toString(null, "null")         = "null" 
	 * ObjectUtils.toString("", "null")           = "" 
	 * ObjectUtils.toString("bat", "null")        = "bat" 
	 * ObjectUtils.toString(Boolean.TRUE, "null") = "true"
	 * </pre>
	 * 
	 * @param obj
	 *            源
	 * @param nullStr
	 *            如果obj为null时返回这个指定值
	 * @return String
	 */
	public static String toString(Object obj, String nullStr) {
		return obj == null ? nullStr : obj.toString();
	}

	/**
	 * <p>
	 * 只从源字符串中移除指定开头子字符串.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.removeStart(null, *)      = null 
	 * StringUtil.removeStart("", *)        = "" 
	 * StringUtil.removeStart(*, null)      = * 
	 * StringUtil.removeStart("www.domain.com", "www.")   = "domain.com" 
	 * StringUtil.removeStart("domain.com", "www.")       = "domain.com" 
	 * StringUtil.removeStart("www.domain.com", "domain") = "www.domain.com" 
	 * StringUtil.removeStart("abc", "")    = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            源字符串
	 * @param remove
	 *            将要被移除的子字符串
	 * @return String
	 */
	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	/**
	 * <p>
	 * 只从源字符串中移除指定结尾的子字符串.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.removeEnd(null, *)      = null 
	 * StringUtil.removeEnd("", *)        = "" 
	 * StringUtil.removeEnd(*, null)      = * 
	 * StringUtil.removeEnd("www.domain.com", ".com.")  = "www.domain.com" 
	 * StringUtil.removeEnd("www.domain.com", ".com")   = "www.domain" 
	 * StringUtil.removeEnd("www.domain.com", "domain") = "www.domain.com" 
	 * StringUtil.removeEnd("abc", "")    = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            源字符串
	 * @param remove
	 *            将要被移除的子字符串
	 * @return String
	 */
	public static String removeEnd(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.endsWith(remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	/**
	 * <p>
	 * 将一个字符串重复N次
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.repeat(null, 2) = null 
	 * StringUtil.repeat("", 0)   = "" 
	 * StringUtil.repeat("", 2)   = "" 
	 * StringUtil.repeat("a", 3)  = "aaa" 
	 * StringUtil.repeat("ab", 2) = "abab" 
	 * StringUtil.repeat("a", -2) = ""
	 * </pre>
	 * 
	 * @param str
	 *            源字符串
	 * @param repeat
	 *            重复的次数
	 * @return String
	 */
	public static String repeat(String str, int repeat) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		if (repeat <= 0) {
			return EMPTY;
		}
		int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) {
			return str;
		}
		if (inputLength == 1 && repeat <= PAD_LIMIT) {
			return repeat(str.charAt(0), repeat);
		}

		int outputLength = inputLength * repeat;
		switch (inputLength) {
		case 1:
			return repeat(str.charAt(0), repeat);
		case 2:
			char ch0 = str.charAt(0);
			char ch1 = str.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default:
			StringBuilder buf = new StringBuilder(outputLength);
			for (int i = 0; i < repeat; i++) {
				buf.append(str);
			}
			return buf.toString();
		}
	}
	
	/** 
     * <p> 
     * 将某个字符重复N次. 
     * </p> 
     * 
     * @param ch 
     *            某个字符 
     * @param repeat 
     *            重复次数 
     * @return String 
     */  
    public static String repeat(char ch, int repeat) {  
        char[] buf = new char[repeat];  
        for (int i = repeat - 1; i >= 0; i--) {  
            buf[i] = ch;  
        }  
        return new String(buf);  
    }  
    
    /** 
     * <p> 
     * 字符串长度达不到指定长度时，在字符串右边补指定的字符. 
     * </p> 
     *  
     * <pre> 
     * StringUtil.rightPad(null, *, *)     = null 
     * StringUtil.rightPad("", 3, 'z')     = "zzz" 
     * StringUtil.rightPad("bat", 3, 'z')  = "bat" 
     * StringUtil.rightPad("bat", 5, 'z')  = "batzz" 
     * StringUtil.rightPad("bat", 1, 'z')  = "bat" 
     * StringUtil.rightPad("bat", -1, 'z') = "bat" 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param size 
     *            指定的长度 
     * @param padChar 
     *            进行补充的字符 
     * @return String 
     */  
    public static String rightPad(String str, int size, char padChar) {  
        if (str == null) {  
            return null;  
        }  
        int pads = size - str.length();  
        if (pads <= 0) {  
            return str; // returns original String when possible  
        }  
        if (pads > PAD_LIMIT) {  
            return rightPad(str, size, String.valueOf(padChar));  
        }  
        return str.concat(repeat(padChar, pads));  
    }  
   
    /** 
     * <p> 
     * 扩大字符串长度，从左边补充指定字符 
     * </p> 
     *  
     * <pre> 
     * StringUtil.rightPad(null, *, *)      = null 
     * StringUtil.rightPad("", 3, "z")      = "zzz" 
     * StringUtil.rightPad("bat", 3, "yz")  = "bat" 
     * StringUtil.rightPad("bat", 5, "yz")  = "batyz" 
     * StringUtil.rightPad("bat", 8, "yz")  = "batyzyzy" 
     * StringUtil.rightPad("bat", 1, "yz")  = "bat" 
     * StringUtil.rightPad("bat", -1, "yz") = "bat" 
     * StringUtil.rightPad("bat", 5, null)  = "bat  " 
     * StringUtil.rightPad("bat", 5, "")    = "bat  " 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param size 
     *            扩大后的长度 
     * @param padStr 
     *            在右边补充的字符串 
     * @return String 
     */  
    public static String rightPad(String str, int size, String padStr) {  
        if (str == null) {  
            return null;  
        }  
        if (isEmpty(padStr)) {  
            padStr = " ";  
        }  
        int padLen = padStr.length();  
        int strLen = str.length();  
        int pads = size - strLen;  
        if (pads <= 0) {  
            return str; // returns original String when possible  
        }  
        if (padLen == 1 && pads <= PAD_LIMIT) {  
            return rightPad(str, size, padStr.charAt(0));  
        }  
   
        if (pads == padLen) {  
            return str.concat(padStr);  
        } else if (pads < padLen) {  
            return str.concat(padStr.substring(0, pads));  
        } else {  
            char[] padding = new char[pads];  
            char[] padChars = padStr.toCharArray();  
            for (int i = 0; i < pads; i++) {  
                padding[i] = padChars[i % padLen];  
            }  
            return str.concat(new String(padding));  
        }  
    }  
   
    /** 
     * <p> 
     * 扩大字符串长度，从左边补充空格 
     * </p> 
     * 
     * <pre> 
     * StringUtil.leftPad(null, *)   = null 
     * StringUtil.leftPad("", 3)     = "   " 
     * StringUtil.leftPad("bat", 3)  = "bat" 
     * StringUtil.leftPad("bat", 5)  = "  bat" 
     * StringUtil.leftPad("bat", 1)  = "bat" 
     * StringUtil.leftPad("bat", -1) = "bat" 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param size 
     *            扩大后的长度 
     * @return String 
     */  
    public static String leftPad(String str, int size) {  
        return leftPad(str, size, ' ');  
    }  
   
    /** 
     * <p> 
     * 扩大字符串长度，从左边补充指定的字符 
     * </p> 
     * 
     * <pre> 
     * StringUtil.leftPad(null, *, *)     = null 
     * StringUtil.leftPad("", 3, 'z')     = "zzz" 
     * StringUtil.leftPad("bat", 3, 'z')  = "bat" 
     * StringUtil.leftPad("bat", 5, 'z')  = "zzbat" 
     * StringUtil.leftPad("bat", 1, 'z')  = "bat" 
     * StringUtil.leftPad("bat", -1, 'z') = "bat" 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param size 
     *            扩大后的长度 
     * @param padStr 
     *            补充的字符 
     * @return String 
     */  
    public static String leftPad(String str, int size, char padChar) {  
        if (str == null) {  
            return null;  
        }  
        int pads = size - str.length();  
        if (pads <= 0) {  
            return str; // returns original String when possible  
        }  
        if (pads > PAD_LIMIT) {  
            return leftPad(str, size, String.valueOf(padChar));  
        }  
        return repeat(padChar, pads).concat(str);  
    }  
   
    /** 
     * <p> 
     * 扩大字符串长度，从左边补充指定的字符 
     * </p> 
     *  
     * <pre> 
     * StringUtil.leftPad(null, *, *)      = null 
     * StringUtil.leftPad("", 3, "z")      = "zzz" 
     * StringUtil.leftPad("bat", 3, "yz")  = "bat" 
     * StringUtil.leftPad("bat", 5, "yz")  = "yzbat" 
     * StringUtil.leftPad("bat", 8, "yz")  = "yzyzybat" 
     * StringUtil.leftPad("bat", 1, "yz")  = "bat" 
     * StringUtil.leftPad("bat", -1, "yz") = "bat" 
     * StringUtil.leftPad("bat", 5, null)  = "  bat" 
     * StringUtil.leftPad("bat", 5, "")    = "  bat" 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param size 
     *            扩大后的长度 
     * @param padStr 
     *            补充的字符串 
     * @return String 
     */  
    public static String leftPad(String str, int size, String padStr) {  
        if (str == null) {  
            return null;  
        }  
        if (isEmpty(padStr)) {  
            padStr = " ";  
        }  
        int padLen = padStr.length();  
        int strLen = str.length();  
        int pads = size - strLen;  
        if (pads <= 0) {  
            return str; // returns original String when possible  
        }  
        if (padLen == 1 && pads <= PAD_LIMIT) {  
            return leftPad(str, size, padStr.charAt(0));  
        }  
   
        if (pads == padLen) {  
            return padStr.concat(str);  
        } else if (pads < padLen) {  
            return padStr.substring(0, pads).concat(str);  
        } else {  
            char[] padding = new char[pads];  
            char[] padChars = padStr.toCharArray();  
            for (int i = 0; i < pads; i++) {  
                padding[i] = padChars[i % padLen];  
            }  
            return new String(padding).concat(str);  
        }  
    }  
   
    /** 
     * <p> 
     * 扩大字符串长度并将现在的字符串居中，被扩大部分用空格填充。 
     * <p> 
     *  
     * <pre> 
     * StringUtil.center(null, *)   = null 
     * StringUtil.center("", 4)     = "    " 
     * StringUtil.center("ab", -1)  = "ab" 
     * StringUtil.center("ab", 4)   = " ab " 
     * StringUtil.center("abcd", 2) = "abcd" 
     * StringUtil.center("a", 4)    = " a  " 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param size 
     *            扩大后的长度 
     * @return String 
     */  
    public static String center(String str, int size) {  
        return center(str, size, ' ');  
    }  
   
    /** 
     * <p> 
     * 将字符串长度修改为指定长度，并进行居中显示。 
     * </p> 
     * 
     * <pre> 
     * StringUtil.center(null, *, *)     = null 
     * StringUtil.center("", 4, ' ')     = "    " 
     * StringUtil.center("ab", -1, ' ')  = "ab" 
     * StringUtil.center("ab", 4, ' ')   = " ab" 
     * StringUtil.center("abcd", 2, ' ') = "abcd" 
     * StringUtil.center("a", 4, ' ')    = " a  " 
     * StringUtil.center("a", 4, 'y')    = "yayy" 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param size 
     *            指定的长度 
     * @param padStr 
     *            长度不够时补充的字符串 
     * @return String 
     * @throws IllegalArgumentException 
     *             如果被补充字符串为 null或者 empty 
     */  
    public static String center(String str, int size, char padChar) {  
        if (str == null || size <= 0) {  
            return str;  
        }  
        int strLen = str.length();  
        int pads = size - strLen;  
        if (pads <= 0) {  
            return str;  
        }  
        str = leftPad(str, strLen + pads / 2, padChar);  
        str = rightPad(str, size, padChar);  
        return str;  
    }  
   
    /** 
     * <p> 
     * 将字符串长度修改为指定长度，并进行居中显示。 
     * </p> 
     * 
     * <pre> 
     * StringUtil.center(null, *, *)     = null 
     * StringUtil.center("", 4, " ")     = "    " 
     * StringUtil.center("ab", -1, " ")  = "ab" 
     * StringUtil.center("ab", 4, " ")   = " ab" 
     * StringUtil.center("abcd", 2, " ") = "abcd" 
     * StringUtil.center("a", 4, " ")    = " a  " 
     * StringUtil.center("a", 4, "yz")   = "yayz" 
     * StringUtil.center("abc", 7, null) = "  abc  " 
     * StringUtil.center("abc", 7, "")   = "  abc  " 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param size 
     *            指定的长度 
     * @param padStr 
     *            长度不够时补充的字符串 
     * @return String 
     * @throws IllegalArgumentException 
     *             如果被补充字符串为 null或者 empty 
     */  
    public static String center(String str, int size, String padStr) {  
        if (str == null || size <= 0) {  
            return str;  
        }  
        if (isEmpty(padStr)) {  
            padStr = " ";  
        }  
        int strLen = str.length();  
        int pads = size - strLen;  
        if (pads <= 0) {  
            return str;  
        }  
        str = leftPad(str, strLen + pads / 2, padStr);  
        str = rightPad(str, size, padStr);  
        return str;  
    }  
    
    /** 
     * <p> 
     * 检查字符串是否全部为小写. 
     * </p> 
     *  
     * <pre> 
     * StringUtil.isAllLowerCase(null)   = false 
     * StringUtil.isAllLowerCase("")     = false 
     * StringUtil.isAllLowerCase("  ")   = false 
     * StringUtil.isAllLowerCase("abc")  = true 
     * StringUtil.isAllLowerCase("abC") = false 
     * </pre> 
     * 
     * @param cs 
     *            源字符串 
     * @return String 
     */  
    public static boolean isAllLowerCase(String cs) {  
        if (cs == null || isEmpty(cs)) {  
            return false;  
        }  
        int sz = cs.length();  
        for (int i = 0; i < sz; i++) {  
            if (Character.isLowerCase(cs.charAt(i)) == false) {  
                return false;  
            }  
        }  
        return true;  
    }  
   
    /** 
     * <p> 
     * 检查是否都是大写. 
     * </p> 
     *  
     * <pre> 
     * StringUtil.isAllUpperCase(null)   = false 
     * StringUtil.isAllUpperCase("")     = false 
     * StringUtil.isAllUpperCase("  ")   = false 
     * StringUtil.isAllUpperCase("ABC")  = true 
     * StringUtil.isAllUpperCase("aBC") = false 
     * </pre> 
     * 
     * @param cs 
     *            源字符串 
     * @return String 
     */  
    public static boolean isAllUpperCase(String cs) {  
        if (cs == null || StringUtil.isEmpty(cs)) {  
            return false;  
        }  
        int sz = cs.length();  
        for (int i = 0; i < sz; i++) {  
            if (Character.isUpperCase(cs.charAt(i)) == false) {  
                return false;  
            }  
        }  
        return true;  
    }  
    
    /** 
     * <p> 
     * 反转字符串. 
     * </p> 
     *  
     * <pre> 
     * StringUtil.reverse(null)  = null 
     * StringUtil.reverse("")    = "" 
     * StringUtil.reverse("bat") = "tab" 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @return String 
     */  
    public static String reverse(String str) {  
        if (str == null) {  
            return null;  
        }  
        return new StringBuilder(str).reverse().toString();  
    }  
   
    /** 
     * <p> 
     * 字符串达不到一定长度时在右边补空白. 
     * </p> 
     *  
     * <pre> 
     * StringUtil.rightPad(null, *)   = null 
     * StringUtil.rightPad("", 3)     = "   " 
     * StringUtil.rightPad("bat", 3)  = "bat" 
     * StringUtil.rightPad("bat", 5)  = "bat  " 
     * StringUtil.rightPad("bat", 1)  = "bat" 
     * StringUtil.rightPad("bat", -1) = "bat" 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param size 
     *            指定的长度 
     * @return String 
     */  
    public static String rightPad(String str, int size) {  
        return rightPad(str, size, ' ');  
    }  
   
    /** 
     * 从右边截取字符串.</p> 
     *  
     * <pre> 
     * StringUtil.right(null, *)    = null 
     * StringUtil.right(*, -ve)     = "" 
     * StringUtil.right("", *)      = "" 
     * StringUtil.right("abc", 0)   = "" 
     * StringUtil.right("abc", 2)   = "bc" 
     * StringUtil.right("abc", 4)   = "abc" 
     * </pre> 
     *  
     * @param str 
     *            源字符串 
     * @param len 
     *            长度 
     * @return String 
     */  
    public static String right(String str, int len) {  
        if (str == null) {  
            return null;  
        }  
        if (len < 0) {  
            return EMPTY;  
        }  
        if (str.length() <= len) {  
            return str;  
        }  
        return str.substring(str.length() - len);  
    }  
   
    /** 
     * <p> 
     * 截取一个字符串的前几个. 
     * </p> 
     *  
     * <pre> 
     * StringUtil.left(null, *)    = null 
     * StringUtil.left(*, -ve)     = "" 
     * StringUtil.left("", *)      = "" 
     * StringUtil.left("abc", 0)   = "" 
     * StringUtil.left("abc", 2)   = "ab" 
     * StringUtil.left("abc", 4)   = "abc" 
     * </pre> 
     *  
     * @param str 
     *            源字符串 
     * @param len 
     *            截取的长度 
     * @return the String 
     */  
    public static String left(String str, int len) {  
        if (str == null) {  
            return null;  
        }  
        if (len < 0) {  
            return EMPTY;  
        }  
        if (str.length() <= len) {  
            return str;  
        }  
        return str.substring(0, len);  
    }  
   
    /** 
     * <p> 
     * 得到tag字符串中间的子字符串，只返回第一个匹配项。 
     * </p> 
     *  
     * <pre> 
     * StringUtil.substringBetween(null, *)            = null 
     * StringUtil.substringBetween("", "")             = "" 
     * StringUtil.substringBetween("", "tag")          = null 
     * StringUtil.substringBetween("tagabctag", null)  = null 
     * StringUtil.substringBetween("tagabctag", "")    = "" 
     * StringUtil.substringBetween("tagabctag", "tag") = "abc" 
     * </pre> 
     *  
     * @param str 
     *            源字符串。 
     * @param tag 
     *            标识字符串。 
     * @return String 子字符串, 如果没有符合要求的，返回{@code null}。 
     */  
    public static String substringBetween(String str, String tag) {  
        return substringBetween(str, tag, tag);  
    }  
   
    /** 
     * <p> 
     * 得到两个字符串中间的子字符串，只返回第一个匹配项。 
     * </p> 
     *  
     * <pre> 
     * StringUtil.substringBetween("wx[b]yz", "[", "]") = "b" 
     * StringUtil.substringBetween(null, *, *)          = null 
     * StringUtil.substringBetween(*, null, *)          = null 
     * StringUtil.substringBetween(*, *, null)          = null 
     * StringUtil.substringBetween("", "", "")          = "" 
     * StringUtil.substringBetween("", "", "]")         = null 
     * StringUtil.substringBetween("", "[", "]")        = null 
     * StringUtil.substringBetween("yabcz", "", "")     = "" 
     * StringUtil.substringBetween("yabcz", "y", "z")   = "abc" 
     * StringUtil.substringBetween("yabczyabcz", "y", "z")   = "abc" 
     * </pre> 
     *  
     * @param str 
     *            源字符串 
     * @param open 
     *            起字符串。 
     * @param close 
     *            末字符串。 
     * @return String 子字符串, 如果没有符合要求的，返回{@code null}。 
     */  
    public static String substringBetween(String str, String open, String close) {  
        if (str == null || open == null || close == null) {  
            return null;  
        }  
        int start = str.indexOf(open);  
        if (start != INDEX_NOT_FOUND) {  
            int end = str.indexOf(close, start + open.length());  
            if (end != INDEX_NOT_FOUND) {  
                return str.substring(start + open.length(), end);  
            }  
        }  
        return null;  
    }  
   
    /** 
     * <p> 
     * 得到两个字符串中间的子字符串，所有匹配项组合为数组并返回。 
     * </p> 
     *  
     * <pre> 
     * StringUtil.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"] 
     * StringUtil.substringsBetween(null, *, *)            = null 
     * StringUtil.substringsBetween(*, null, *)            = null 
     * StringUtil.substringsBetween(*, *, null)            = null 
     * StringUtil.substringsBetween("", "[", "]")          = [] 
     * </pre> 
     * 
     * @param str 
     *            源字符串 
     * @param open 
     *            起字符串。 
     * @param close 
     *            末字符串。 
     * @return String 子字符串数组, 如果没有符合要求的，返回{@code null}。 
     */  
    public static String[] substringsBetween(String str, String open,  
            String close) {  
        if (str == null || isEmpty(open) || isEmpty(close)) {  
            return null;  
        }  
        int strLen = str.length();  
        if (strLen == 0) {  
            return new String[0];  
        }  
        int closeLen = close.length();  
        int openLen = open.length();  
        List<String> list = new ArrayList<String>();  
        int pos = 0;  
        while (pos < strLen - closeLen) {  
            int start = str.indexOf(open, pos);  
            if (start < 0) {  
                break;  
            }  
            start += openLen;  
            int end = str.indexOf(close, start);  
            if (end < 0) {  
                break;  
            }  
            list.add(str.substring(start, end));  
            pos = end + closeLen;  
        }  
        if (list.isEmpty()) {  
            return null;  
        }  
        return list.toArray(new String[list.size()]);  
    }  
   
    /** 
     * 功能：切换字符串中的所有字母大小写。<br/> 
     *  
     * <pre> 
     * StringUtil.swapCase(null)                 = null 
     * StringUtil.swapCase("")                   = "" 
     * StringUtil.swapCase("The dog has a BONE") = "tHE DOG HAS A bone" 
     * </pre> 
     *  
     * 
     * @param str 
     *            源字符串 
     * @return String 
     */  
    public static String swapCase(String str) {  
        if (StringUtil.isEmpty(str)) {  
            return str;  
        }  
        char[] buffer = str.toCharArray();  
   
        boolean whitespace = true;  
   
        for (int i = 0; i < buffer.length; i++) {  
            char ch = buffer[i];  
            if (Character.isUpperCase(ch)) {  
                buffer[i] = Character.toLowerCase(ch);  
                whitespace = false;  
            } else if (Character.isTitleCase(ch)) {  
                buffer[i] = Character.toLowerCase(ch);  
                whitespace = false;  
            } else if (Character.isLowerCase(ch)) {  
                if (whitespace) {  
                    buffer[i] = Character.toTitleCase(ch);  
                    whitespace = false;  
                } else {  
                    buffer[i] = Character.toUpperCase(ch);  
                }  
            } else {  
                whitespace = Character.isWhitespace(ch);  
            }  
        }  
        return new String(buffer);  
    }  
   
    /** 
     * 功能：截取出最后一个标志位之后的字符串.<br/> 
     * 如果sourceStr为empty或者expr为null，直接返回源字符串。<br/> 
     * 如果expr长度为0，直接返回sourceStr。<br/> 
     * 如果expr在sourceStr中不存在，直接返回sourceStr。<br/> 
     *  
     * @author  
     * @date 2014年06月24日 
     * @param sourceStr 
     *            被截取的字符串 
     * @param expr 
     *            分隔符 
     * @return String 
     */  
    public static String substringAfterLast(String sourceStr, String expr) {  
        if (isEmpty(sourceStr) || expr == null) {  
            return sourceStr;  
        }  
        if (expr.length() == 0) {  
            return sourceStr;  
        }  
   
        int pos = sourceStr.lastIndexOf(expr);  
        if (pos == -1) {  
            return sourceStr;  
        }  
        return sourceStr.substring(pos + expr.length());  
    }  
   
    /** 
     * 功能：截取出最后一个标志位之前的字符串.<br/> 
     * 如果sourceStr为empty或者expr为null，直接返回源字符串。<br/> 
     * 如果expr长度为0，直接返回sourceStr。<br/> 
     * 如果expr在sourceStr中不存在，直接返回sourceStr。<br/> 
     *  
     * @author  
     * @date 2014年06月24日 
     * @param sourceStr 
     *            被截取的字符串 
     * @param expr 
     *            分隔符 
     * @return String 
     */  
    public static String substringBeforeLast(String sourceStr, String expr) {  
        if (isEmpty(sourceStr) || expr == null) {  
            return sourceStr;  
        }  
        if (expr.length() == 0) {  
            return sourceStr;  
        }  
        int pos = sourceStr.lastIndexOf(expr);  
        if (pos == -1) {  
            return sourceStr;  
        }  
        return sourceStr.substring(0, pos);  
    }  
   
    /** 
     * 功能：截取出第一个标志位之后的字符串.<br/> 
     * 如果sourceStr为empty或者expr为null，直接返回源字符串。<br/> 
     * 如果expr长度为0，直接返回sourceStr。<br/> 
     * 如果expr在sourceStr中不存在，直接返回sourceStr。<br/> 
     *  
     * @author  
     * @date 2014年06月24日 
     * @param sourceStr 
     *            被截取的字符串 
     * @param expr 
     *            分隔符 
     * @return String 
     */  
    public static String substringAfter(String sourceStr, String expr) {  
        if (isEmpty(sourceStr) || expr == null) {  
            return sourceStr;  
        }  
        if (expr.length() == 0) {  
            return sourceStr;  
        }  
   
        int pos = sourceStr.indexOf(expr);  
        if (pos == -1) {  
            return sourceStr;  
        }  
        return sourceStr.substring(pos + expr.length());  
    }  
   
    /** 
     * 功能：截取出第一个标志位之前的字符串.<br/> 
     * 如果sourceStr为empty或者expr为null，直接返回源字符串。<br/> 
     * 如果expr长度为0，直接返回sourceStr。<br/> 
     * 如果expr在sourceStr中不存在，直接返回sourceStr。<br/> 
     * 如果expr在sourceStr中存在不止一个，以第一个位置为准。 
     *  
     * @author  
     * @date 2014年06月24日 
     * @param sourceStr 
     *            被截取的字符串 
     * @param expr 
     *            分隔符 
     * @return String 
     */  
    public static String substringBefore(String sourceStr, String expr) {  
        if (isEmpty(sourceStr) || expr == null) {  
            return sourceStr;  
        }  
        if (expr.length() == 0) {  
            return sourceStr;  
        }  
        int pos = sourceStr.indexOf(expr);  
        if (pos == -1) {  
            return sourceStr;  
        }  
        return sourceStr.substring(0, pos);  
    }  
   
    /** 
     * 如果字符串没有超过最长显示长度返回原字符串，否则从开头截取指定长度并加...返回。 
     *  
     * @param str 
     *            原字符串 
     * @param length 
     *            字符串最长显示的长度 
     * @return 转换后的字符串 
     */  
    public static String trimString(String str, int length) {  
        if (str == null) {  
            return "";  
        } else if (str.length() > length) {  
            return str.substring(0, length - 3) + "...";  
        } else {  
            return str;  
        }  
    }  
    
    /**
     * HTML字符转义
     * @see 对输入参数中的敏感字符进行过滤替换,防止用户利用JavaScript等方式输入恶意代码
     * @see String input = <img src='http://t1.baidu.com/it/fm=0&gp=0.jpg'/>
     * @see HtmlUtils.htmlEscape(input);         //from spring.jar
     * @see StringEscapeUtils.escapeHtml(input); //from commons-lang.jar
     * @see 尽管Spring和Apache都提供了字符转义的方法,但Apache的StringEscapeUtils功能要更强大一些
     * @see StringEscapeUtils提供了对HTML,Java,JavaScript,SQL,XML等字符的转义和反转义
     * @see 但二者在转义HTML字符时,都不会对单引号和空格进行转义,而本方法则提供了对它们的转义
     * @return String 过滤后的字符串
     */
    public static String htmlEscape(String input) {
        if(isEmpty(input)){
            return input;
        }
        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll(" ", "&nbsp;");
        input = input.replaceAll("'", "&#39;");   //IE暂不支持单引号的实体名称,而支持单引号的实体编号,故单引号转义成实体编号,其它字符转义成实体名称
        input = input.replaceAll("\"", "&quot;"); //双引号也需要转义，所以加一个斜线对其进行转义
        input = input.replaceAll("\n", "<br/>");  //不能把\n的过滤放在前面，因为还要对<和>过滤，这样就会导致<br/>失效了
        return input;
    }

	public static void main(String[] args) {
		System.out.println(isBlank("  \ff" + ""));
	}

}
