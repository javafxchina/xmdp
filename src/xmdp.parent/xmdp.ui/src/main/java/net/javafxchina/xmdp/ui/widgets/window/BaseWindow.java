package net.javafxchina.xmdp.ui.widgets.window;


import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.javafxchina.xmdp.ui.util.ResourceUtil;

/**
 * 基础无边框窗口
 */
public class BaseWindow extends Stage {
	protected double windowWidth = 400;
	protected double windowHeight = 300;
	protected double toolbarHeight = 35;
	protected Scene scene;
	protected BorderPane basePane;
	protected BorderPane root;
	protected WindowButtons windowButtons;
	protected ToolBar toolBar;
	protected boolean allowMax;
	protected double mouseDragOffsetX = 0;
	protected double mouseDragOffsetY = 0;
	protected String title = "";
	protected BaseWindowDragResizer resizer;

	/**
	 * 创建一般窗体
	 */
	public BaseWindow() {
		this("", true, true);
	}

	/**
	 * 创建带有标题的窗体
	 * @param title 标题
	 */
	public BaseWindow(String title) {
		this(title, true, true);
	}

	/**
	 * 创建无标题的窗体，可设置是否带有最大化，最小化按钮
	 * @param canMax 是否允许最大化
	 * @param canMin 是否允许最小化
	 */
	public BaseWindow(boolean canMax, boolean canMin) {
		this("", canMax, canMin);
	}

	/**
	 * 构造方法，可选择是否允许最大化及最小化、鼠标拖动改变大小
	 * 
	 * @param canMax
	 *            是否允许最大化
	 * @param canMin
	 *            是否允许最小化
	 * @param resizable
	 *            是否允许通过鼠标拖放改变大小
	 */
	public BaseWindow(boolean canMax, boolean canMin, boolean resizable) {
		this("", canMax, canMin);
		if (resizable) {
			this.setResizable();
		}
	}

	/**
	 * 构造方法，可选择是否允许最大化及最小化
	 * 
	 * @param title
	 *            标题
	 * @param canMax
	 *            是否允许最大化
	 * @param canMin
	 *            是否允许最小化
	 * @param resizable 是否允许通过鼠标拖放改变大小
	 */
	public BaseWindow(String title, boolean canMax, boolean canMin, boolean resizable) {
		this(title, canMax, canMin);
		if (resizable) {
			this.setResizable();
		}
	}

	/**
	 * 构造方法，可选择是否允许最大化及最小化
	 * 
	 * @param title
	 *            标题
	 * @param canMax
	 *            是否允许最大化
	 * @param canMin
	 *            是否允许最小化
	 */
	public BaseWindow(String title, boolean canMax, boolean canMin) {
		super();
		this.title = title;
		this.allowMax = canMax;
		initStyle(StageStyle.UNDECORATED);
		// 模态窗口
		initModality(Modality.APPLICATION_MODAL);

		basePane = new BorderPane();
		basePane.setPrefSize(windowWidth, windowHeight);

		toolBar = new ToolBar();
		toolBar.setPrefSize(windowWidth, toolbarHeight);
		toolBar.getStyleClass().add("titleArea");
		basePane.setTop(toolBar);

		// root.setTop(toolBar);

		root = new BorderPane();
		basePane.setCenter(root);

		if (title != null && title.length() > 0) {
			Label titleLabel = new Label(title);
			toolBar.getItems().add(titleLabel);
		}
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		toolBar.getItems().add(spacer);
		windowButtons = new WindowButtons(this, canMax, canMin);
		toolBar.getItems().add(windowButtons);
		addListener();
		scene = new Scene(basePane);
		ObservableList<String> styleSheets = scene.getStylesheets();

		String cssUrl = ResourceUtil.getFileUrlByRelativePath("cfg/ui/css/window.css");
		styleSheets.add(cssUrl);
		setScene(scene);
	}

	private void addListener() {
		if (allowMax) {
			toolBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.getClickCount() == 2) {
						windowButtons.toogleMaximized();
					}
				}
			});
		}
		toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseDragOffsetX = event.getSceneX();
				mouseDragOffsetY = event.getSceneY();
			}
		});
		toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!windowButtons.isMaximized()) {
					BaseWindow.this.setX(event.getScreenX() - mouseDragOffsetX);
					BaseWindow.this.setY(event.getScreenY() - mouseDragOffsetY);
				}
			}
		});
		this.setEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					BaseWindow.this.close();
				}
			};
		});
	}

	public void setPrefWidth(double width) {
		root.setPrefWidth(width);
	}

	public void setPrefHeight(double height) {
		root.setPrefHeight(height);
	}

	public void setCenter(Node node) {
		root.setCenter(node);
	}

	public void setBottom(Node node) {
		root.setBottom(node);
	}

	public Pane getPane() {
		return root;
	}

	public ToolBar getToolBar() {
		return toolBar;
	}

	/**
	 * 使得此窗体变得可以通过鼠标改变大小
	 */
	public void setResizable() {
		if (resizer != null)
			return;
		resizer = new BaseWindowDragResizer(this.root);
		resizer.makeResizable();
	}

}
