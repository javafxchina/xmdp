package net.javafxchina.xmdp.ui.widgets.view;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.*;
import net.javafxchina.xmdp.ui.util.FXMLUtil;
import net.javafxchina.xmdp.ui.util.UIUtil;

/**
 * 基于FXML配置文件和控制器类的View
 * @author Victor
 *
 */
public abstract class FXMLBaseView extends BaseView {
	public static final String fxmlCfgPath="cfg/ui/fxml/";//FXML文件的配置路径
	private static Logger logger = LoggerFactory.getLogger(FXMLBaseView.class);
	protected Initializable controller;
	protected URL fxmlURL;
	protected Parent fxmlParent;
	protected boolean isFXMLWithController=false;

	/**
	 * 构造方法<br>
	 * 注意在构造完毕后，需要调用{@link #setFXML(String, Initializable)}或调用{@link #setFXML(URL, Initializable)}来设置FXML<br>
	 * 然后再调用{@link #initContent(AnchorPane)方法}
	 * @param module
	 *            模块信息
	 */
	public FXMLBaseView(ModuleInfo module) {
		super(module);
	}

	/**
	 * 设置FXML的文件和控制器
	 * 
	 * @param fxmlPath
	 *            用于绘制UI的fxml文件的路径，位于产品安装目录下的fxml文件夹中
	 * @param controller
	 *            与fxml文件对应的控制类
	 * @see #setFXML(URL, Initializable)
	 */
	public void setFXML(String fxmlPath, Initializable controller) {
		this.isFXMLWithController=false;
		this.controller = controller;
		if(!fxmlPath.startsWith(fxmlCfgPath)) {
			fxmlPath=fxmlCfgPath+fxmlPath;
		}
		try {
			this.fxmlURL =FXMLUtil.getURLByFXML(fxmlPath);
		} catch (Exception e) {
			this.fxmlURL =null;
			e.printStackTrace();
		}
	}

	/**
	 * 设置FXML的文件和控制器
	 * 
	 * @param fxmlURL
	 *            用于绘制UI的fxml文件的URL
	 * @param controller
	 *            与fxml文件对应的控制类
	 * @see #setFXML(String, Initializable)
	 */
	public void setFXML(URL fxmlURL, Initializable controller) {
		this.isFXMLWithController=false;
		this.controller = controller;
		this.fxmlURL = fxmlURL;
	}
	
	public void setFXMLWithController(String fxmlPath) {
		this.isFXMLWithController=true;
		if(!fxmlPath.startsWith(fxmlCfgPath)) {
			fxmlPath=fxmlCfgPath+fxmlPath;
		}
		try {
			this.fxmlURL =FXMLUtil.getURLByFXML(fxmlPath);
		} catch (Exception e) {
			this.fxmlURL =null;
			logger.error("getURLByFXML时发生异常",e);
		}
	}
	public void setFXMLWithController(URL fxmlURL) {
		this.isFXMLWithController=true;
		this.fxmlURL = fxmlURL;
	}

	@Override
	protected void initContent(AnchorPane pane) {
		if(this.isFXMLWithController==false) {
			//FXML中未设置controller，通过setFXML直接传入
			if(this.controller==null||this.fxmlURL==null) {
				throw new RuntimeException("未设置FXML参数，请先调用setFXML设置后再进行初始化！");
			}
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
				fxmlLoader.setController(controller);

				fxmlParent = fxmlLoader.load();
				UIUtil.setAnchor(fxmlParent, 0D, 0D, 0D, 0D);
				pane.getChildren().add(fxmlParent);
			} catch (Exception e) {
				logger.error("打开模块错误",e);
				UIUtil.openError(null, "打开模块错误:" + e.getMessage());
			}
		}else {
			//FXML中设置了controller，通过内置的ControllerFactory从Spring中获取
			if(this.fxmlURL==null) {
				throw new RuntimeException("未设置FXML参数，请先调用setFXMLWithController设置后再进行初始化！");
			}
			try {
				fxmlParent=FXMLUtil.getParentWithSpringControllerFactory(fxmlURL);
				UIUtil.setAnchor(fxmlParent, 0D, 0D, 0D, 0D);
				pane.getChildren().add(fxmlParent);
			} catch (Exception e) {
				logger.error("打开模块错误",e);
				UIUtil.openError(null, "打开模块错误:" + e.getMessage());
			}
		}
		

	}

	/* (non-Javadoc)
	 * @see net.javafxchina.xmdp.ui.widgets.view.BaseView#beforeClose()
	 */
	protected abstract boolean beforeClose();

}
