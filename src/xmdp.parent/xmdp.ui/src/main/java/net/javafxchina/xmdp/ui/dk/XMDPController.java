package net.javafxchina.xmdp.ui.dk;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import net.javafxchina.xmdp.core.XMDPParams;
import net.javafxchina.xmdp.core.XMDPContext;
import net.javafxchina.xmdp.ui.dk.ResourcePathDefine.DesktopResource;
import net.javafxchina.xmdp.ui.util.ResourceUtil;
import net.javafxchina.xmdp.ui.widgets.window.WindowButtons;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * 桌面主面板，与XMDPLauncher配套
 * 
 * @author Victor
 *
 */
@Component
@Lazy(true)
public class XMDPController implements Initializable {
	private static Logger logger = LoggerFactory.getLogger(XMDPController.class);
	@Autowired
	@Qualifier("xmdpParams")
	private XMDPParams xmdpParams;
	private double logoHeight = 40;
	private double logoWidth = 160;
	private double tabIconHeight=25;
	private double tabIconWidth=25;
	private String titleColor="#000000";

	public final static String CLASS_DEFINE_TOPBAR = "topBar";
	public final static String CLASS_DEFINE_TAB = "tab";
	public final static String CLASS_DEFINE_TABPANE = "tabPane";
	private String title = "我的桌面";

	@Autowired
	private XMDPEntryFactory xmdpEntryFactory;

	/** 标题栏，包括logo和最大化最小化按钮等 */
	@FXML
	private ToolBar topBar;
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
		logger.info("XMDPController初始化成功");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 初始化参数
		try {
			logoHeight =xmdpParams.getIntParam("xmdp.logoheight");
			logoWidth = xmdpParams.getIntParam("xmdp.logowidth");
			title =xmdpParams.getStringParam("xmdp.apptitle");
			titleColor=xmdpParams.getStringParam("xmdp.apptitle_color");
		} catch (Exception e) {
			logger.error("初始化参数获取异常", e);
			throw new RuntimeException(e);
		}


		context.getRootStage().setTitle(title);
		desktopTabPane.getStyleClass().add(CLASS_DEFINE_TABPANE);
		homeTab.getStyleClass().add(CLASS_DEFINE_TAB);
		topBar.getStyleClass().add(CLASS_DEFINE_TOPBAR);
		initTopBar();

		// Home选项卡
		homeTab.setGraphic(ResourceUtil.createImageView(DesktopResource.HOME_ICON,tabIconWidth,tabIconHeight));

		// Home选项卡的内容，菜单按钮
		Pane centerNode = xmdpEntryFactory.createEntryPane(desktopTabPane);
		if (centerNode != null) {
			homePane.setCenter(centerNode);
			// 菜单部分全屏，与底板同宽同高
			centerNode.prefHeightProperty().bind(homePane.heightProperty());
			centerNode.prefWidthProperty().bind(homePane.widthProperty());
		}
	}

	private double mouseDragOffsetX = 0;
	private double mouseDragOffsetY = 0;

	/**
	 * 初始化标题栏，包括logo、最大化最小化按钮等
	 */
	private void initTopBar() {
		Image logoImage = ResourceUtil.getImage(DesktopResource.LOGO_ICON);
		ImageView logoIv = new ImageView(logoImage);
		logoIv.setFitHeight(logoHeight);
		logoIv.setFitWidth(logoWidth);
		topBar.getItems().add(logoIv);
		
		
		Label appTitel=new Label(title);
		appTitel.setTextFill(Color.web(titleColor));
		topBar.getItems().add(appTitel);
		
		// logo左对齐，按钮右对齐
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		topBar.getItems().add(spacer);
		final WindowButtons windowButtons = new WindowButtons(context.getRootStage(), true, true);
		topBar.getItems().add(windowButtons);

		topBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					windowButtons.toogleMaximized();
				}
			}
		});
		// add window dragging
		topBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseDragOffsetX = event.getSceneX();
				mouseDragOffsetY = event.getSceneY();
			}
		});
		topBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!windowButtons.isMaximized()) {
					context.getRootStage().setX(event.getScreenX() - mouseDragOffsetX);
					context.getRootStage().setY(event.getScreenY() - mouseDragOffsetY);
				}
			}
		});

	}


}
