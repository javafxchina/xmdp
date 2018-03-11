package com.javafxchina.xmdp.addon.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 使用XML配置的插件
 * @author Victor
 *
 */
public class TestAddon {
	private static Logger logger = LoggerFactory.getLogger(TestAddon.class);
	public void init(){
		logger.info("=================TestAddon init!=======================");
	}
	public void doit() {
		logger.info("=================TestAddon doit!=======================");
	}
}
