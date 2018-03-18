package net.javafxchina.xmdp.addon.test;


import org.junit.Test;

import net.javafxchina.xmdp.core.Params;

import net.javafxchina.xmdp.addon.AddonsLauncher;
public class AddonServiceSetTest {

	@Test
	public void test() throws Exception {
		AddonsLauncher serviceSet = AddonsLauncher.getInstance();
		serviceSet.init();
		Params params=(Params) serviceSet.getService("params");
		System.out.println("debug="+params.isDebug());
		System.out.println("xmdp.isDebug="+params.getParam("xmdp.isDebug"));
		System.out.println("xmdp.testPWD.PWD="+params.getParam("xmdp.testPWD.PWD"));
	}

}
