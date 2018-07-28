package net.javafxchina.xdmp.addon.demo;

import java.net.URL;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.javafxchina.xmdp.core.TestBean;
import net.javafxchina.xmdp.core.XMDPContext;


/**
 * @author Victor
 *
 */
public class TestClientViewCotroller implements Initializable {
	private static Logger logger = LoggerFactory.getLogger(TestClientViewCotroller.class);
	@FXML
	private TextField textField;
	@FXML
	private Button helloButton;
	
	private XMDPContext context=XMDPContext.getInstance();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textField.setText("请点击按钮say Hello");		
	}

	@FXML
	private void buttonClick() {
		logger.info("按钮被点击啦");
		//注意：从Spring中获取服务
		TestBean testBean=(TestBean) context.getService("testBean");
		String msg=testBean.test();
		textField.setText(msg);		
	}
}
