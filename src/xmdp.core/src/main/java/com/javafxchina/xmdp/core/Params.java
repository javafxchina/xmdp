package com.javafxchina.xmdp.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Victor
 *
 */
@Component
public class Params {
	@Value("${xmdp.isDebug}")
	boolean isDebug;

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
	
}
