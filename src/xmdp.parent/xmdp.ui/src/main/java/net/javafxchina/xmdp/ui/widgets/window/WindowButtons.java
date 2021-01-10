/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.javafxchina.xmdp.ui.widgets.window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Vertical box with 3 small buttons for window close, minimize and maximize.
 */
public class WindowButtons extends HBox {
	private Rectangle2D backupWindowBounds = null;
	private boolean maximized = false;
	private Stage mainStage;

	public WindowButtons(final Stage stage, boolean canMax, boolean canMin) {
		super(4);
		this.mainStage = stage;
		if (canMin) {
			Button minBtn = new Button();
			minBtn.setId("window-min");
			minBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					mainStage.setIconified(true);
				}
			});
			getChildren().addAll(minBtn);
		}
		if (canMax) {
			Button maxBtn = new Button();
			maxBtn.setId("window-max");
			maxBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					toogleMaximized();
				}
			});
			getChildren().addAll(maxBtn);
		}
		Button closeBtn = new Button();
		closeBtn.setId("window-close");
//		closeBtn.setMaxSize(25, 25);
//		closeBtn.setMinSize(25, 25);
		closeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				mainStage.close();
			}
		});
		getChildren().addAll(closeBtn);
	}

	public void toogleMaximized() {
		double x = mainStage.getX();
		if (x < 0) {
			x = 0;
		}
		// 当窗口左侧边拖到超过屏幕左侧边的时候，这里会出现一个数组越界的异常，因此做一下保护
		final Screen screen = Screen.getScreensForRectangle(x, mainStage.getY(), 1, 1).get(0);
		if (maximized) {
			maximized = false;
			if (backupWindowBounds != null) {
				mainStage.setX(backupWindowBounds.getMinX());
				mainStage.setY(backupWindowBounds.getMinY());
				mainStage.setWidth(backupWindowBounds.getWidth());
				mainStage.setHeight(backupWindowBounds.getHeight());
			}
		} else {
			maximized = true;
			backupWindowBounds = new Rectangle2D(mainStage.getX(), mainStage.getY(), mainStage.getWidth(),
					mainStage.getHeight());
			mainStage.setX(screen.getVisualBounds().getMinX());
			mainStage.setY(screen.getVisualBounds().getMinY());
			mainStage.setWidth(screen.getVisualBounds().getWidth());
			mainStage.setHeight(screen.getVisualBounds().getHeight());
		}
	}

	public boolean isMaximized() {
		return maximized;
	}
}
