package net.javafxchina.xmdp.ui.widgets.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * webView的控制器
 * 
 * @author Victor
 *
 */
public class WebBaseViewController implements Initializable {
	@FXML
	protected WebView webView;
	protected WebEngine webEngine;
	protected String url;
	protected WebBaseView webBaseView;

	public WebBaseViewController(String uri, WebBaseView webBaseView) {
		this.url = uri;
		this.webBaseView = webBaseView;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadHTML();
	}

	protected void loadHTML() {
		if (!url.startsWith("http") && !url.startsWith("https") && !url.startsWith("file")) {
			url = "http://" + url;
		}
		webEngine = webView.getEngine();
		System.out.println("开始加载URL:" + url);

		webEngine.load(url);
		// load是异步返回，加载完毕后调用此方法
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (newValue == State.SUCCEEDED) {
					afterLoad();
				}
			}
		});

	}

	/**
	 * 在网页加载完毕后调用此方法,实际实现是调用WebView界面类的afterLoad方法
	 * @see net.javafxchina.xmdp.ui.widgets.view.WebBaseView#afterLoad()
	 */
	protected void afterLoad() {
		webBaseView.afterLoad();
	}
}
