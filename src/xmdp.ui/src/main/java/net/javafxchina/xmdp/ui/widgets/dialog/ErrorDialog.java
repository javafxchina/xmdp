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
 * 错误信息提示对话框
 * @author Victor
 *
 */
public class ErrorDialog extends BaseWindow {
	private final static double PREFWIDTH=400;
	private final static double PREFHEIGHT=300;
	private String msg;
	
	public ErrorDialog(String title,String msg) {
		super(title,false, true);
		this.msg = msg;
		setPrefWidth(PREFWIDTH);
		setPrefHeight(PREFHEIGHT);
		createContent();
		createButtons();
	}

	private void createContent() {
		AnchorPane anchorpane = new AnchorPane();

		Label label = new Label();
		label.setPrefWidth(150);
		label.setText(msg);
		Image image = new Image(getClass().getResourceAsStream("error.png"));
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

		okBtn.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				close();
			};
		});
		hBox.getChildren().add(okBtn);
		anchorpane.getChildren().add(hBox);
		AnchorPane.setBottomAnchor(hBox, 5.0);
		AnchorPane.setRightAnchor(hBox, 5.0);
		setBottom(anchorpane);
	}

	public void openDialog() {
		super.showAndWait();
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
