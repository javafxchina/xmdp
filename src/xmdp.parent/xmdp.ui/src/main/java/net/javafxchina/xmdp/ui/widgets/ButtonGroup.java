package net.javafxchina.xmdp.ui.widgets;

import java.io.IOException;
import java.util.LinkedHashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * 按钮组控件
 * 
 */
public class ButtonGroup extends VBox {
	@FXML
	private Label title;
	@FXML
	private GridPane buttonGrid;
	private LinkedHashMap<String, Button> buttons = new LinkedHashMap<String, Button>();
	private int maxColumns=10;
	private int rowNumber;
	public ButtonGroup() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"ButtonGroup.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTitle(String title) {
		this.title.setText(title);
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
		addButton(btn,buttons.size());
	}
	/**
	 * 增加按钮组件
	 */
	protected void addButton(Button btn,int count){
		int columnIndex=(count - 1)%maxColumns;
		int rowIndex=(count - 1)/maxColumns;
		buttonGrid.add(btn, columnIndex, rowIndex);
		if(rowIndex+1>rowNumber){
			rowNumber=rowIndex+1;
		}
	}

	/**
	 * 移除指定的按钮
	 * @param key 增加按钮时给出的key值
	 */
	public void removeButton(String key) {
		buttonGrid.getChildren().removeAll(buttons.values());
		buttons.remove(key);
		int count=0;
		for(String k:buttons.keySet()){
			count++;
			addButton(buttons.get(k),count);
		}
	} 

	/**
	 * 获取最大的列数 
	 * @return 最大的列数
	 */
	public int getMaxColumns() {
		return maxColumns;
	}

	/**
	 * 设置按钮组最大的列数，超过后会进行换行
	 * @param maxColumns 最大列数
	 */
	public void setMaxColumns(int maxColumns) {
		this.maxColumns = maxColumns;
	}
	/**
	 * 设置按钮之间的空隙
	 * @param gap 空隙大小
	 */
	public void setGap(double gap){
		buttonGrid.setHgap(gap);
		buttonGrid.setVgap(gap);
		setMargin(title, new Insets(gap));
	}
	
	/**
	 * 获取按钮个数
	 * @return 按钮个数
	 */
	public int getButtonsSize(){
		return buttons.size();
	}

	/**
	 * 获取行数
	 * @return 行数
	 */
	public int getRowNumber() {
		return rowNumber;
	}

	public Label getTitle() {
		return title;
	}

	public LinkedHashMap<String, Button> getButtons() {
		return buttons;
	}
}
