package net.javafxchina.xmdp.ui.dk;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import net.javafxchina.xmdp.core.XMDPParams;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.*;
import net.javafxchina.xmdp.ui.util.ResourceUtil;
import net.javafxchina.xmdp.ui.widgets.RibbonHome;

/**
 * 入口初始化工厂，创建菜单和按钮
 * @author Victor
 *
 */
@Component
public class XMDPEntryFactory {
	private static Logger logger = LoggerFactory.getLogger(XMDPEntryFactory.class);
	private double menuHeight = 40;// 侧边菜单每行的高度
	private double menuWidth = 35;// 侧边菜单的宽度
	private double buttonRowHeight = 180;// 按钮组每行的高度
	private double buttonWidth = 60;// 按钮图标的大小
	private String defaultPageButtonIcon="defaultPageButtonIcon.png";//默认按钮图标
	private RibbonHome pageArea;
	
	@Autowired
	@Qualifier("xmdpParams")
	private XMDPParams xmdpParams;
	/**
	 * 扩展点：菜单和按钮
	 */
	@Autowired(required = false)
	@Qualifier("entrySet")
	private IEntrySet entrySet;
	
	private TabOpenManager tabOpenManager;
	
	
	
	@PostConstruct
	public void init() throws Exception {
		buttonRowHeight =xmdpParams.getIntParam("xmdp.buttongroup_row_height");
		buttonWidth =xmdpParams.getIntParam("xmdp.buttonimage_width");
		logger.debug("XMDPEntryFactory初始化成功");
	}
	/**
	 * 创建主页，配置侧边栏菜单和菜单按钮
	 * @param desktopTabPane 点击菜单按钮会打开对应的选项卡，此参数为对应的选项卡面板
	 * @return 配置完毕的面板
	 */
	public Pane createEntryPane(TabPane desktopTabPane) {
		if(entrySet==null) {
			logger.warn("EntrySet服务为空，请确认是否配置");
			System.err.println("EntrySet服务为空，请确认是否配置");
			return null;
		}
		pageArea = new RibbonHome();
		tabOpenManager = new TabOpenManager(desktopTabPane);
		
		List<SideMenu> sideMenus = entrySet.getSideMenus();
		for (SideMenu sd : sideMenus) {
			ImageView iv=ResourceUtil.createImageView(sd.iconpath,menuWidth,menuHeight);
			Button button = new Button(sd.showName, iv);
			button.setContentDisplay(ContentDisplay.TOP);
			pageArea.addMenuButton(sd.key, button);

			List<ModuleButton> moduleButtons = entrySet.getModuleButtonsByMenuKey(sd.key);
			initPageButtons(sd.key, moduleButtons);
		}
		pageArea.showFirstPage();
		return pageArea;
	}
	/**
	 * 配置(与侧边菜单相关的)各个桌面页的菜单按钮,参考样式：RibbonHome.css
	 */
	private void initPageButtons(String menuKey, List<ModuleButton> moduleButtons) {
		for (ModuleButton mb : moduleButtons) {
			//按钮带有图片时，以图片大小作为按钮大小
			ImageView iv=ResourceUtil.createImageView(mb.iconpath,buttonWidth,buttonRowHeight,defaultPageButtonIcon);
			Button button = new Button(mb.showName, iv);
			button.setId(mb.key);
			
			ModuleInfo moduleInfo=entrySet.getModuleInfo(mb.moduleKey);
			if(moduleInfo==null) {
				logger.warn(mb.moduleKey+"未找到ModuleInfo配置信息");
				System.err.println(mb.moduleKey+"未找到ModuleInfo配置信息");
			}
			button.setUserData(moduleInfo);
//			button.setTextOverrun(OverrunStyle.CLIP);
			if(mb.showName.length()>8) {//超长则添加tooltip
				button.setTooltip(new Tooltip(mb.showName));
			}
			pageArea.addPageButton(menuKey,mb.group,mb.key,button);
			EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
						if (mouseEvent.getClickCount() == 2) {// 双击打开tab页
							Button sourceBtn = (Button) mouseEvent.getSource();
							ModuleInfo module = (ModuleInfo) sourceBtn.getUserData();
							if(module!=null) {
								logger.debug("Selected Module Button: " + module.showName);
								tabOpenManager.openTab(module);
							}else {
								logger.error("ModuleInfo为NULL");
							}
							
						}
					}

				}

			};
			button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		}

	}
}
