package net.javafxchina.xmdp.ui.dk;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.javafxchina.xmdp.addon.AddonsLauncher;
import net.javafxchina.xmdp.core.IServiceSet;
import net.javafxchina.xmdp.core.XMDPContext;
import net.javafxchina.xmdp.ui.dk.ResourcePathDefine.DesktopResource;
import net.javafxchina.xmdp.ui.util.FXMLUtil;
import net.javafxchina.xmdp.ui.util.ResourceUtil;

/**
 * 新桌面，考虑引入MD设计，与XMDPController2配套
 * 
 * @author Victor
 *
 */
public class XMDPLauncher2 extends Application {
	private Logger logger = LoggerFactory.getLogger(XMDPLauncher2.class);
	private IServiceSet serviceSet = AddonsLauncher.getInstance();

	@Override
	public void start(Stage stage) throws Exception {
		try {
			serviceSet.init();
			XMDPContext.getInstance().setRootStage(stage);
			Parent root = FXMLUtil.getParentWithSpringControllerFactory(DesktopResource.FXML2);// 从Spring中获取受控的控制器

			JFXDecorator decorator = new JFXDecorator(stage, root);
			decorator.setCustomMaximize(true);
			ImageView logoIv = (ImageView) XMDPContext.getInstance().getProperty(DesktopResource.LOGO_PROPERTY_KEY);
			if (logoIv == null) {
				decorator.setGraphic(new SVGGlyph(""));
			} else {
				decorator.setGraphic(logoIv);
			}
			Rectangle2D bounds = getBiggestViewableRectangle(stage);
			double width = bounds.getWidth();
			double height = bounds.getHeight();
			Scene scene = new Scene(decorator, width, height);

			stage.getIcons().add(ResourceUtil.getImage(DesktopResource.TITLE_ICON));
			stage.setScene(scene);
			root.requestFocus();
			String desktopCssUrl = ResourceUtil.getFileUrlByRelativePath(DesktopResource.CSS2);
			scene.getStylesheets().add(desktopCssUrl);
			stage.show();
		} catch (Exception e) {
			logger.error("启动桌面失败", e);
			throw e;
		}
	}

	protected Rectangle2D getBiggestViewableRectangle(Stage stage) {

		Rectangle2D res;

		if (Screen.getScreens().size() == 1) {
			res = Screen.getPrimary().getVisualBounds();
		} else {
			Rectangle2D stageRect = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
			List<Screen> screens = Screen.getScreensForRectangle(stageRect);

			// The stage is entirely rendered on one screen, which is either the
			// primary one or not, we don't care here.
			// if (screens.size() == 1) {
			res = screens.get(0).getVisualBounds();
			// } else {
			// The stage is spread over several screens.
			// We compute the surface of the stage on each on the involved
			// screen to select the biggest one == still to be implemented.
			// TreeMap<String, Screen> sortedScreens = new TreeMap<>();
			//
			// for (Screen screen : screens) {
			// computeSurface(screen, stageRect, sortedScreens);
			// }
			//
			// res = sortedScreens.get(sortedScreens.lastKey()).getVisualBounds();
			// }
		}

		return res;
	}

	/**
	 * @param args 运行参数
	 */
	public static void main(String[] args) {
		XMDPLauncher2.launch(args);
	}

}
