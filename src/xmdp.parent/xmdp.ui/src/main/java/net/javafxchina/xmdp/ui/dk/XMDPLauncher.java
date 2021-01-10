package net.javafxchina.xmdp.ui.dk;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.javafxchina.xmdp.addon.AddonsLauncher;
import net.javafxchina.xmdp.core.IServiceSet;
import net.javafxchina.xmdp.core.XMDPContext;
import net.javafxchina.xmdp.ui.dk.ResourcePathDefine.DesktopResource;
import net.javafxchina.xmdp.ui.util.FXMLUtil;
import net.javafxchina.xmdp.ui.util.ResourceUtil;

/**
 * 桌面，与XMDPController配套
 * @author Victor
 *
 */
public class XMDPLauncher extends Application {
	private Logger logger = LoggerFactory.getLogger(XMDPLauncher.class);
	private IServiceSet serviceSet = AddonsLauncher.getInstance();

	@Override
	public void start(Stage stage) throws Exception {
		try {
			serviceSet.init();
			XMDPContext.getInstance().setRootStage(stage);
			Parent root = FXMLUtil
					.getParentWithSpringControllerFactory(DesktopResource.FXML);// 从Spring中获取受控的控制器
			
			Scene scene = new Scene(root);
			stage.initStyle(StageStyle.UNDECORATED);
			Screen screen = Screen.getPrimary();
			stage.setWidth(screen.getVisualBounds().getWidth());
			stage.setHeight(screen.getVisualBounds().getHeight());
			stage.getIcons().add(
					ResourceUtil.getImage(DesktopResource.TITLE_ICON));
			stage.setScene(scene);
			String desktopCssUrl = ResourceUtil
					.getFileUrlByRelativePath(DesktopResource.CSS);
			scene.getStylesheets().add(desktopCssUrl);
			stage.show();
		} catch (Exception e) {
			logger.error("启动桌面失败", e);
			throw e;
		}
	}

	/**
	 * @param args
	 *            运行参数
	 */
	public static void main(String[] args) {
		XMDPLauncher.launch(args);
	}

}
