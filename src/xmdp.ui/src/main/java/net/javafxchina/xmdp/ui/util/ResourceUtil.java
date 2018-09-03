package net.javafxchina.xmdp.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.javafxchina.xmdp.ui.dk.ResourcePathDefine;
import net.javafxchina.xmdp.ui.dk.ResourcePathDefine.DesktopResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 资源操作工具类
 * @author Victor
 *
 */
public class ResourceUtil {
	private static final Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

	public static final Object PROPERTY_CONTROLLER = "Controller";
	public static final Object PROPERTY_LOADER = "Loader";
	private static Map<String, Image> images = new HashMap<String, Image>();

	/**
	 * 根据相对路径获取表示文件URL的字符串，如果relativePath为"img/test.jpg"，程序当前目录为d:/apppath，
	 * 则返回"file:/d:/apppath/img/test.jpg"，一般用于获取外部CSS文件的URL
	 * 
	 * @param relativePath
	 *            相对路径
	 * @return 表示文件的URL的字符串
	 */
	public static String getFileUrlByRelativePath(String relativePath) {
		File file = new File("");
		String curPath = file.getAbsolutePath();
		curPath = curPath.replace("\\", "/");
		String url = "file:/" + curPath + "/" + relativePath;
		return url;
	}
	/**
	 * 根据相对路径获取文件全名字符串，如果relativePath为"img/test.jpg"，程序当前目录为d:/apppath，
	 * 则返回"d:/apppath/img/test.jpg"
	 * 
	 * @param relativePath
	 *            相对路径
	 * @return 表示文件的URL的字符串
	 */
	public static String getFullFilePathByRelativePath(String relativePath) {
		File file = new File("");
		String curPath = file.getAbsolutePath();
		curPath = curPath.replace("\\", "/");
		return curPath + "/" + relativePath;
	}

	/**
	 * 获取模块图标
	 * 
	 * @param fileName
	 *            待超找的文件名
	 * @return 如果在资源文件夹下能找到对应文件名的图标则返回，否则返回默认模块图标
	 */
	public static Image getModuleIcon(String fileName) {
		return getImage(fileName, DesktopResource.DEFAULT_RESOURCE_ICON);
	}

	/**
	 * 获取模块图标
	 * 
	 * @param fileName
	 *            待超找的文件名
	 * @return 如果在资源文件夹下能找到对应文件名的图标则返回，否则返回默认模块图标
	 */
	public static Image getResourceIcon(String fileName) {
		return getImage(fileName, DesktopResource.DEFAULT_RESOURCE_ICON);
	}

	/**
	 * 通过文件名获取图像
	 * 
	 * @param fileName
	 *            文件名
	 * @return 图像对象
	 */
	public static Image getImage(String fileName) {
		if (images.get(fileName) != null) {
			return images.get(fileName);
		}else {
			return getImage(fileName, DesktopResource.DEFAULT_ICON);
		}
	}

	/**
	 * 通过文件名获取图像
	 * 
	 * @param fileName
	 *            文件名
	 * @param defaultFileName
	 *            找不到指定图像时的默认图像名
	 * @return 图像对象
	 */
	public static Image getImage(String fileName, String defaultFileName) {
		if (fileName == null || fileName.trim().equals("")) {
			fileName = defaultFileName;
		}
		if (images.get(fileName) != null) {
			return images.get(fileName);
		}
		File imgFile = getImageFileByFileName(fileName);
		if (logger.isDebugEnabled()) {
			logger.trace("图标路径：" + imgFile.getAbsolutePath());
		}
		if (!imgFile.exists()) {
			logger.error("未找到图标:" + imgFile.getAbsolutePath() + ",将使用默认图标");
			imgFile = getImageFileByFileName(defaultFileName);
		}
		InputStream in = null;
		Image image = null;
		try {
			in = new FileInputStream(imgFile);
			image = new Image(in);
		} catch (FileNotFoundException e) {// 默认图标都找不到
			throw new RuntimeException("程序资源缺失，无法正常启动:" + imgFile.getAbsolutePath());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("关闭文件输出流出现异常");
				}
			}
		}
		images.put(fileName, image);
		return image;
	}

	/**
	 * 将指定的图片转换为适合按钮或标签的imageview
	 * @param iconPath 图片路径
	 * @param buttonWidth 期望的宽度，如果为-1则不进行设置
	 * @param buttonHeight 期望的高度，如果为-1则不进行设置
	 * @return 转换后的ImageView
	 */
	public static  ImageView createImageView(String iconPath,double buttonWidth,double buttonHeight) {
		Image img = getResourceIcon(iconPath);
		ImageView iv = new ImageView(img);
		if(Math.abs(buttonWidth+1)>0.00000001) {
			iv.setFitWidth(buttonWidth);
		}
		if(Math.abs(buttonHeight+1)>0.00000001) {
			iv.setFitHeight(buttonHeight);
		}
		
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);
		return iv;
	}
	/**
	 * 将指定的图片转换为适合按钮或标签的imageview
	 * @param iconPath  图片路径
	 * @param buttonWidth 期望的宽度，如果为-1则不进行设置
	 * @param buttonHeight 期望的高度，如果为-1则不进行设置
	 * @param defaultIconPath 当图片找不到时的默认图片
	 * @return 转换后的ImageView
	 */
	public static ImageView createImageView(String iconPath, double buttonWidth, double buttonHeight,
			String defaultIconPath) {
		Image img = getImage(iconPath,defaultIconPath);
		ImageView iv = new ImageView(img);
		if(Math.abs(buttonWidth+1)>0.00000001) {
			iv.setFitWidth(buttonWidth);
		}
		if(Math.abs(buttonHeight+1)>0.00000001) {
			iv.setFitHeight(buttonHeight);
		}
		
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);
		return iv;
	}

	/**
	 * 通过 fxml的文件名获取Parent对象,并将对应的Controller和FXMLLoader存到返回的Parent对象的属性中，属性值分别为：PROPERTY_CONTROLLER和PROPERTY_LOADER
	 * 
	 * @param fileName
	 *            fxml文件名
	 * @return parent对象
	 * @throws Exception
	 *             当 fxml文件不存在或格式不正确时抛出此异常
	 */
	public static Parent getParentByFxmlFileName(String fileName) throws Exception {
		URL deskTopUrl = new URL("file", null, ResourceUtil.getFxmlFileByFileName(fileName).getAbsolutePath());
//		Parent re= FXMLLoader.load(deskTopUrl);
		FXMLLoader loader = new FXMLLoader(deskTopUrl);
		Parent re= loader.load();
		Object controller=loader.getController();
		re.getProperties().put(PROPERTY_CONTROLLER, controller);
		re.getProperties().put(PROPERTY_LOADER,loader);
		return re;
	}

	/**
	 * 通过图像文件名获取文件
	 * 
	 * @param fileName
	 *            图像文件名
	 * @return 图像文件
	 */
	public static File getImageFileByFileName(String fileName) {
		return getFileByFileName(ResourcePathDefine.DesktopResource.IMAGE_RELATIVE_PATH + File.separator + fileName);
	}

	// /**
	// * 通过CSS文件名获取文件
	// *
	// * @param fileName
	// * CSS文件名
	// * @return CSS文件
	// */
	// public static File getCssFileByFileName(String fileName) {
	// return getFileByFileName(ResourcePathDefine.CSSRELATIVEPATH
	// + File.separator + fileName);
	// }

	/**
	 * 通过fxml文件名获取文件
	 * 
	 * @param fileName
	 *            fxml文件名
	 * @return fxml文件
	 */
	public static File getFxmlFileByFileName(String fileName) {
		return getFileByFileName(fileName);
	}

	public static File getFileByFileName(String fileName) {
		// TODO 文件是放在user.dir下，还是放在程序的相对路径下
		// File File = new File(System.getProperty("user.dir")
		// + File.separator + fileName);
		File file = new File(fileName);
		return file.getAbsoluteFile();
	}


}
