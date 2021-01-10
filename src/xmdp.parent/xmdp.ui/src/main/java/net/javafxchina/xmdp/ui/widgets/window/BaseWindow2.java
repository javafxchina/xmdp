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
 * @version 1.0.0
 */
public class BaseWindow2 extends Stage {
	protected Scene scene;
	protected BorderPane root;
	protected WindowButtons windowButtons;
	protected ToolBar toolBar;
	protected boolean allowMax;
	protected double mouseDragOffsetX = 0;
	protected double mouseDragOffsetY = 0;
	protected String title = "";

	public BaseWindow2() {
		this("", true, true);
	}

	public BaseWindow2(String title) {
		this(title, true, true);
	}

	public BaseWindow2(boolean canMax, boolean canMin) {
		this("", canMax, canMin);
	}

	/**
	 * 构造方法，可选择是否允许最大化及最小化
	 * 
	 * @param canMax
	 *            是否允许最大化
	 * @param canMin
	 *            是否允许最小化
	 */
	public BaseWindow2(String title, boolean canMax, boolean canMin) {
		super();
		this.title = title;
		this.allowMax = canMax;
		initStyle(StageStyle.UNDECORATED);
		// 模态窗口
		initModality(Modality.APPLICATION_MODAL);

		root = new BorderPane();
		root.setPrefSize(600, 400);
		toolBar = new ToolBar();
		toolBar.setPrefSize(400, 35);
		toolBar.getStyleClass().add("titleArea");
		root.setTop(toolBar);
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
		scene = new Scene(root);
		ObservableList<String> styleSheets=scene.getStylesheets();
		
		String cssUrl = ResourceUtil.getFileUrlByRelativePath("css/window.css");
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
					BaseWindow2.this.setX(event.getScreenX() - mouseDragOffsetX);
					BaseWindow2.this.setY(event.getScreenY() - mouseDragOffsetY);
				}
			}
		});
		this.setEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					BaseWindow2.this.close();
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
	public void setResizable() {
		new BaseWindowDragResizer(this.root);
	}

}
