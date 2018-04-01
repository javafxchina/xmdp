package net.javafxchina.xmdp.ui.dk;

import java.util.List;

import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.*;


/**
 *入口配置管理服务，包括侧边菜单、按钮、模块信息等
 * @author Victor
 *
 */
public interface IEntrySet {
	/**
	 * 获取所有的侧边菜单按钮信息
	 * @return 侧边栏按钮配置，如果没有相关信息则返回空的List
	 */
	List<SideMenu> getSideMenus();
	/**
	 * 获取指定的侧边菜单按钮信息
	 * @param key 按钮key值
	 * @return key对应的侧边菜单按钮信息，如果没有关联信息则返回null
	 */
	SideMenu getSideMenu(String key);
	
	/**
	 * 获取所有的模块按钮信息
	 * @return 模块按钮信息配置列表，如果没有相关信息则返回空的List
	 */
	List<ModuleButton>getModuleButtons();
	/**
	 * 获取指定的侧边按钮关联的模块按钮信息
	 * @param menuKey 侧边按钮key，点击对应的侧边按钮后，会显示出关联的按钮清单
	 * @return 关联的模块按钮信息,如果没有相关信息则返回空的List
	 */
	List<ModuleButton>getModuleButtonsByMenuKey(String menuKey);
	/**
	 * 获取指定的模块按钮信息
	 * @param key 模块按钮key
	 * @return 关联的模块按钮信息
	 */
	ModuleButton getModuleButton(String key);
	
	/**
	 * 获取模块信息
	 * @return 模块信息清单，如果没有相关信息则返回空的List
	 */
	List<ModuleInfo>getModuleInfos();
	/**
	 * 获取指定的模块信息
	 * @param key 模块信息的key
	 * @return 对应的模块信息，不存在则返回null
	 */
	ModuleInfo getModuleInfo(String key);
}
