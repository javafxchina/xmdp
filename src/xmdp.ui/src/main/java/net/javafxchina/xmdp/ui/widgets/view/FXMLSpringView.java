package net.javafxchina.xmdp.ui.widgets.view;


import javafx.scene.layout.AnchorPane;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.ModuleInfo;
import net.javafxchina.xmdp.ui.widgets.view.FXMLBaseView;

/**
 * 客户端View，由FXML实现。
 * <b>注意:<br>
 * 1:在对应的fxml文件中必须配置Controller class<br>
 * 2:对应的Controller class必须作为Spring bean配置
 * </b>
 * @author Victor
 *
 */
public class FXMLSpringView extends FXMLBaseView{
	
	public FXMLSpringView(ModuleInfo module) {
		super(module);
		if(!module.type.equalsIgnoreCase(ModuleInfo.TYPE_CLIENT_FXML)) {
			throw new RuntimeException("ModuleInfo.type必须为"+ModuleInfo.TYPE_CLIENT_FXML);
		}
	}

	@Override
	protected boolean beforeClose() {
		return true;
	}

	@Override
	protected void initContent(AnchorPane pane) {
		String fxmlPath=moduleInfo.uri;
		setFXMLWithController(fxmlPath);
		super.initContent(pane);
	}

}
