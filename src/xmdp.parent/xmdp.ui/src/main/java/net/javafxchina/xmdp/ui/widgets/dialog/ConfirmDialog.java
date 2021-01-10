package net.javafxchina.xmdp.ui.widgets.dialog;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import net.javafxchina.xmdp.ui.util.UIUtil;
import net.javafxchina.xmdp.ui.widgets.window.BaseWindow;


/**
 * 确认对话框
 * 
 */
public class ConfirmDialog extends BaseWindow {
	private boolean result = false;
	private String msg;

	public ConfirmDialog(String title,String msg) {
		super(title,false, true);
		this.msg = msg;
		setPrefWidth(300);
		setPrefHeight(180);
		createContent();
		createButtons();
	}

	private void createContent() {
		AnchorPane anchorpane = new AnchorPane();

		Label label = new Label();
		label.setPrefWidth(150);
		label.setText(msg);
		Image image = new Image(getClass().getResourceAsStream("info.png"));
        label.setGraphic(new ImageView(image));
		label.setWrapText(true);
		UIUtil.setAnchor(label, 5D, null, 5D, 5D);
		anchorpane.getChildren().add(label);
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
				result = true;
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

	public boolean openDialog() {
		super.showAndWait();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.stage.Stage#close()
	 */
	public void close() {
		result = false;
		super.close();
	}

}
