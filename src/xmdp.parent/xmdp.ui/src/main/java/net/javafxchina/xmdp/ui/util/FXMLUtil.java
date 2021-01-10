package net.javafxchina.xmdp.ui.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import net.javafxchina.xmdp.ui.dk.ControllerFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * 从工作目录下获取FXML文件资源
 * 
 * @author Victor
 *
 */
public class FXMLUtil {
	private static Logger logger = LoggerFactory.getLogger(FXMLUtil.class);

	/**
	 * 从指定的fxml文件中获取controller类
	 * 
	 * @param fxmlPath
	 *            fxml文件名
	 * @return controller类
	 */
	public static Class<?> getControllerClass(String fxmlPath) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			URL location = FXMLUtil.class.getResource(fxmlPath);
			if (location == null) {
				location = new URL("file", null, fxmlPath);
			}
			Document document = builder.parse(location.openStream());
			NamedNodeMap attributes = document.getDocumentElement().getAttributes();
			String fxControllerClassName = null;
			for (int i = 0; i < attributes.getLength(); i++) {
				Node item = attributes.item(i);
				if (item.getNodeName().equals(FXMLLoader.FX_NAMESPACE_PREFIX + ":" + FXMLLoader.CONTROLLER_KEYWORD)) {
					fxControllerClassName = item.getNodeValue();
				}
			}
			if (fxControllerClassName != null)
				return ClassLoader.getSystemClassLoader().loadClass(fxControllerClassName);
		} catch (ParserConfigurationException | SAXException | IOException | ClassNotFoundException ex) {
			logger.error("获取fxml文件:" + fxmlPath + "中的control失败", ex);
		}
		return null;
	}

	/**
	 * 通过 fxml的文件名获取Parent对象，注意其Controller由标准的FX机制产生
	 * 
	 * @param fileName
	 *            fxml文件名
	 * @return parent对象
	 * @throws Exception
	 *             当 fxml文件不存在或格式不正确时抛出此异常
	 */
	public static Parent getParentByFXML(String fileName) throws Exception {
		URL deskTopUrl = new URL("file", null, ResourceUtil.getFxmlFileByFileName(fileName).getAbsolutePath());
		return FXMLLoader.load(deskTopUrl);
	}

	/**
	 * 获取指定FXML文件名对应的URL
	 * 
	 * @param fileName
	 *            FXML文件名
	 * @return 对应的URL
	 * @throws Exception
	 *             当 fxml文件不存在或格式不正确时抛出此异常
	 */
	public static URL getURLByFXML(String fileName) throws Exception {
		String fxml = ResourceUtil.getFxmlFileByFileName(fileName).getAbsolutePath();
		URL deskTopUrl = new URL("file", null, fxml);
		return deskTopUrl;
	}

	/**
	 * 通过 fxml的文件名获取Root节点,注意其Controller是受Spring管理的
	 * 
	 * @param fileName
	 *            fxml文件名
	 * @return parent对象
	 * @throws Exception
	 *             当 fxml文件不存在或格式不正确时抛出此异常
	 */
	public static Parent getParentWithSpringControllerFactory(String fileName) throws Exception {
		String fxml = ResourceUtil.getFxmlFileByFileName(fileName).getAbsolutePath();
		URL deskTopUrl = new URL("file", null, fxml);
		FXMLLoader loader = new FXMLLoader(deskTopUrl);
		loader.setControllerFactory(new ControllerFactory());
		Parent root = loader.load();
		return root;
	}

	/**
	 * 通过 fxml的URL获取Root节点,注意其Controller是受Spring管理的
	 * 
	 * @param fileURL
	 *            fxml文件的URL
	 * @return parent对象
	 * @throws Exception
	 *             当 fxml文件不存在或格式不正确时抛出此异常
	 */
	public static Parent getParentWithSpringControllerFactory(URL fileURL) throws Exception {
		FXMLLoader loader = new FXMLLoader(fileURL);
		loader.setControllerFactory(new ControllerFactory());
		Parent root = loader.load();
		return root;
	}

	public static List<javafx.scene.Node> getChildrenNodes(javafx.scene.Node parent) {
		if (parent instanceof Pane) {
			List<javafx.scene.Node> re = new ArrayList<javafx.scene.Node>();
			Pane pa = (Pane) parent;
			for (javafx.scene.Node node : pa.getChildren()) {
				re.add(node);
				 List<javafx.scene.Node> children=getChildrenNodes(node);
				 if(children!=null) {
					 re.addAll(children);
				 }
			}
			return re;
		} else {
			return null;
		}

	}

}
