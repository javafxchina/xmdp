package net.javafxchina.xmdp.ui.dk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javafx.util.Callback;
import net.javafxchina.xmdp.core.XMDPContext;

/**
 * 控制器工厂，将JavaFX Controller的初始化及其依赖的初始化委托给Spring容器。
 * 
 * @author Victor
 *
 */
public class ControllerFactory implements Callback<Class<?>, Object> {
	private static Logger logger = LoggerFactory.getLogger(ControllerFactory.class);

	@Override
	public Object call(Class<?> param) {
		Object service = XMDPContext.getInstance().getService(param);
		logger.info("控件工厂call,查找服务" + param.getSimpleName() + ";查找结果为" + service);
		return service;
	}

}
