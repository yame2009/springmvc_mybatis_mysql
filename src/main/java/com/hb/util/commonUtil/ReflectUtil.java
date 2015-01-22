package com.hb.util.commonUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具
 * 
 * @author 338342
 *
 */
public class ReflectUtil {
	/**
	 * 根据对象，返回一个class对象，用于获取方法
	 */
	public static Class<?> getClass(Object obj) {
		try {
			return Class.forName(obj.getClass().getName());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据对象，获取某个方法
	 * 
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param parameterTypes
	 *            该方法需传的参数类型，如果不需传参，则不传
	 */
	public static Method getMethod(Object obj, String methodName,
			Class<?>... parameterTypes) {
		try {
			Method method = getClass(obj).getDeclaredMethod(methodName,
					parameterTypes);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Method getMethod(Class<?> cls, String methodName,
			Class<?>... parameterTypes) {
		try {
			Method method = cls.getDeclaredMethod(methodName, parameterTypes);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 直接传入对象、方法名、参数，即可使用该对象的隐藏方法
	 * 
	 * @param obj
	 * @param methodName
	 * @param parameter
	 */
	public static Object invoke(Object obj, String methodName,
			Object... parameter) {
		Class<?>[] parameterTypes = new Class<?>[parameter.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = parameter[i].getClass();
		}
		try {
			return getMethod(obj, methodName, parameterTypes).invoke(obj,
					parameter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 直接传入类名、方法名、参数，即可使用该对象的隐藏静态方法
	 * 
	 * @param cls
	 * @param methodName
	 * @param parameter
	 */
	public static Object invoke(Class<?> cls, String methodName,
			Object... parameter) {
		Class<?>[] parameterTypes = new Class<?>[parameter.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = parameter[i].getClass();
		}
		try {
			return getMethod(cls, methodName, parameterTypes).invoke(null,
					parameter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断某个类里是否有某个方法
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static boolean isHaveSuchMethod(Class<?> clazz, String methodName) {
		Method[] methodArray = clazz.getMethods();
		boolean result = false;
		if (null != methodArray) {
			for (int i = 0; i < methodArray.length; i++) {
				if (methodArray[i].getName().equals(methodName)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public static void beanCopy(Object source, Object target) {

		if (null == source || null == target) {
			return;
		}

		Class<?> sourceClazz = source.getClass();
		Class<?> targetClazz = target.getClass();
		Field[] fields = targetClazz.getDeclaredFields(); // 取到所有类下的属性，也就是变量名
		Field field;

		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			String fieldName = field.getName();
			// 把属性的第一个字母处理成大写
			String stringLetter = fieldName.substring(0, 1).toUpperCase();
			// 取得setter方法名，比如setBbzt
			String setName = "set" + stringLetter + fieldName.substring(1);
			// 取得getter方法名
			String getName = "get" + stringLetter + fieldName.substring(1);
			// 真正取得get方法。
			Method setMethod = null;
			// 真正取得set方法
			Method sourceGetMethod = null;

			Class<?> fieldClass = field.getType();
			try {
				if (isHaveSuchMethod(targetClazz, setName)) {
					setMethod = targetClazz.getMethod(setName, fieldClass);
					if (isHaveSuchMethod(sourceClazz, getName)) {
						sourceGetMethod = sourceClazz.getMethod(getName);
					}
					Object sourceValue = sourceGetMethod.invoke(source);
					if (null != sourceValue) {
						setMethod.invoke(target, sourceValue);// 为其赋值
					}
				}
			} catch (Exception e) {

			}

		}
		return;
	}

	/**
	 * 将来源对象的值 ，赋给目标对象</br>
	 * 
	 * @param source
	 *            来源对象
	 * @param target
	 *            目标对象
	 * @param isCopyNull
	 *            如果source中的值为null时，是否将其赋给target对象
	 */
	public static void beanCopy(Object source, Object target, boolean isCopyNull) {

		if (null == source || null == target) {
			return;
		}

		Class<?> sourceClazz = source.getClass();
		Class<?> targetClazz = target.getClass();
		Field[] fields = targetClazz.getDeclaredFields(); // 取到所有类下的属性，也就是变量名
		Field field;

		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			String fieldName = field.getName();
			// 把属性的第一个字母处理成大写
			String stringLetter = fieldName.substring(0, 1).toUpperCase();
			// 取得setter方法名，比如setBbzt
			String setName = "set" + stringLetter + fieldName.substring(1);
			// 取得getter方法名
			String getName = "get" + stringLetter + fieldName.substring(1);
			// 真正取得get方法。
			Method setMethod = null;
			// 真正取得set方法
			Method sourceGetMethod = null;

			Class<?> fieldClass = field.getType();
			try {
				if (isHaveSuchMethod(targetClazz, setName)) {
					setMethod = targetClazz.getMethod(setName, fieldClass);
					if (isHaveSuchMethod(sourceClazz, getName)) {
						sourceGetMethod = sourceClazz.getMethod(getName);
					}
					Object sourceValue = sourceGetMethod.invoke(source);
					if (null != sourceValue) {
						setMethod.invoke(target, sourceValue);// 为其赋值
					} else {
						if (isCopyNull) {
							setMethod.invoke(target, sourceValue);
						}
					}
				}
			} catch (Exception e) {

			}
		}
		return;
	}

}
