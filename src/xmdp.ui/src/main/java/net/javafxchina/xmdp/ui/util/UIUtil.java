package net.javafxchina.xmdp.ui.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.javafxchina.xmdp.ui.widgets.dialog.ConfirmDialog;
import net.javafxchina.xmdp.ui.widgets.dialog.ErrorDialog;
import net.javafxchina.xmdp.ui.widgets.dialog.InformationDialog;

/**
 * ui工具包
 * @author Victor
 *
 */
public class UIUtil {
	public static void openInformation(String message) {
		new InformationDialog(message).openDialog();
	}

	public static void openError(String title, String message) {
		ErrorDialog dialog=new ErrorDialog(title, message);
		dialog.openDialog();
	}

	public static boolean openConfirm(String title, String message) {
		ConfirmDialog confirmDialog = new ConfirmDialog(title,message);
		return confirmDialog.openDialog();

	}
	public static void setAnchor(Node node, Double top, Double bottom,
			Double left, Double right) {
		if (top != null) {
			AnchorPane.setTopAnchor(node, top);
		}
		if (bottom != null) {
			AnchorPane.setBottomAnchor(node, bottom);
		}
		if (left != null) {
			AnchorPane.setLeftAnchor(node, left);
		}
		if (right != null) {
			AnchorPane.setRightAnchor(node, right);
		}
	}

}
