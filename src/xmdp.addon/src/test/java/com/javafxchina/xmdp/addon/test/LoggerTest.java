package com.javafxchina.xmdp.addon.test;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {
	static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
	@Test
	public void test() {
		
		logger.trace("trace");
		logger.debug("debug");
		logger.warn("warn");
		logger.info("info");
		logger.error("error");
	}

}
