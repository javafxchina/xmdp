package net.javafxchina.xmdp.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射处理类
 * 
 * @author Victor
 * @version 1.0.0
 */
public class ReflectionUtil {
	/**
	 * 获取指定属性的值，可忽略大小写，也可忽略是否具有get方法，
	 * 但是推荐使用和原属性大小写一致且原属性的命名合乎java一般编程规范，且具有get方法， 这样能获得更好的性能。<br>
	 * 增加对带"."分隔的属性支持，可以逐层地获取嵌套对象的值，如果嵌套的对象类型是Map，则可以获取对应的key所对应的对象值。
	 * 
	 * 
	 * @param invokeObj
	 *            将调用的方法所属对象
	 * @param attr
	 *            需要调用get方法的属性
	 * @return 返回对应get方法得到的对象
	 * @throws ReflectException
	 *             若不存在对应属性或发生异常将抛出此异常
	 */
	@SuppressWarnings("rawtypes")
	public Object getInvoke(Object invokeObj, String attr) throws Exception {
		int indexOfDot = attr.indexOf(".");
		if (indexOfDot >= 0) {
			try {
				String attrTmp = attr.substring(0, indexOfDot);
				Object tmpRe = getInvoke(invokeObj, attrTmp);
				String attrTmpNext = attr.substring(indexOfDot + 1);
				return getInvoke(tmpRe, attrTmpNext);
			} catch (Exception e) {
				throw new Exception("获取属性：" + attr + "失败!" + e.getMessage());
			}

		} else {
			String methodName = getBeanMethodName("get", attr);
			try {

				Method method = invokeObj.getClass().getMethod(methodName);
				method.setAccessible(true);
				return method.invoke(invokeObj);
			} catch (Exception ex) {
				try {
					// 若名字大小写不对或方法找不到则查找合适的名字再做
					Map<String, Field> attrNamesMap = getFieldNames(invokeObj);
					if (attrNamesMap.containsKey(attr.toUpperCase())) {
						Field f = attrNamesMap.get(attr.toUpperCase());
						// String realAttrName = f.getName();
						f.setAccessible(true);
						return f.get(invokeObj);
					} else {// 若没有对应的属性或方法，如果是map则获取对应的key对应的value值
						if (invokeObj instanceof Map
								&& ((Map) invokeObj).containsKey(attr)) {
							return ((Map) invokeObj).get(attr);
						} else {
							throw new Exception("未找到对应名字的属性：" + attr);
						}
					}
				} catch (Exception exx) {
					throw new Exception("获取属性：" + attr + "失败!"
							+ exx.getMessage());
				}
			}
		}

	}

	// 获取所有的属性名的map<大写名称,实际名称>
	@SuppressWarnings("rawtypes")
	private Map<String, Field> getFieldNames(Object invokeObj) {

		Map<String, Field> fieldNamesMap = new HashMap<String, Field>();
		Class classOfObj = invokeObj.getClass();
		Field[] fields = null;
		while (classOfObj != null) {
			fields = classOfObj.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (!fields[i].getName().toUpperCase().equals("THIS$0"))
					fieldNamesMap.put(fields[i].getName().toUpperCase(),
							fields[i]);
			}
			classOfObj = classOfObj.getSuperclass();
		}
		return fieldNamesMap;
	}

	// 获取所有的属性名的map<大写名称,实际名称>
	@SuppressWarnings("rawtypes")
	private Map<String, Field> getFieldNames(Class classOfObj) {

		Map<String, Field> fieldNamesMap = new HashMap<String, Field>();
		Field[] fields = null;
		while (classOfObj != null) {
			fields = classOfObj.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (!fields[i].getName().toUpperCase().equals("THIS$0"))
					fieldNamesMap.put(fields[i].getName().toUpperCase(),
							fields[i]);
			}
			classOfObj = classOfObj.getSuperclass();
		}
		return fieldNamesMap;
	}

	/**
	 * 动态设置指定属性的值，可忽略大小写，也可忽略该属性是否有set方法。
	 * 但是推荐使用和原属性大小写一致且原属性的命名合乎java一般编程规范，且具有set方法， 这样能获得更好的性能。
	 * 
	 * @param invokeObj
	 *            将调用的方法所属对象
	 * @param attr
	 *            需要调用set方法的属性名
	 * @param setValue
	 *            需要设置的值
	 * @throws ReflectException
	 *             ReflectException 若不存在对应属性或发生异常将抛出此异常
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setInvoke(Object invokeObj, String attr, Object setValue)
			throws Exception {
		int indexOfDot = attr.indexOf(".");
		if (indexOfDot >= 0) {
			try {
				int lastIndexOfDot = attr.lastIndexOf(".");
				String attrTmp = attr.substring(0, lastIndexOfDot);
				Object tmpRe = getInvoke(invokeObj, attrTmp);
				String attrTmpNext = attr.substring(lastIndexOfDot + 1);
				setInvoke(tmpRe, attrTmpNext, setValue);
			} catch (Exception e) {
				throw new Exception("设置属性：" + attr + "失败!" + e.getMessage());
			}

		} else {
			String methodName = getBeanMethodName("set", attr);
			try {
				Method method = invokeObj.getClass().getMethod(methodName,
						setValue.getClass());
				method.setAccessible(true);
				method.invoke(invokeObj, setValue);
			} catch (Exception ex) {
				try {
					Method method = invokeObj.getClass().getMethod(methodName,
							getPrimitiveClass(setValue));
					method.invoke(invokeObj, setValue);
				} catch (Exception ex2) {
					try {
						// 若名字大小写不对或方法找不到则查找合适的名字再做
						Map<String, Field> attrNamesMap = getFieldNames(invokeObj);
						if (attrNamesMap.containsKey(attr.toUpperCase())) {

							Field f = attrNamesMap.get(attr.toUpperCase());
							// String realAttrName = f.getName();
							f.setAccessible(true);
							try {
								f.set(invokeObj, setValue);
							} catch (Exception e) {
								throw new Exception("设置属性：" + attr + "失败!"
										+ e.getMessage());
							}
						} else {
							if (invokeObj instanceof Map
									&& ((Map) invokeObj).containsKey(attr)) {
								((Map) invokeObj).put(attr, setValue);
							} else {
								throw new Exception("未找到对应名字的属性：" + attr);
							}
						}
					} catch (Exception e3) {
						throw new Exception("设置属性：" + attr + "失败!"
								+ e3.getMessage());
					}

				}
			}
		}
	}

	/**
	 * 获取指定名称的属性的类型
	 * 
	 * @param invokeObj
	 *            对应的对象
	 * @param fieldName
	 *            属性名,支持带"."多层嵌套
	 * @return 属性值
	 * @throws Exception
	 *             若不存在该属性或获取时发生异常则抛出
	 */
	@SuppressWarnings("rawtypes")
	public Class getFieldType(Object invokeObj, String fieldName)
			throws Exception {
		return getFieldType(invokeObj.getClass(), fieldName,invokeObj);
	}

	/**
	 * 获取指定类的指定名称的属性的类型
	 * 
	 * @param clax
	 *            对应的类
	 * @param fieldName
	 *            属性名,支持带"."多层嵌套
	 * @param invokeObj
	 *            如果要支持获取Map的key值对应的value值的类型，需要Map不能为空，且必须有对应的key-value对,如果Map只是最后一级的类型，则此参数可以为null
	 * @return 属性值
	 * @throws Exception
	 *             若不存在该属性或获取时发生异常则抛出
	 */
	@SuppressWarnings("rawtypes")
	public Class getFieldType(Class clax, String fieldName, Object invokeObj)
			throws Exception {
		int indexOfDot = fieldName.indexOf(".");
		if (indexOfDot >= 0) {// 如果带有.则说明要嵌套获取
			String attrTmp = fieldName.substring(0, indexOfDot);
			Object objNext=null;
			try{
				if(invokeObj!=null){
					objNext=getInvoke(invokeObj,attrTmp);
				}
			}catch (Exception e) {
				objNext=null;
			}
			Class tmpRe = getFieldType(clax, attrTmp,invokeObj);
			String attrTmpNext = fieldName.substring(indexOfDot + 1);
			return getFieldType(tmpRe, attrTmpNext,objNext);

		} else {
			Map<String, Field> attrNamesMap = getFieldNames(clax);
			if (attrNamesMap.containsKey(fieldName.toUpperCase())) {
				Field f = attrNamesMap.get(fieldName.toUpperCase());
				return f.getType();
			} else {
				// 如果没有对应的属性，则判断是不是map，如果是map，则需要使用invokeObj来协助获取下一层，否则无法继续
				if (invokeObj == null) {
					throw new Exception("获取" + fieldName + "类型失败!");
				} else {
					if (invokeObj instanceof Map
							&& ((Map) invokeObj).containsKey(fieldName)) {
						return ((Map) invokeObj).get(fieldName).getClass();
					} else {
						throw new Exception("获取" + fieldName + "类型失败!");
					}
				}

			}
		}

	}

	/**
	 * 利用反射进行类型转换，仅支持基本类型和字符串之间的转换,不支持基本类型之间的转换； 一般和
	 * {@link #getFieldType(Object, String)}结合起来使用
	 * 
	 * @param value
	 *            待转换值
	 * @param type
	 *            要转换成的结果类型
	 * @return 转换后的值
	 * @throws Exception
	 *             若转换失败则抛出异常
	 */
	@SuppressWarnings("rawtypes")
	public Object formatValue(Object value, Class type) throws Exception {
		boolean candoType = false;
		boolean candoValue = false;
		// 目的类型是否是字符串或基本类型及其包装类
		if (type.isPrimitive()) {
			candoType = true;
		} else if (type.equals(String.class)) {
			candoType = true;
		} else {
			if (type.equals(Integer.class) || type.equals(Short.class)
					|| type.equals(Long.class) || type.equals(Double.class)
					|| type.equals(Float.class) || type.equals(Boolean.class)
					|| type.equals(Byte.class) || type.equals(Character.class)) {
				candoType = true;
			}
		}
		// 源值类型是否是字符串或基本类型及其包装类
		if (value.getClass().isPrimitive()) {
			candoValue = true;
		} else if (value.getClass().equals(String.class)) {
			candoValue = true;
		} else {
			if (getPrimitiveClass(value) != null) {
				candoValue = true;
			}
		}
		if (candoValue && candoType) {
			Class tmpType = type;// tmpType均为转化后的包装类
			if (type.isPrimitive()) {
				tmpType = getWrapperClass(type);
			}
			// 将空串转换为基本类型则返回null
			if (getPrimitiveClass(type) != null
					&& value.getClass().equals(String.class)
					&& value.toString().trim().length() == 0) {
				return null;
			}
			if (value.getClass().equals(tmpType)) {// 类型一样则返回源值
				return value;
			} else {
				if (type.equals(String.class)) {// 转字符串则调用toString方法
					return value.toString();
				} else {
					if (value.getClass().equals(String.class)
							&& (type.equals(char.class) || type
									.equals(Character.class))) {
						if (value.toString().length() == 1) {
							return value.toString().charAt(0);
						} else {
							throw new Exception("不支持此类型的转换："
									+ value.getClass().getName() + "->"
									+ type.getName());
						}
					}

					// 对应的valueOf方法
					@SuppressWarnings("unchecked")
					Method valueOfMethod = tmpType.getDeclaredMethod("valueOf",
							value.getClass());
					return valueOfMethod.invoke(tmpType, value.toString());
				}
			}
		} else {
			throw new Exception("不支持此类型的转换：" + value.getClass().getName()
					+ "->" + type.getName());
		}

		/*
		 * if(type.equals(Integer.class)||type.equals(int.class)){ return
		 * Integer.valueOf(value.toString()); }else{ }
		 */
	}

	private String getBeanMethodName(String prefix, String attr) {
		String first = attr.substring(0, 1).toUpperCase();
		String rest = attr.substring(1);
		return prefix + first + rest;
	}

	@SuppressWarnings("rawtypes")
	private Class getPrimitiveClass(Object obj) {
		if (obj instanceof Integer) {
			return int.class;
		} else if (obj instanceof Short) {
			return short.class;
		} else if (obj instanceof Long) {
			return long.class;
		} else if (obj instanceof Double) {
			return double.class;
		} else if (obj instanceof Float) {
			return Float.class;
		} else if (obj instanceof Boolean) {
			return boolean.class;
		} else if (obj instanceof Byte) {
			return byte.class;
		} else if (obj instanceof Character) {
			return char.class;
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private Class getPrimitiveClass(Class clax) {
		if (clax.equals(Integer.class)) {
			return int.class;
		} else if (clax.equals(Short.class)) {
			return short.class;
		} else if (clax.equals(Long.class)) {
			return long.class;
		} else if (clax.equals(Double.class)) {
			return double.class;
		} else if (clax.equals(Float.class)) {
			return Float.class;
		} else if (clax.equals(Boolean.class)) {
			return boolean.class;
		} else if (clax.equals(Byte.class)) {
			return byte.class;
		} else if (clax.equals(Character.class)) {
			return char.class;
		} else {
			return null;
		}
	}

	// 获取基本类型的包装类
	@SuppressWarnings("rawtypes")
	private Class getWrapperClass(Class clax) {
		if (clax.equals(int.class)) {
			return Integer.class;
		} else if (clax.equals(short.class)) {
			return Short.class;
		} else if (clax.equals(long.class)) {
			return Long.class;
		} else if (clax.equals(double.class)) {
			return Double.class;
		} else if (clax.equals(float.class)) {
			return Float.class;
		} else if (clax.equals(byte.class)) {
			return Byte.class;
		} else if (clax.equals(char.class)) {
			return Character.class;
		} else if (clax.equals(boolean.class)) {
			return Boolean.class;
		} else {
			return null;
		}
	}
}
