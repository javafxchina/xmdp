package net.javafxchina.xmdp.addon.entrys;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.javafxchina.xmdp.core.util.JSONUtil;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.*;
import net.javafxchina.xmdp.ui.util.ResourceUtil;
import net.javafxchina.xmdp.ui.dk.IEntrySet;

/**
 * 入口配置服务，包括菜单、按钮、模块等
 * 
 * @author Victor
 *
 */
@Component
public class EntrySet implements IEntrySet {
	private Logger logger = LoggerFactory.getLogger(EntrySet.class);
	private LinkedHashMap<String, SideMenu> sideMenus = new LinkedHashMap<>();
	private LinkedHashMap<String, ModuleButton> moduleButtons = new LinkedHashMap<>();
	private LinkedHashMap<String, ModuleInfo> moduleInfos = new LinkedHashMap<>();

	@PostConstruct
	public void init() throws Exception {
		logger.info("开始解析EntrySet配置");
		try {
			String urlString = ResourceUtil.getFileUrlByRelativePath("cfg/ui/entrys/0_sideMenus.json");
			URL url=new URL(urlString);
			List<SideMenu> sms = JSONUtil.getList(url, SideMenu[].class);
			for (SideMenu sm : sms) {
				if (sideMenus.containsKey(sm.key)) {
					throw new Exception("重复的SideMenu key：" + sm.key);
				}
				sideMenus.put(sm.key, sm);
			}
		} catch (Exception e) {
			logger.error("解析sideMenus.json文件出错", e);
			throw e;
		}

		try {
			String urlString = ResourceUtil.getFileUrlByRelativePath("cfg/ui/entrys/1_moduleButtons.json");
			URL url=new URL(urlString);
			List<ModuleButton> mbs = JSONUtil.getList(url, ModuleButton[].class);
			for (ModuleButton mb : mbs) {
				if (moduleButtons.containsKey(mb.key)) {
					throw new Exception("重复的ModuleButton key：" + mb.key);
				}
				moduleButtons.put(mb.key, mb);
			}
		} catch (Exception e) {
			logger.error("解析moduleButtons.json文件出错", e);
			throw e;
		}

		try {
			String urlString = ResourceUtil.getFileUrlByRelativePath("cfg/ui/entrys/2_modules.json");
			URL url=new URL(urlString);
			List<ModuleInfo> mis = JSONUtil.getList(url, ModuleInfo[].class);
			for (ModuleInfo mi : mis) {
				if (moduleInfos.containsKey(mi.key)) {
					throw new Exception("重复的ModuleInfo key：" + mi.key);
				}
				moduleInfos.put(mi.key, mi);
			}
		} catch (Exception e) {
			logger.error("解析modules.json文件出错", e);
			throw e;
		}
		logger.info("解析EntrySet配置完成");
		check();
	}

	private void check() {
		for(ModuleButton mb:this.moduleButtons.values()) {
			String mkey=mb.moduleKey;
			ModuleInfo mi=this.moduleInfos.get(mkey);
			if(mi==null) {
				throw new RuntimeException("moduleKey:"+mkey+"对应的ModuleInfo配置为NULL");
			}
		}
	}

	public List<SideMenu> getSideMenus() {
		return new ArrayList<>(sideMenus.values());
	}

	public SideMenu getSideMenu(String key) {
		return sideMenus.get(key);
	}

	public List<ModuleButton> getModuleButtons() {
		return new ArrayList<>(moduleButtons.values());
	}

	public List<ModuleButton> getModuleButtonsByMenuKey(String menuKey) {
		List<ModuleButton> mbs = new ArrayList<>();
		for (ModuleButton mb : this.moduleButtons.values()) {
			if (mb.menuKey.equals(menuKey)) {
				mbs.add(mb);
			}
		}
		return mbs;
	}

	public ModuleButton getModuleButton(String key) {
		return moduleButtons.get(key);
	}

	public List<ModuleInfo> getModuleInfos() {
		return new ArrayList<>(moduleInfos.values());
	}

	public ModuleInfo getModuleInfo(String key) {
		return moduleInfos.get(key);
	}

}
