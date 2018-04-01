package net.javafxchina.xmdp.ui.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * ui工具包
 * @author Victor
 *
 */
public class UiTools {

	public static void setAnchor(Node node, Double top, Double bottom,
			Double left, Double right) {
		if (top != null) {
			AnchorPane.setTopAnchor(node, top);
		}
		if (bottom != null) {
			AnchorPane.setBottomAnchor(node, bottom);
		}
		if (left != null) {
			AnchorPane.setLeftAnchor(node, left);
		}
		if (right != null) {
			AnchorPane.setRightAnchor(node, right);
		}
	}

}
