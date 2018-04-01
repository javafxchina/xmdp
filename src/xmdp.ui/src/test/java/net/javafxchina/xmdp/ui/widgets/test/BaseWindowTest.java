package net.javafxchina.xmdp.ui.widgets.test;


import javafx.application.Application;
import javafx.stage.Stage;
import net.javafxchina.xmdp.ui.widgets.window.BaseWindow;
/**
 * 测试基础窗体
 * @author Victor
 *
 */
public class BaseWindowTest extends Application{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BaseWindowTest.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseWindow win=new BaseWindow("hello world window");
		win.setWidth(300);
		win.setHeight(300);
		win.setX(100);
		win.setY(100);
		win.show();
		win.setResizable();
	}

}
