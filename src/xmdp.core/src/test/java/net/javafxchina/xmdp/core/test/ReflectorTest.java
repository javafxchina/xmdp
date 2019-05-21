package net.javafxchina.xmdp.core.test;

import java.util.HashMap;
import java.util.Map;

import net.javafxchina.xmdp.core.util.ReflectionUtil;



/**
 * 用来测试反射类，主要测试获取属性类型和类型转换方法
 * 
 * @author CaiChaowei
 * @version 1.0.0
 */
public class ReflectorTest {
	int testInt = 1;
	Integer testInteger = 2;
	String testString;
	long testl = 2;
	Long testLong = 3l;
	char testc = 'a';
	Character testChar = 'a';
	float testf;
	Float testFloat;
	double testd;
	Double testDouble;
	short tests;
	Short testShort;
	byte testb;
	Byte testByte;
	boolean testboolean;
	Boolean testBool;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		ReflectorTest rTest = new ReflectorTest();
		ReflectionUtil reflector = new ReflectionUtil();
		try {
			System.out.println("获取");
			Class clax = reflector.getFieldType(rTest, "testint");
			System.out.println(clax.getName());
			System.out.println(clax.isPrimitive());
			System.out.println(Integer.class.equals(clax));
			
			TestObj t11=new TestObj();
			TestObj t22=new TestObj();
			t11.setTo1(t22);
			t22.setAttrInt2(123);
			t22.setTo1(t11);
			Class clax2 = reflector.getFieldType(t11, "attrInt");
			Map attrMap1=new HashMap<String,Object>();
			attrMap1.put("ext1", "");
			attrMap1.put("ext2", null);
			attrMap1.put("ext3", 123);
			attrMap1.put("ext4", t22);
			t22.setAttrMap(attrMap1);
			System.out.println(clax2.getName());
			clax2 = reflector.getFieldType(t11, "to.attrInt");
			System.out.println(clax2.getName());
			clax2 = reflector.getFieldType(t11, "to.attrMap");
			System.out.println(clax2.getName());
			System.out.println("to.to");
			clax2 = reflector.getFieldType(t11, "to.to.to");
			System.out.println(clax2.getName());
			clax2 = reflector.getFieldType(t11, "to.to.to.attrMap");
			System.out.println(clax2.getName());
			clax2 = reflector.getFieldType(t11, "to.to.to.attrMap.ext1");
			System.out.println(clax2.getName());
//			clax2 = reflector.getFieldType(t11, "to.to.to.to.to.to.to.attrMap.ext2");
//			System.out.println(clax2.getName());
			clax2 = reflector.getFieldType(t11, "to.to.to.attrMap.ext3");
			System.out.println(clax2.getName());
			clax2 = reflector.getFieldType(t11, "to.to.to.attrMap.ext4.attrMap");
			System.out.println(clax2.getName());
			clax2 = reflector.getFieldType(t11, "to.to.to.attrMap.ext4.attrMap.ext3");
			System.out.println(clax2.getName());
			
			System.out.println("");
			System.out.println("整数转字符串");
			reflector.setInvoke(rTest, "testString", reflector.formatValue(100,
					String.class));
			System.out.println(rTest.testString);

			System.out.println("字符串转整数");
			reflector.setInvoke(rTest, "testInt", reflector.formatValue("100",
					int.class));
			System.out.println(rTest.testInt);

			System.out.println("整数转整数");
			reflector.setInvoke(rTest, "testInteger", reflector.formatValue(
					100, int.class));
			System.out.println(rTest.testInteger);

			System.out.println("长整数转字符串");
			reflector.setInvoke(rTest, "testString", reflector.formatValue(
					999L, String.class));
			System.out.println(rTest.testString);

			System.out.println("字符串转长整数");
			reflector.setInvoke(rTest, "testLong", reflector.formatValue("999",
					Long.class));
			System.out.println(rTest.testLong);

			System.out.println("长整数转长整数");
			reflector.setInvoke(rTest, "testLong", reflector.formatValue(999L,
					Long.class));
			System.out.println(rTest.testLong);

			System.out.println("浮点转字符串");
			reflector.setInvoke(rTest, "testString", reflector.formatValue(
					999.99f, String.class));
			System.out.println(rTest.testString);

			System.out.println("字符串转浮点数");
			reflector.setInvoke(rTest, "testFloat", reflector.formatValue(
					"999.99", Float.class));
			System.out.println(rTest.testFloat);

			System.out.println("浮点数转浮点数");
			reflector.setInvoke(rTest, "testFloat", reflector.formatValue(
					999.9f, Float.class));
			System.out.println(rTest.testFloat);

			System.out.println("双精度转字符串");
			reflector.setInvoke(rTest, "testString", reflector.formatValue(
					999.99, String.class));
			System.out.println(rTest.testString);

			System.out.println("字符串转双精度");
			reflector.setInvoke(rTest, "testDouble", reflector.formatValue(
					"999.99", Double.class));
			System.out.println(rTest.testDouble);

			System.out.println("双精度转双精度");
			reflector.setInvoke(rTest, "testDouble", reflector.formatValue(
					999.9, Double.class));
			System.out.println(rTest.testDouble);

			System.out.println("字符转字符串");
			reflector.setInvoke(rTest, "testString", reflector.formatValue('c',
					String.class));
			System.out.println(rTest.testString);

			System.out.println("字符串转字符");
			reflector.setInvoke(rTest, "testChar", reflector.formatValue("c",
					Character.class));
			System.out.println(rTest.testChar);

			System.out.println("字符转字符");
			reflector.setInvoke(rTest, "testChar", reflector.formatValue('c',
					Character.class));
			System.out.println(rTest.testChar);

			System.out.println("short转字符串");
			reflector.setInvoke(rTest, "testString", reflector.formatValue(
					(Short) (short) 111, String.class));
			System.out.println(rTest.testString);

			System.out.println("字符串转short");
			reflector.setInvoke(rTest, "testShort", reflector.formatValue(
					"111", Short.class));
			System.out.println(rTest.testShort);

			System.out.println("short转short");
			reflector.setInvoke(rTest, "testShort", reflector.formatValue(
					(Short) (short) 111, Short.class));
			System.out.println(rTest.testShort);

			System.out.println("byte转字符串");
			reflector.setInvoke(rTest, "testString", reflector.formatValue(
					(Byte) (byte) 123, String.class));
			System.out.println(rTest.testString);

			System.out.println("字符串转byte");
			reflector.setInvoke(rTest, "testByte", reflector.formatValue("123",
					Byte.class));
			System.out.println(rTest.testByte);

			System.out.println("byte转byte");
			reflector.setInvoke(rTest, "testByte", reflector.formatValue(
					(Byte) (byte) 123, Byte.class));
			System.out.println(rTest.testByte);

			System.out.println("boolean转字符串");
			reflector.setInvoke(rTest, "testString", reflector.formatValue(
					true, String.class));
			System.out.println(rTest.testString);

			System.out.println("字符串转boolean");
			reflector.setInvoke(rTest, "testboolean", reflector.formatValue(
					"true", boolean.class));
			System.out.println(rTest.testboolean);

			System.out.println("boolean转boolean");
			reflector.setInvoke(rTest, "testboolean", reflector.formatValue(
					true, boolean.class));
			System.out.println(rTest.testboolean);

			System.out.println("测试Map");
			Map attrMap=new HashMap<String,Object>();
			attrMap.put("test", "value");
			System.out.println(reflector.getInvoke(attrMap, "test"));
			reflector.setInvoke(attrMap, "test", "valueSetted");
			System.out.println(reflector.getInvoke(attrMap, "test"));
			
			System.out.println("测试多层带.");
			TestObj t1=new TestObj();
			TestObj t2=new TestObj();
			t1.setTo1(t2);
			t2.setAttrInt2(123);
			System.out.println(reflector.getInvoke(t1, "to.attrInt"));
			reflector.setInvoke(t1, "to.attrInt", 456);
			System.out.println(reflector.getInvoke(t1, "to.attrInt"));
			
			System.out.println("测试多层带.和Map");
			attrMap.put("key1", "value1");
			attrMap.put("key2", t2);
			attrMap.put("key3", t1);
			t1.setAttrMap(attrMap);
			t2.setAttrMap(attrMap);
			System.out.println(reflector.getInvoke(t1, "attrMap.test"));
			System.out.println(reflector.getInvoke(t1, "attrMap.key1"));
			System.out.println(reflector.getInvoke(t1, "attrMap.key2.attrInt"));
			System.out.println(reflector.getInvoke(t1, "attrMap.key3.to.attrInt"));
			reflector.setInvoke(t1, "attrMap.key3.to.attrMap.key1","value1Setted");
			System.out.println(attrMap.get("key1"));
			System.out.println(reflector.getInvoke(t1, "attrMap.key3.to.attrMap.key1"));
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
