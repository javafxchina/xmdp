package net.javafxchina.xmdp.ui.widgets;

import java.util.LinkedHashMap;


import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.javafxchina.xmdp.ui.util.ResourceUtil;

/**
 * 类似Office2010中的Ribbon风格菜单,纵向摆放
 * @author Victor
 *
 */
public class RibbonMenu extends VBox{
	private LinkedHashMap<String, Button> buttons = new LinkedHashMap<String, Button>();
	
	public RibbonMenu() {
		super();
		 String cssUrl = ResourceUtil
					.getFileUrlByRelativePath("cfg/ui/css/ribbonMenu.css");
		 this.getStylesheets().add(cssUrl);
	}
	
	/**
	 * 根据Key值获取对应的按钮
	 * @param key 按钮key值
	 * @return 对应的按钮
	 */
	public Button getButton(String key) {
		return buttons.get(key);
	}
	/**
	 * 获取菜单栏中的第index个按钮
	 * @param index 按钮的索引号，从0开始
	 * @return 返回对应的按钮，如果索引号超出按钮个数的范围则返回null
	 */
	public Button getButton(int index) {
		if(index<0||index>=buttons.size()) {
			return null;
		}
		int i=0;
		for(Button btn:buttons.values()) {
			if(i==index) {
				return btn;
			}
			i++;
		}
		return null;
	}
	/**
	 * 向按钮组增加按钮
	 * @param key 关联的key值，每个button会赋予一个key，类似于内部名称，可以通过该key来获取按钮
	 * @param btn 指定 的按钮
	 */
	public void addButton(String key, Button btn) {
		if (buttons.containsKey(key)) {
			throw new RuntimeException("已经存在的key：" + key);
		}
		buttons.put(key, btn);
		btn.getStyleClass().add("ribbon-menu-button");
		
		this.getChildren().add(btn);
		
	}

	/**
	 * 移除指定的按钮
	 * @param key 增加按钮时给出的key值
	 */
	public void removeButton(String key) {
		this.getChildren().removeAll(buttons.values());
		for(String k:buttons.keySet()){
			this.getChildren().remove(buttons.get(k));
		}
		buttons.remove(key);
		for(String k:buttons.keySet()){
			addButton(k, buttons.get(k));
		}
	} 

	/**
	 * 获取按钮个数
	 * @return 按钮个数
	 */
	public int getButtonsSize(){
		return buttons.size();
	}

	public LinkedHashMap<String, Button> getButtons() {
		return buttons;
	}
}
