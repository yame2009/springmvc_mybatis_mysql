package com.hb.util.commonUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
     * 同类型对象值复制，将other的同名同类型属性值复制到target上
     * 
     * @param target
     *            将被更新值
     * @param other
     *            模板值
     * @param includeSuperClass
     *            是否包括父类字段，直到Object
     * @param replaceWhenNull
     *            当模板值为null时，是否替换
     * @param excludeFieldArray
     *            排除的字段
     */
    public static <T> void copyObject(T target, T other,
            boolean includeSuperClass, boolean replaceWhenNull,
            String... excludeFieldArray)
    {
        List<Field> fields = ReflectUtil.getFields(target.getClass(),
                includeSuperClass);
        List<String> exculdeFields = new ArrayList<String>();
        if (null != excludeFieldArray)
        {
            for (int i = 0; i < excludeFieldArray.length; i++)
            {
                exculdeFields.add(excludeFieldArray[i]);
            }
        }
        for (int i = 0; i < fields.size(); i++)
        {
            Field field = fields.get(i);
            String name = field.getName();
            if (exculdeFields.contains(name))
            {
                continue;
            }
            boolean isAccsessible = field.isAccessible();
            try
            {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                Object otherValue = field.get(other);
                Object castImpl = castImpl(otherValue, fieldType);
                if (null == castImpl)
                {
                    if (replaceWhenNull)
                    {
                        field.set(target, castImpl);
                    }
                }
                else
                {
                    field.set(target, castImpl);
                }
            }
            catch (Exception e)
            {
                continue;
            }
            finally
            {
                field.setAccessible(isAccsessible);
            }

        }
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
	public static void copyObject(Object source, Object target, boolean isCopyNull) {

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
	
	 /**
     * 将other的同名同类型属性值设置到target上
     * 
     * @param target
     *            将被更新值
     * @param other
     *            模板值
     * @param includeSuperClass
     *            是否包括父类字段，直到Object
     * @param replaceWhenNull
     *            当模板值为null时，是否替换
     * @param excludeFieldArray
     *            排除的字段
     */
    public static void updateObjFromOther(Object target, Object other,
            boolean includeSuperClass, boolean replaceWhenNull,
            String... excludeFieldArray)
    {
        List<Field> fields = getFields(target.getClass(),
                includeSuperClass);
        Map<String, Field> otherFields = fields2Map(getFields(other.getClass(), includeSuperClass));
        List<String> exculdeFields = new ArrayList<String>();
        if (null != excludeFieldArray)
        {
            for (int i = 0; i < excludeFieldArray.length; i++)
            {
                exculdeFields.add(excludeFieldArray[i]);
            }
        }
        for (int i = 0; i < fields.size(); i++)
        {
            Field field = fields.get(i);
            String name = field.getName();
            if (exculdeFields.contains(name))
            {
                continue;
            }
            Field otherField = otherFields.get(name);
            if (null == otherField)
            {
                continue;
            }
            boolean a1 = field.isAccessible();
            boolean a2 = otherField.isAccessible();
            try
            {
                field.setAccessible(true);
                otherField.setAccessible(true);

                Class<?> fieldType = field.getType();
                Object otherValue = otherField.get(other);
                Object castImpl = castImpl(otherValue, fieldType);
                if (null == castImpl)
                {
                    if (replaceWhenNull)
                    {
                        field.set(target, castImpl);
                    }
                }
                else
                {
                    field.set(target, castImpl);
                }
            }
            catch (Exception e)
            {
                continue;
            }
            finally
            {
                field.setAccessible(a1);
                otherField.setAccessible(a2);
            }

        }
    }
    
    /**
     * 将字段集合转换为Map,Key为字段名
     * 
     * @param fields
     * @return
     */
    public static Map<String, Field> fields2Map(Collection<Field> fields)
    {
        Map<String, Field> result = new HashMap<String, Field>();
        if (null == fields)
        {
            return result;
        }
        for (Field f : fields)
        {
            if (null == f)
            {
                continue;
            }
            String name = f.getName();
            result.put(name, f);
        }
        return result;
    }
	
	 /**
     * 获取注解
     * 
     * @param beanType
     * @param fieldName
     * @param annoType
     * @return
     */
    public static <T extends Annotation> T getFieldAnnotation(
            Class<?> beanType, String fieldName, Class<T> annoType)
    {
        try
        {
            Field declaredField = beanType.getDeclaredField(fieldName);
            T annotation = declaredField.getAnnotation(annoType);
            return annotation;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 获取指定类型的字段
     * 
     * @param beanType
     *            类型
     * @param includeSuperClass
     *            是否获取父类至Object的所有字段（除Object）
     * @return
     */
    public static List<Field> getFields(Class<?> beanType,
            boolean includeSuperClass)
    {
        List<Field> result = new ArrayList<Field>();
        try
        {
            while (!Object.class.equals(beanType) && null != beanType)
            {
                Field[] declaredFields = beanType.getDeclaredFields();
                if (null != declaredFields)
                {
                    for (int i = 0; i < declaredFields.length; i++)
                    {
                        Field field = declaredFields[i];
                        result.add(field);
                    }
                }
                if (includeSuperClass)
                {
                    beanType = beanType.getSuperclass();
                }
                else
                {
                    break;
                }
            }
        }
        catch (Exception e)
        {
            return result;
        }
        return result;
    }

    /**
     * 获取字段值
     * 
     * @param instance
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object instance, String fieldName)
    {
        Object result = null;
        try
        {
            Field declaredField = instance.getClass().getDeclaredField(
                    fieldName);
            boolean isAccess = declaredField.isAccessible();
            declaredField.setAccessible(true);
            result = declaredField.get(instance);
            declaredField.setAccessible(isAccess);
        }
        catch (Exception e)
        {
            return result;
        }
        return result;
    }

    /**
     * 设置字段值
     * 
     * @param instance
     * @param fieldName
     * @param obj
     */
    public static void setFieldValue(Object instance, String fieldName,
            Object obj)
    {
        try
        {
            Field declaredField = instance.getClass().getDeclaredField(
                    fieldName);
            Class<?> type = declaredField.getType();
            boolean isAccess = declaredField.isAccessible();
            declaredField.setAccessible(true);
            Object cast = castImpl(obj, type);
            declaredField.set(instance, cast);
            declaredField.setAccessible(isAccess);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
    }
    
    /**
     * 类型转换实现
     * 
     * @param obj
     * @param type
     * @return
     */
    public static Object castImpl(Object obj, Class<?> type)
    {
        Object cast = null;
        if (null != obj)
        {
            String string = obj.toString();
            if (!"".equals(string))
            {
                if (type.isAssignableFrom(Integer.class))
                {
                    cast = Integer.valueOf(string);
                }
                else if (type.isAssignableFrom(Long.class))
                {
                    cast = Long.valueOf(string);
                }
                else if (type.isAssignableFrom(Short.class))
                {
                    cast = Short.valueOf(string);
                }
                else if (type.isAssignableFrom(Float.class))
                {
                    cast = Float.valueOf(string);
                }
                else if (type.isAssignableFrom(Double.class))
                {
                    cast = Double.valueOf(string);
                }
                else if (type.isAssignableFrom(Boolean.class))
                {
                    cast = Boolean.valueOf(string);
                }
                else if (type.isAssignableFrom(String.class))
                {
                    cast = string;
                }
                else
                {
                    cast = type.cast(obj);
                }
            }
        }
        return cast;
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
     * Object转换为Map对象
     * 
     * @param source
     * @return
     */
    public static Map<String, Object> objectToMap(Object source)
    {
        Map<String, Object> retMap = new HashMap<String, Object>();

        try
        {
            Method[] methods = source.getClass().getMethods();
            for (Method method : methods)
            {
                String methodName = method.getName();
                if (methodName.startsWith("get"))
                {
                    int modifier = method.getModifiers();
                    if (Modifier.isPublic(modifier)
                            || Modifier.isProtected(modifier))
                    {
                        String key = methodName.substring(3, 4).toLowerCase()
                                + methodName.substring(4);
                        Object value = method.invoke(source, new Object[0]);
                        if (key != null && value != null)
                        {
                            retMap.put(key, value);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return retMap;
    }
    
    /**
     * Map 转Object
     * @param map   数据源
     * @param bean  目标源
     */
    public static void mapToObject(Map<String, Object> map, Object bean)
    {
        String propertyName = "";
        Object value = null;
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext())
        {
            propertyName = it.next();
            value = map.get(propertyName);
            try
            {
                setPropertyValue(bean, propertyName, value);
            }
            catch (Exception e)
            {
            }
        }
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
	
	 /**
     * 属性复制
     * 
     * @param source
     *            源Bean对象
     * @param target
     *            目标Bean对象
     * @param nullValueCopy
     *            是否复制null值
     */
    public static void copyProperties(Object source, Object target,
            boolean nullValueCopy)
    {
        String methodName = "";
        Method[] methods = source.getClass().getMethods();
        for (Method method : methods)
        {
            methodName = method.getName();
            if (methodName.startsWith("get") || methodName.startsWith("is"))
            {
                try
                {
                    Object value = method.invoke(source, new Object[0]);
                    // 如果value为null, 不允许null值复制,则不进行复制
                    if (value == null && !nullValueCopy)
                    {
                        continue;
                    }
                    String setMethodName = methodName.replaceFirst("(get|is)",
                            "set");
                    Method setMethod = target.getClass().getMethod(
                            setMethodName, method.getReturnType());
                    setMethod.invoke(target, value);
                }
                catch (Exception e)
                {
                    // do nothing
                }
            }
        }
    }

    /**
     * 属性复制
     * 
     * @param source
     *            源Bean对象
     * @param target
     *            目标Bean对象
     */
    public static void copyProperties(Object source, Object target)
    {
        copyProperties(source, target, true);
    }

    public static void setPropertyValue(Object bean, String propertyName,
            Object value) throws Exception
    {
        try
        {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
                    propertyName, bean.getClass());
            Method writeMethod = propertyDescriptor.getWriteMethod();

            if (writeMethod == null)
            {
                throw new IntrospectionException("No write method for "
                        + bean.getClass() + "." + propertyName);
            }

            Class<?> typeNeeded = writeMethod.getParameterTypes()[0];

            if ((value == null || "".equals(value)) && typeNeeded.isPrimitive())
            {
                if (typeNeeded.equals(Integer.TYPE))
                {
                    value = new Integer(0);
                }
                else if (typeNeeded == Long.TYPE)
                {
                    value = new Long(0L);
                }
                else if (typeNeeded.equals(Float.TYPE))
                {
                    value = new Float(0F);
                }
                else if (typeNeeded.equals(Double.TYPE))
                {
                    value = new Double(0D);
                }
                else if (typeNeeded.equals(Boolean.TYPE))
                {
                    value = new Boolean(false);
                }
            }
            else if (typeNeeded.isPrimitive())
            {
                if (typeNeeded.equals(Integer.TYPE))
                {
                    value = Integer.parseInt(value.toString());
                }
                else if (typeNeeded == Long.TYPE)
                {
                    value = Long.parseLong(value.toString());
                }
                else if (typeNeeded.equals(Float.TYPE))
                {
                    value = Float.parseFloat(value.toString());
                }
                else if (typeNeeded.equals(Double.TYPE))
                {
                    value = Double.parseDouble(value.toString());
                }
                else if (typeNeeded.equals(Boolean.TYPE))
                {
                    value = new Boolean(value.toString());
                }
            }
            writeMethod.invoke(bean, new Object[]
            { value });
        }
        catch (Exception e)
        {
            throw new IntrospectionException("property:" + bean.getClass()
                    + "." + propertyName + "  " + e.getMessage());
        }
    }

    public static Object getPropertyValue(Object bean, String propertyName)
    {
        try
        {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
                    propertyName, bean.getClass());
            Method readMethod = propertyDescriptor.getReadMethod();

            if (readMethod == null)
            {
                throw new RuntimeException("No read method for "
                        + bean.getClass() + "." + propertyName);
            }

            return readMethod.invoke(bean, new Object[] {});
        }
        catch (Exception e)
        {
            // do nothing
        }
        return null;
    }

}
