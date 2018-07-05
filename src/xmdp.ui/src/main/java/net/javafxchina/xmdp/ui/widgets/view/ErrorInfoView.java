package net.javafxchina.xmdp.ui.widgets.view;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.ModuleInfo;
import net.javafxchina.xmdp.ui.util.UIUtil;

/**
 * 错误界面 tab页
 */
public class ErrorInfoView extends BaseView {
	private String errorMsg;

	public ErrorInfoView(ModuleInfo module, String errorMsg) {
		super(module);
		this.errorMsg = errorMsg;
	}

	protected void initContent(AnchorPane pane) {
		HBox hbox = new HBox();
		Label errorInfo = new Label(errorMsg);
		errorInfo.setTextFill(Color.RED);
		errorInfo.setFont(new Font(20));
		errorInfo.setTextAlignment(TextAlignment.CENTER);
		hbox.getChildren().add(errorInfo);
		hbox.setAlignment(Pos.CENTER);
		// hbox.setStyle("-fx-background-color: red");
		UIUtil.setAnchor(hbox, 0.0, 0.0, 0.0, 0.0);
		pane.getChildren().add(hbox);
	}


	@Override
	protected boolean beforeClose() {
		return true;
	}

}
