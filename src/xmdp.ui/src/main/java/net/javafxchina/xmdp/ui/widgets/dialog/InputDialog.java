package net.javafxchina.xmdp.ui.widgets.dialog;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import net.javafxchina.xmdp.ui.util.UIUtil;
import net.javafxchina.xmdp.ui.widgets.window.BaseWindow;


/**
 * 枚举值编辑、新增窗口
 * @author Victor
 *
 */
public class InputDialog extends BaseWindow {
	private String msg;
	private TextField text;

	public InputDialog(String title,String msg) {
		super(title);
//		super(false, true);
//		this.runmode = mode;
		this.msg=msg;
		setPrefWidth(400);
		setPrefHeight(240);
		createContent();
		createButtons();
	}

	private void createContent() {
		AnchorPane anchorpane = new AnchorPane();
		if(msg!=null&& msg.length()>0){
			Label label = new Label();
			label.setPrefWidth(150);
			label.setText(msg+":");
			label.setAlignment(Pos.CENTER_RIGHT);
			UIUtil.setAnchor(label, 35D, null, 5D, 285D);
			anchorpane.getChildren().add(label);
		}
		
		text=new TextField();
		UIUtil.setAnchor(text, 35D, null, 120D, 5D);
		
		anchorpane.getChildren().add(text);
		setCenter(anchorpane);
	}

	private void createButtons() {
		AnchorPane anchorpane = new AnchorPane();
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(5, 5, 5, 5));
		hBox.setSpacing(10);

		Button okBtn = new Button("确定");
		Button cancelBtn = new Button("取消");
		hBox.getChildren().addAll(okBtn, cancelBtn);
		okBtn.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				
				// 这里要调用hide,而不是close（close默认就是调用hide，但这里进行了重写，详见close方法）
				hide();
			};
		});

		cancelBtn.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				close();
			};
		});

		anchorpane.getChildren().add(hBox);
		AnchorPane.setBottomAnchor(hBox, 5.0);
		AnchorPane.setRightAnchor(hBox, 5.0);
		setBottom(anchorpane);
	}

	public String openDialog() {
		super.showAndWait();
		return text.getText();
	}
	public String openDialog(String defaultValue){
		text.setText(defaultValue);
		super.showAndWait();
		return text.getText();
	}
	public String openDialog(boolean canEdit) {
		text.setEditable(canEdit);
		super.showAndWait();
		return text.getText();
	}
	public String openDialog(boolean canEdit,String defaultValue){
		text.setEditable(canEdit);
		text.setText(defaultValue);
		super.showAndWait();
		return text.getText();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.stage.Stage#close()
	 */
	public void close() {
		super.close();
	}


}
