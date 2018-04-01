package net.javafxchina.xmdp.ui.dk;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.javafxchina.xmdp.core.XMDPContext;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.*;
import net.javafxchina.xmdp.ui.widgets.view.BaseView;
import net.javafxchina.xmdp.ui.widgets.view.ErrorInfoView;
import net.javafxchina.xmdp.ui.widgets.view.FXMLSpringView;
import net.javafxchina.xmdp.ui.widgets.view.WebBaseView;

/**
 * Tab页打开控制器 
 * 
 */
public class TabOpenManager {
	private Logger logger = LoggerFactory.getLogger(TabOpenManager.class);
	private TabPane desktopTabPane;

	public TabOpenManager(TabPane desktopTabPane) {
		super();
		this.desktopTabPane = desktopTabPane;
	}

	/**
	 * 在指定的选项卡中查找（根据id查找）并打开tab页，如果对应的tab页不存在则打开之，并设置为当前页
	 * 
	 * @param module
	 *            模块
	 */
	public void openTab(ModuleInfo module) {
		//外部程序直接执行返回，不再创建tab页
		if (module.type.equalsIgnoreCase(ModuleInfo.TYPE_EXTERN_JAR)) {
			try {
				JarRunner runner=new JarRunner(module);
				runner.run();
			} catch (Exception e) {
				logger.error("运行外部Jar文件失败",e);
			}
			return;
		} 
		//内部程序创建Tab
		ObservableList<Tab> tabs = desktopTabPane.getTabs();
		BaseView foundTab = null;
		for (Tab t : tabs) {
			if (module.key.equals(t.getId())) {
				foundTab = (BaseView) t;
				break;
			}
		}
		if (foundTab == null) {
			logger.debug("开始创建Tab页：" + module.key);
			foundTab = createTabByModule(module);
			if (foundTab == null) {
				foundTab = new ErrorInfoView(module, "未找到对应的页面");
			}
			desktopTabPane.getTabs().add(foundTab);

		} else {
			logger.debug("找到key为：" + module.key + "的Tab页");
		}
		desktopTabPane.getSelectionModel().select(foundTab);
	}

	/**
	 * 根据模块创建ui界面并嵌入到对应的tab页
	 * 
	 * @param ModuleInfo
	 *            module 模块
	 * @return 创建完毕的ui
	 */
	/**
	 * @param module
	 * @return
	 */
	private BaseView createTabByModule(ModuleInfo module) {
		BaseView tab = null;
		if (module.type.equalsIgnoreCase(ModuleInfo.TYPE_CLIENT)) {
			try {
				Class<?> moduleClass = XMDPContext.getInstance().getClass(module.className);
				Constructor<?> constructor = moduleClass.getConstructor(ModuleInfo.class);
				tab = (BaseView) constructor.newInstance(module);
				tab.init();
			} catch (Exception e) {
				logger.error("初始化模块出现错误,模块key:" + module.key, e);
				tab = new ErrorInfoView(module, "初始化模块出现错误,模块key:" + module.key + "\n" + e.getMessage());
				tab.init();
			}
		} else if (module.type.equalsIgnoreCase(ModuleInfo.TYPE_WEB)) {
			if (module.className.trim().equals("")) {
				// 当className为空时使用默认的WebView
				tab = new WebBaseView(module);
				tab.init();
			} else {
				try {
					Class<?> moduleClass = XMDPContext.getInstance().getClass(module.className);
					Constructor<?> constructor = moduleClass.getConstructor(ModuleInfo.class);
					tab = (BaseView) constructor.newInstance(module);
					tab.init();
				} catch (Exception e) {
					logger.error("初始化Web模块出现错误,模块key:" + module.key, e);
					tab = new ErrorInfoView(module, "初始化Web模块出现错误,模块key:" + module.key + "\n" + e.getMessage());
					tab.init();
				}
			}

		} else if (module.type.equalsIgnoreCase(ModuleInfo.TYPE_CLIENT_FXML)) {
			tab = new FXMLSpringView(module);
			tab.init();
		} else {
			logger.error("初始化模块出现错误,错误的模块类型:" + module.type);
			tab = new ErrorInfoView(module, "初始化模块出现错误,错误的模块类型:" + module.type + ",请注意检查配置");
			tab.init();
		}
		return tab;
	}
}
