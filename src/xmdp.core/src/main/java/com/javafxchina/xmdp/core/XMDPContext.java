package com.javafxchina.xmdp.core;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.stage.Stage;

/**
 * 工作上下文,其中包括框架运行的重要信息，如启动时间，服务集、属性等
 * @author Victor
 *
 */
public class XMDPContext {
	private static XMDPContext instance=new XMDPContext();
	private String startupTime;//启动时间
	private IServiceSet serviceSet;//服务集合
	private Map<String,Object>properties=new LinkedHashMap<String,Object>();
	private Stage rootStage;
	
	//This is a simple sigleton
	private XMDPContext() {
		super();
		this.startupTime=getCurrentFullTime();
	}
	public static XMDPContext getInstance(){
		return instance;
	}
	public String getStartupTime() {
		return startupTime;
	}
	/**
	 * 获取当前时间的完整字符串值
	 * 
	 * @return 当前时间的字符串值,格式为"yyyy-MM-dd HH:mm:ss.SSS"
	 */
	public static String getCurrentFullTime() {
		java.util.Date current = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String timeString = sdf.format(current);
		return timeString;
	}
	public void setServiceSet(IServiceSet serviceSet) {
		this.serviceSet = serviceSet;
	}
	/**
	 * 根据Class类型获取对应服务
	 * 
	 * @param type
	 *            Class类
	 * @return 对应服务
	 */
	public <T> T getService(Class<T> type) {
		return serviceSet.getService(type);
	}

	/**
	 * 根据Bean名称获取对应服务
	 * 
	 * @param beanName
	 *            服务名称
	 * @return 对应服务
	 */
	public  Object getService(String beanName) {
		return serviceSet.getService(beanName);
	}

	/**
	 * 根据类名获取对应服务
	 * 
	 * @param className
	 *            类名
	 * @return 对应服务
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public  Class getClass(String className) throws ClassNotFoundException {
		return serviceSet.getClass(className);
	}
	
//========属性操作
	/**
	 * 获取属性集
	 * @return
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}
	/**
	 * 根据属性Key获取对应的属性值
	 * @param propertyKey 属性Key
	 * @return 属性值，如果不存在则返回null
	 */
	public Object getProperty(String propertyKey) {
		return properties.get(propertyKey);
	}
	/**
	 * 设置属性
	 * @param propertyKey  属性的Key
	 * @param propertyValue 属性值
	 */
	public void setProperty(String propertyKey,Object propertyValue) {
		this.properties.put(propertyKey, propertyValue);
	}
	
	/**
	 * 设置桌面舞台
	 * @param stage
	 */
	public void setRootStage(Stage stage) {
		this.rootStage=stage;
	}
	/**
	 * 获取桌面根
	 * @return
	 */
	public Stage getRootStage(){
		return this.rootStage;
	}
}
