package net.javafxchina.xmdp.core.test;

import java.util.Map;

/**
 * 普通的测试对象，用于测试带"."的对象获取和设置
 * @version 1.0.0
 */
public class TestObj {
	private TestObj to;
	private int attrInt;
	private Map<String,Object> attrMap;
	public TestObj getTo() {
		return to;
	}
	public void setTo1(TestObj to) {
		this.to = to;
	}
	public int getAttrInt() {
		return attrInt;
	}
	public void setAttrInt2(int attrInt) {
		this.attrInt = attrInt;
	}
	public Map<String, Object> getAttrMap() {
		return attrMap;
	}
	public void setAttrMap(Map<String, Object> attrMap) {
		this.attrMap = attrMap;
	}
}
