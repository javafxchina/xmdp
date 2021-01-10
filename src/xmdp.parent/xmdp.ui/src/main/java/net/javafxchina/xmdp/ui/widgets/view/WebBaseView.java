package net.javafxchina.xmdp.ui.widgets.view;

import java.net.URL;


import javafx.scene.layout.AnchorPane;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.ModuleInfo;

/**
 * 展示Web页面的View
 * @author Victor
 *
 */
public class WebBaseView extends FXMLBaseView {
	protected String fxmlPath = "WebBaseView.fxml";

	/**
	 * 
	 * @see FXMLBaseView#FXMLBaseView(ModuleInfo)
	 */
	public WebBaseView(ModuleInfo module) {
		super(module);
	}

	@Override
	protected void initContent(AnchorPane pane) {
		boolean continueSuperInit = initWebContent(pane);
		if (continueSuperInit) {
			super.initContent(pane);
		}
	}

	/**
	 * 初始化，直接使用WebBaseView则查找类路径下的fxml文件，子类则查找产品文件夹下的fxml子文件夹
	 * @param pane 放置web内容的父面板
	 * @return 是否继续调用父类的初始化方法initContent,如果为false则pane的内容绘制完全由此方法控制，如果为true则会调用父类方法<br>
	 * 如果子类重写此方法，并且返回为false，则需完全重写面板初始化逻辑（<b>一般并不建议这样做</b>）
	 * 如果子类重写此方法，并且返回为true，则一般仅需调用setFXML方法设置新的fxml文件和controller类
	 * @see #initContent(AnchorPane)
	 */
	protected boolean initWebContent(AnchorPane pane) {
		if(this.getClass().getName().equals(WebBaseView.class.getName())) {
			URL url=getClass().getResource(	fxmlPath);
			setFXML(url, new WebBaseViewController(moduleInfo.uri,this));
		}else{
			setFXML(fxmlPath, new WebBaseViewController(moduleInfo.uri,this));
		}
		return true;
	}

	/**
	 * 在加载完毕后调用此方法
	 */
	protected void afterLoad() {
		System.out.println("加载网页" + moduleInfo.uri + "完毕");
	}

	@Override
	protected boolean beforeClose() {
		return true;
	}
}
