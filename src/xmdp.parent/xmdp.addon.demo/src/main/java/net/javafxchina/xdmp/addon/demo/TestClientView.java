package net.javafxchina.xdmp.addon.demo;

import javafx.scene.layout.AnchorPane;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.ModuleInfo;
import net.javafxchina.xmdp.ui.widgets.view.FXMLBaseView;

/**
 * @author Victor
 *
 */
public class TestClientView extends FXMLBaseView {

	public TestClientView(ModuleInfo module) {
		super(module);
	}

	@Override
	protected boolean beforeClose() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void initContent(AnchorPane pane) {
		String fxmlPath = "demo/TestClientView.fxml";
		controller = new TestClientViewCotroller();
		setFXML(fxmlPath, controller);
		super.initContent(pane);
	}

}
