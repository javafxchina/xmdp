package net.javafxchina.xmdp.ui.widgets;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import net.javafxchina.xmdp.ui.util.ResourceUtil;

/**
 * Ribbon风格的主页，包括一个纵向的菜单栏和若干个首页菜单按钮
 * @author Victor
 *
 */
public class RibbonHome extends BorderPane {
	private  Logger logger = LoggerFactory.getLogger(RibbonHome.class);
	private double menuWidht=40;//左侧菜单栏的宽度
	private double rowHeight = 100;// 按钮组每行的高度的默认值
	private double buttonWidth = 100;// 按钮图标的大小的默认值
	private int maxColumns = 10;// 按钮组最大列数的默认值
	private double buttonGap = 5;// 按钮组空隙的默认值
	private RibbonMenu leftMenu;
	private LinkedHashMap<String, Button> buttons = new LinkedHashMap<>();// 所有的菜单按钮集合

	// 右侧的主页内容，默认摆放按钮，也可以为自定义内容，这些内容根据按钮存放在不同的key之下
	@FXML
	private ScrollPane homePageArea;
	private final static String CONTENTTYPE_DEFAULT = "NULL";
	private final static String CONTENTTYPE_MENU = "MENU";
	private final static String CONTENTTYPE_OTHERS = "OTHERS";

	/**
	 * 每个菜单按钮都会关联一个主页内容，点击按钮时，会将其中的内容展示在右侧。 内容分为两大类：1：菜单列表，2：其他自定义
	 */
	class MenuButtonContent {
		String key;// 关联菜单按钮的key
		String contentType = CONTENTTYPE_DEFAULT;// 关联的主页内容类型
		Node contentArea;// 关联的主页内容
		LinkedHashMap<String, ButtonGroup> menuButtonGroup = new LinkedHashMap<>();// 菜单类型主页的按钮组列表
	}

	public RibbonHome() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RibbonHome.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			logger.error("构造RibbonHome失败", e);
		}

		leftMenu = new RibbonMenu();
		leftMenu.setPrefWidth(menuWidht);
		this.setLeft(leftMenu);
		String cssURL = ResourceUtil
				.getFileUrlByRelativePath("cfg/ui/css/ribbonHome.css");
		getStylesheets().add(cssURL);
	}

	public void showFirstPage() {
		Button btn = leftMenu.getButton(0);
		if (btn != null) {
			MenuButtonContent mbc = (MenuButtonContent) btn.getUserData();
			if (mbc.contentArea != null) {
				homePageArea.setContent(mbc.contentArea);
			}
		}
	}

	/**
	 * 增加左边菜单栏的按钮，并为每一个按钮设置一个UserData，其类型为MenuButtonContent
	 * 
	 * @param key
	 *            按钮的key
	 * @param button
	 *            按钮对象
	 */
	public void addMenuButton(String key, Button button) {
		leftMenu.addButton(key, button);
		MenuButtonContent mbc = new MenuButtonContent();
		mbc.key = key;
		button.setUserData(mbc);
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 1) {// 单击显示对应的主页
						Button sourceBtn = (Button) mouseEvent.getSource();
						MenuButtonContent mbc = (MenuButtonContent) sourceBtn.getUserData();
						if (mbc != null) {
							logger.debug("Selected button: " + mbc.key);
							Node content = mbc.contentArea;
							homePageArea.setContent(content);
						} else {
							logger.debug("Selected button's MenuButtonContent is null");
						}

					}
				}

			}

		};
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	/**
	 * 获取所有的菜单按钮
	 * @return 菜单按钮的集合
	 */
	public LinkedHashMap<String, Button> getMenuButtons() {
		return this.leftMenu.getButtons();
	}

	/**
	 * 向与指定按钮关联的主页增加按钮（添加到默认组，即title为空字符串）
	 * 
	 * @param menuKey
	 *            菜单按钮的key
	 * @param buttonKey
	 *            按钮唯一key
	 * @param button
	 *            待添加的按钮
	 */
	public void addPageButton(String menuKey, String buttonKey, Button button) {
		addPageButton(menuKey, "", buttonKey, button);
	}

	/**
	 * 向与指定按钮关联的主页增加按钮<br>
	 * 这个动作包含两个含义：<br>
	 * 1：指定key关联的菜单按钮被点击时，将打开一个菜单主页<br>
	 * 2：在菜单主页中指定的按钮组（如果按钮组不存在则会新增一个）中，增加一个按钮
	 * 
	 * @param menuKey
	 *            菜单按钮的key
	 * @param groupName
	 *            菜单页上的菜单组名称，如果为“”则不显示
	 * @param buttonKey
	 *            在每一个按钮组中，每个按钮的key是唯一的
	 * @param button
	 *            待添加的按钮
	 */
	public void addPageButton(String menuKey, String groupName, String buttonKey, Button button) {
		if(this.buttons.containsKey(buttonKey)) {
			throw new RuntimeException("已有重复key值的按钮，key=" + buttonKey);
		}
		Button menuButton = leftMenu.getButton(menuKey);
		if (menuButton == null) {
			throw new RuntimeException("没有指定key对应的按钮,key=" + menuKey);
		}
		MenuButtonContent mbc = (MenuButtonContent) menuButton.getUserData();
		VBox contentArea = createVBox(mbc);// 底板

		// 增加关联的按钮组
		ButtonGroup bg = mbc.menuButtonGroup.get(groupName);
		if (bg == null) {
			// 首次，没有对应的按钮组则创建
			bg = new ButtonGroup();
			bg.setTitle(groupName);
			bg.setMaxColumns(maxColumns);
			bg.setGap(buttonGap);

			mbc.menuButtonGroup.put(groupName, bg);
			contentArea.getChildren().add(bg);
		}
		button.getStyleClass().add("moduleButton");
		bg.addButton(buttonKey, button);
		bg.setPrefHeight(rowHeight * bg.getRowNumber());
		contentArea.layout();// 重新计算控件位置
	}

	/**
	 * 创建底板
	 */
	private VBox createVBox(MenuButtonContent mbc) {
		VBox contentArea = null;// 底板
		if (!mbc.contentType.equals(CONTENTTYPE_DEFAULT) && !mbc.contentType.equals(CONTENTTYPE_MENU)) {
			throw new RuntimeException("无法在非空及非菜单类型的主页内容上添加按钮");
		} else if (mbc.contentType.equals(CONTENTTYPE_DEFAULT)) {// 首次添加，创建底板
			mbc.contentType = CONTENTTYPE_MENU;
			contentArea = new VBox();// 菜单按钮的底板为VBox
			contentArea.prefWidthProperty().bind(homePageArea.widthProperty().add(-5));
			contentArea.prefHeightProperty().bind(homePageArea.prefHeightProperty().add(-5));
			mbc.contentArea = contentArea;
		} else {// 否首次添加，无需创建底板
			contentArea = (VBox) mbc.contentArea;
		}
		return contentArea;
	}

	/**
	 * 获取所有的主页按钮
	 * @return 主页按钮集合
	 */
	public LinkedHashMap<String, Button> getPageButtons() {
		return this.buttons;
	}
	/**
	 * 获取指定的主页按钮
	 * @param key 按钮key值
	 * @return 与key关联的按钮
	 */
	public Button getPageButton(String key) {
		return this.buttons.get(key);
	}

	/**
	 * 向主页增加内容，并与key对应的左侧菜单按钮关联
	 * 
	 * @param key
	 *            菜单按钮的key
	 * @param content
	 *            关联的主页内容
	 */
	public void addPageContent(String key, Node content) {
		Button menuButton = leftMenu.getButton(key);
		if (menuButton == null) {
			throw new RuntimeException("没有指定key对应的按钮,key=" + key);
		}
		MenuButtonContent mbc = (MenuButtonContent) menuButton.getUserData();
		if (!mbc.contentType.equals(CONTENTTYPE_DEFAULT)) {
			throw new RuntimeException("无法在非空主页内容上添加内容");
		} else if (mbc.contentType.equals(CONTENTTYPE_DEFAULT)) {
			mbc.contentType = CONTENTTYPE_OTHERS;
			mbc.contentArea = content;
		}

	}

	/**
	 * 获取与指定的左侧菜单按钮相关的主页内容
	 * 
	 * @param key
	 *            菜单按钮的key
	 * @return 主页内容
	 */
	public Node getPageContent(String key) {
		Button menuButton = leftMenu.getButton(key);
		MenuButtonContent mbc = (MenuButtonContent) menuButton.getUserData();
		return mbc.contentArea;
	}

	public double getButtonGap() {
		return buttonGap;
	}
	public void setButtonGap(double buttonGap) {
		this.buttonGap = buttonGap;
	}

	public int getMaxColumns() {
		return maxColumns;
	}

	public void setMaxColumns(int maxColumns) {
		this.maxColumns = maxColumns;
	}

	public double getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(double rowHeight) {
		this.rowHeight = rowHeight;
	}

	public double getButtonWidth() {
		return buttonWidth;
	}

	public void setButtonWidth(double buttonWidth) {
		this.buttonWidth = buttonWidth;
	}

	public double getMenuWidht() {
		return menuWidht;
	}

	public void setMenuWidht(double menuWidht) {
		this.menuWidht = menuWidht;
		leftMenu.setPrefWidth(menuWidht);
	}

	
}
