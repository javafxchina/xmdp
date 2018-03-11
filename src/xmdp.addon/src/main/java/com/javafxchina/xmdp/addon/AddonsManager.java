package com.javafxchina.xmdp.addon;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.UrlResource;

public class AddonsManager {
	private static Logger logger = LoggerFactory.getLogger(AddonsManager.class);
	private AddonsLauncher springServiceSet;
	private String addonsPath = System.getProperty("user.dir") + File.separator + "addons";

	public AddonsManager() {
		logger.info("==================AddonsManager created==================");
		springServiceSet = AddonsLauncher.getInstance();
	}

	/**
	 * 加载插件Jar中的类
	 * 
	 * @return
	 */
	public ClassLoader discoverAddonJars() {

		List<URL> list = new ArrayList<URL>();

		File d = new File(addonsPath);
		if (d.isDirectory()) {
			logger.info("============= Starting the Addons Search=============");
			File[] f = d.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".jar") && pathname.isFile();
				}

			});
			logger.info("Found " + f.length + " addons");
			for (int i = 0; i < f.length; i++) {
				try {
					logger.info("Found a addon jar:" + f[i].getName());
					list.add(f[i].toURI().toURL());
				} catch (MalformedURLException e) {
					e.printStackTrace();
					logger.error("Analysis addon jar failed", e);
				}
			}
		} else {
			logger.warn("Have not found the addons directory:" + d.getPath());
		}

		URL[] urls = list.toArray(new URL[list.size()]);
		return new URLClassLoader(urls, AddonsManager.class.getClassLoader());
	}

	public void loadBean(ClassLoader classloader) {
		Enumeration<URL> resources;
		try {
			resources = classloader.getResources("addon.xml");

			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				UrlResource urlResource = new UrlResource(resource);

				DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
				BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
				reader.loadBeanDefinitions(urlResource);

				String[] beanIds = beanFactory.getBeanDefinitionNames();
				for (String beanId : beanIds) {
					BeanDefinition bdef = beanFactory.getMergedBeanDefinition(beanId);

					springServiceSet.registerBeanDefinition(beanId, bdef);
					logger.debug(
							"register beans in addon.xml:name=" + beanId + ";ClassName=" + bdef.getBeanClassName());
				}

			}
		} catch (Exception e) {
			logger.error("load the beans in addon.xml failed", e);
		}
	}
}
