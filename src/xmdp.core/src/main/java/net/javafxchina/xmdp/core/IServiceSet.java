package net.javafxchina.xmdp.core;


/**
 * 服务集合接口
 * @author Victor
 *
 */
public interface IServiceSet {
	void init()throws Exception;
	/**
	 * 根据Class类型获取对应服务
	 * 
	 * @param type
	 *            Class类
	 * @return 对应服务
	 */
	<T> T getService(Class<T> type);
	/**
	 * 根据Bean名称获取对应服务
	 * 
	 * @param beanName
	 *            服务名称
	 * @return 对应服务
	 */
	Object getService(String beanName);
	
	
	@SuppressWarnings("rawtypes")
	Class getClass(String className) throws ClassNotFoundException;
	
}
