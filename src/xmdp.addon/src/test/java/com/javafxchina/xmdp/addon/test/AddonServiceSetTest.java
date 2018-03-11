package com.javafxchina.xmdp.addon.test;


import org.junit.Test;

import com.javafxchina.xmdp.addon.AddonsLauncher;
import com.javafxchina.xmdp.core.Params;
public class AddonServiceSetTest {

	@Test
	public void test() throws Exception {
		AddonsLauncher serviceSet = AddonsLauncher.getInstance();
		serviceSet.init();
		Params params=(Params) serviceSet.getService("params");
		System.out.println(params.isDebug());
	}

}
