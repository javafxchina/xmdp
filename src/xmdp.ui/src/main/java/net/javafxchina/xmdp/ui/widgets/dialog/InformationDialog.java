package net.javafxchina.xmdp.ui.widgets.dialog;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import net.javafxchina.xmdp.ui.util.UIUtil;
import net.javafxchina.xmdp.ui.widgets.window.BaseWindow;


/**
 * 确认对话框
 */
public class InformationDialog extends BaseWindow {
	private String msg;

	public InformationDialog(String msg) {
		super(false, true);
		this.msg = msg;
		setPrefWidth(200);
		setPrefHeight(160);
		createContent();
		createButtons();
	}

	private void createContent() {
		AnchorPane anchorpane = new AnchorPane();

		Label label = new Label();
		label.setPrefWidth(150);
		label.setText(msg);
		label.setWrapText(true);
		UIUtil.setAnchor(label, 10D, null, 5D, 5D);
		anchorpane.getChildren().add(label);
		setCenter(anchorpane);
	}

	private void createButtons() {
		AnchorPane anchorpane = new AnchorPane();
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(5, 5, 5, 5));
		hBox.setSpacing(10);

		Button okBtn = new Button("确定");
		hBox.getChildren().addAll(okBtn);
		okBtn.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				hide();
			};
		});
		
		okBtn.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
			}
		});

		anchorpane.getChildren().add(hBox);
		AnchorPane.setBottomAnchor(hBox, 5.0);
		AnchorPane.setRightAnchor(hBox, 5.0);
		setBottom(anchorpane);
	}

	public void openDialog() {
		super.showAndWait();
	}

}
