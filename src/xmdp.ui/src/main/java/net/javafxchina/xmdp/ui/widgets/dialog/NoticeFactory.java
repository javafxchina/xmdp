package net.javafxchina.xmdp.ui.dk;


import javafx.stage.Stage;
import net.javafxchina.xmdp.ui.widgets.dialog.ConfirmDialog;
import net.javafxchina.xmdp.ui.widgets.dialog.ErrorDialog;
import net.javafxchina.xmdp.ui.widgets.dialog.InformationDialog;

/**
 * 提示信息工厂类
 */
public class NoticeFactory {

	public static void openInformation(final Stage stg, String message) {
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

}
