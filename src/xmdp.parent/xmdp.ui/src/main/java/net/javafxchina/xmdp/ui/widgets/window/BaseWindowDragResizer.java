package net.javafxchina.xmdp.ui.widgets.window;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 拖动改变大小工具类
 * @author Victor
 *
 */
public class BaseWindowDragResizer {
	private static final int MIN_WIDTH = 50;
	private static final int MIN_HEIGHT = 50;
	/**
	 * 目标周边可以点击改变大小的范围大小
	 */
	private static final int RESIZE_MARGIN = 2;

	private final BorderPane region;
	private final Stage stage;
	private double mouseDragOffsetY;
	private double mouseDragOffsetX;
	private boolean initMinHeight;

	private boolean dragging;
	DraggableZoneState draggableZoneState = new DraggableZoneState();

	class DraggableZoneState {
		/** 在下边 */
		boolean isMaxY;
		/** 在右边 */
		boolean isMaxX;
		/** 在左边 */
		boolean isMinX;

		/** 是否属于可拖放区域 */
		boolean isInDraggableZone() {
			return draggableZoneState.isMaxY || draggableZoneState.isMaxX || draggableZoneState.isMinX;
		}
	}

	public BaseWindowDragResizer(BorderPane region) {
		this.stage = (Stage)region.getScene().getWindow();
		this.region = region;
		// 设置使用鼠标改变大小的边
		region.setStyle("-fx-border-width: 7;-fx-border-color: transparent;");
	}

	public void makeResizable() {
		region.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mousePressed(event);
			}
		});
		region.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseDragged(event);
			}
		});
		region.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseOver(event);
			}
		});
		region.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseReleased(event);
			}
		});
	}

	protected void mouseReleased(MouseEvent event) {
		dragging = false;
		region.setCursor(Cursor.DEFAULT);
	}

	protected void mouseOver(MouseEvent event) {
		if (isInDraggableZone(event) || dragging) {
			if (draggableZoneState.isMaxY) {
				region.setCursor(Cursor.S_RESIZE);
			} else if (draggableZoneState.isMaxX) {
				region.setCursor(Cursor.E_RESIZE);
			} else if (draggableZoneState.isMinX) {
				region.setCursor(Cursor.W_RESIZE);
			}
		} else {
			region.setCursor(Cursor.DEFAULT);
		}
	}

	protected boolean isInDraggableZone(MouseEvent event) {
		draggableZoneState.isMaxY = event.getY() > (region.getHeight() - RESIZE_MARGIN);
		draggableZoneState.isMaxX = event.getX() > (region.getWidth() - RESIZE_MARGIN);
		draggableZoneState.isMinX = event.getX() < RESIZE_MARGIN;
		return draggableZoneState.isInDraggableZone();
	}

	protected void mouseDragged(MouseEvent event) {
		if (!dragging) {
			return;
		}
		double mouseY = event.getScreenY();
		double mouseX = event.getScreenX();
		if (draggableZoneState.isMaxY) {
			double newHeight = region.getMinHeight() + (mouseY - mouseDragOffsetY);
			double newStageHeight = stage.getMinHeight() + (mouseY - mouseDragOffsetY);
			if (newHeight <= MIN_HEIGHT)
				return;
			region.setMinHeight(newHeight);
			stage.setHeight(newStageHeight);
			stage.setMinHeight(newStageHeight);
			mouseDragOffsetY = mouseY;
		} else if (draggableZoneState.isMaxX) {
			double newWidth = region.getMinWidth() + (mouseX - mouseDragOffsetX);
			if (newWidth <= MIN_WIDTH)
				return;
			region.setMinWidth(newWidth);
			stage.setWidth(newWidth);
			stage.setMinWidth(newWidth);
			mouseDragOffsetX = mouseX;
		} else if (draggableZoneState.isMinX) {
			stage.setX(event.getScreenX());
			double newWidth = region.getMinWidth() - (mouseX - mouseDragOffsetX);
			if (newWidth <= MIN_WIDTH)
				return;
			region.setMinWidth(newWidth);
			stage.setWidth(newWidth);
			stage.setMinWidth(newWidth);
			mouseDragOffsetX = mouseX;

		}

	}

	protected void mousePressed(MouseEvent event) {
		if (!isInDraggableZone(event)) {
			return;
		}

		dragging = true;

		// 设置初始宽度和高度值，用于计算偏移量
		if (!initMinHeight) {
			region.setMinHeight(region.getHeight());
			stage.setMinHeight(stage.getHeight());
			region.setMinWidth(region.getWidth());
			stage.setMinWidth(region.getWidth());
			initMinHeight = true;
		}
		mouseDragOffsetX = event.getScreenX();
		mouseDragOffsetY = event.getScreenY();
	}
}