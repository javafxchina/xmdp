package net.javafxchina.xdmp.addon.demo;


import javafx.scene.layout.AnchorPane;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.ModuleInfo;
import net.javafxchina.xmdp.ui.widgets.view.FXMLBaseView;

/**
 * 注意，与demo/TestClientSpringView.fxml配套，其中需要制定Controller为TestClientSpringViewCotroller
 * @author Victor
 *
 */
public class TestClientSpringView extends FXMLBaseView{
	
	public TestClientSpringView(ModuleInfo module) {
		super(module);
	}

	@Override
	protected boolean beforeClose() {
		return true;
	}

	@Override
	protected void initContent(AnchorPane pane) {
		String fxmlPath="demo/TestClientSpringView.fxml";
		setFXMLWithController(fxmlPath);
		super.initContent(pane);
	}

}
