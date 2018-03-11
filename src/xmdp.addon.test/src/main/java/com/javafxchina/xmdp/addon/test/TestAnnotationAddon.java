package com.javafxchina.xmdp.addon.test;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 使用注解来声明的插件
 * @author Victor
 *
 */
@Component
public class TestAnnotationAddon {
	private static Logger logger = LoggerFactory.getLogger(TestAnnotationAddon.class);
	@PostConstruct
	public void init(){
		logger.info("=================TestAnnotationAddon init!=======================");
	}
	
	public void doit() {
		logger.info("=================TestAnnotationAddon doit!=======================");
	}
}
