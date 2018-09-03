package net.javafxchina.xmdp.ui.dk;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.javafxchina.xmdp.core.XMDPParams;
import net.javafxchina.xmdp.core.XMDPContext;
import net.javafxchina.xmdp.ui.dk.ResourcePathDefine.DesktopResource;
import net.javafxchina.xmdp.ui.util.ResourceUtil;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 桌面主面板2（引入MD设计），与XMDPLauncher2配套
 * 
 * @author Victor
 *
 */
@Component
public class XMDPController2 implements Initializable {
	private static Logger logger = LoggerFactory.getLogger(XMDPController2.class);
	@Autowired
	@Qualifier("xmdpParams")
	private XMDPParams xmdpParams;
	private double logoHeight = 40;
	private double logoWidth = 160;
	private double tabIconHeight = 25;
	private double tabIconWidth = 25;
	private String title = "我的桌面";

	@Autowired
	private XMDPEntryFactory xmdpEntryFactory;

	/** 底板，东南西北中 */
	@FXML
	private BorderPane rootPane;
	/** 选项卡 */
	@FXML
	private TabPane desktopTabPane;

	/** Home 选项卡 */
	@FXML
	private Tab homeTab;
	/** Home 选项卡中的内容底板 */
	@FXML
	private BorderPane homePane;

	private XMDPContext context = XMDPContext.getInstance();

	@PostConstruct
	public void init() {
		logger.info("XMDPController2初始化成功");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 初始化参数
		try {
			logoHeight = xmdpParams.getIntParam("xmdp.logoheight");
			logoWidth = xmdpParams.getIntParam("xmdp.logowidth");
			title = xmdpParams.getStringParam("xmdp.apptitle");
		} catch (Exception e) {
			logger.error("初始化参数获取异常", e);
			throw new RuntimeException(e);
		}

		context.getRootStage().setTitle(title);
		

		Image logoImage = ResourceUtil.getImage(DesktopResource.LOGO_ICON);
		ImageView logoIv = new ImageView(logoImage);
		logoIv.setFitHeight(logoHeight);
		logoIv.setFitWidth(logoWidth);
		context.setProperty(DesktopResource.LOGO_PROPERTY_KEY, logoIv);

		// Home选项卡
		homeTab.setGraphic(ResourceUtil.createImageView(DesktopResource.HOME_ICON, tabIconWidth, tabIconHeight));

		// Home选项卡的内容，菜单按钮
		Pane centerNode = xmdpEntryFactory.createEntryPane(desktopTabPane);
		if (centerNode != null) {
			homePane.setCenter(centerNode);
			// 菜单部分全屏，与底板同宽同高
			centerNode.prefHeightProperty().bind(homePane.heightProperty());
			centerNode.prefWidthProperty().bind(homePane.widthProperty());
		}
	}

}
