package com.javafxchina.xmdp.addon;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import com.javafxchina.xmdp.core.IServiceSet;
import com.javafxchina.xmdp.core.XMDPContext;

/**
 * Spring启动器
 * 
 * @author Victor
 *
 */
public class AddonsLauncher implements IServiceSet {
	private static Logger logger = LoggerFactory.getLogger(AddonsLauncher.class);
	private boolean isInitialized = false;
	private FileSystemXmlApplicationContext applicationContext;
	private AddonsManager addOnManager;
	private ClassLoader classloader;
	private static AddonsLauncher instance = new AddonsLauncher();
	private XMDPContext context;

	// This is a simple sigleton
	private AddonsLauncher() {
		context = XMDPContext.getInstance();
		context.setServiceSet(this);
	}

	public static AddonsLauncher getInstance() {
		return instance;
	}

	/**
	 * 初始化Spring环境
	 * 
	 * @param isDebug
	 *            是否调试模式
	 * @throws Exception
	 */
	synchronized public void init() throws Exception {

		if (isInitialized) {
			logger.warn("重复初始化SpringLauncher");
			return;
		}

		String localPath = System.getProperty("user.dir");
		logger.info("localPath=" + localPath);

		addOnManager = new AddonsManager();
		String[] local = { localPath + "\\cfg\\xmdp.xml", localPath + "\\addons\\addon.xml" };
		applicationContext = new FileSystemXmlApplicationContext();
		classloader = addOnManager.discoverAddonJars();
		((DefaultResourceLoader) applicationContext).setClassLoader(classloader);

		logger.info("=============开始加载核心配置=============");
		applicationContext.setConfigLocations(local);

		applicationContext.refresh();
		// 加载外部插件
		logger.info("=============开始加载外部插件=============");
		addOnManager.loadBean(classloader);
		testAddon();
		logger.info("=============核心配置加载完毕=============");
		isInitialized = true;

	}

	/**
	 * 测试外部插件机制是否运行正常
	 */
	private void testAddon() throws Exception {
		try {
			Object obj = getService("testAddon");
			tryInvoke(obj, "doit");
			logger.info("插件机制工作正常，测试：" + obj.getClass().getName());

			obj = getService("testAnnotationAddon");
			tryInvoke(obj, "doit");
			logger.info("注解插件机制工作正常，测试：" + obj.getClass().getName());
		} catch (Exception e) {
			logger.error("插件机制工作失败", e);
			throw e;
		}

	}

	/**
	 * 尝试调用指定对象的制定方法
	 */
	private void tryInvoke(Object obj, String methodName) throws Exception {

		Class<?> paramTypes[] = new Class[0];
		Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
		Object paramValues[] = new Object[0];
		method.invoke(obj, paramValues);

	}

	@SuppressWarnings("rawtypes")
	public Class getClass(String className) throws ClassNotFoundException {
		Class claz = classloader.loadClass(className);
		return claz;
	}

	/**
	 * 根据类来获取对应服务
	 * 
	 * @param type
	 *            类
	 * @return 对应服务
	 */
	public <T> T getService(Class<T> type) {
		try {
			return applicationContext.getBean(type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getService(String beanName) {
		try {
			return applicationContext.getBean(beanName);
		} catch (Exception e) {
			return null;
		}
	}

	public void registerBeanDefinition(String beanId, BeanDefinition bdef) {
		DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		fty.registerBeanDefinition(beanId, bdef);
	}


	public boolean isInitialized() {
		return isInitialized;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
