package net.javafxchina.xmdp.ui.widgets.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.*;
import net.javafxchina.xmdp.ui.util.ResourceUtil;


/**
 * 模块显示基类 tab
 * @author Victor
 *
 */
public abstract class BaseView extends Tab {
	public static double tabIconWidth = 25;
	public static double tabIconheight = 25;
	protected ModuleInfo moduleInfo;
	protected AnchorPane rootPane;

	/**
	 * 请使用{@link #BaseView(ModuleInfo)}进行构造 
	 * @deprecated
	 */
	public BaseView() {

	}

	/**
	 * 构造方法，注意在构造完毕后，调用{@link #init()}方法
	 * @param moduleInfo
	 */
	public BaseView(ModuleInfo moduleInfo) {
		super(moduleInfo.showName);
		this.moduleInfo = moduleInfo;
		this.setId(moduleInfo.key);
		setOnCloseRequest(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				boolean close = beforeClose();
				if (!close) {
					event.consume();
				}
			}

		});
	}

	/**
	 * 初始化视图，包括设置tab图标，和tab页面内容等，其中会自动调用方法{@link #initContent(AnchorPane)},子类可以重写该方法来实现自己的界面
	 */
	public void init() {
		ImageView iv = ResourceUtil.createImageView(moduleInfo.iconpath, tabIconWidth, tabIconheight);
		setGraphic(iv);
		rootPane= new AnchorPane();
		setContent(rootPane);
		initContent(rootPane);
	}

	/**
	 * 初始化模块UI界面，注意此方法的由{@link #init()}方法触发，因此需调用该方法来触发此方法
	 * 
	 * @param pane
	 *            UI绘图底板
	 */
	protected abstract void initContent(AnchorPane pane);

	/**
	 * 在关闭tab之前调用此方法
	 * 
	 * @return 如果为true则可以正常关闭，如果为flase则无法关闭
	 */
	protected abstract boolean beforeClose();

}
