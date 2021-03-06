package net.javafxchina.xmdp.addon.demo;

import java.net.URL;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.javafxchina.xmdp.core.TestBean;

/**
 * @author Victor
 *
 */
@Component
public class TestClientFXMLSpringViewCotroller implements Initializable {
	private static Logger logger = LoggerFactory.getLogger(TestClientFXMLSpringViewCotroller.class);
	@FXML
	private TextField textField;
	@FXML
	private Button helloButton;
	@Autowired
	private TestBean testBean;// 注意：从Spring中注入
	// private XDesktopContext
	// context=XDesktopContext.getInstance();//依赖注入替代了context.getService
	
	@PostConstruct
	public void init() {
		logger.info("TestClientFXMLSpringViewCotroller初始化成功");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textField.setText("请点击按钮say Hello FXMLSpring");
	}

	@FXML
	private void buttonClick() {
		// 依赖注入替代了context.getService
		// TestBean testBean=(TestBean) context.getService("testBean");
		String msg = testBean.test();
		textField.setText(msg + " 运行于使用FXML构造的带有Spring bean控制类的UI");
	}
}
