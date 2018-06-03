package net.javafxchina.xmdp.addon.test;


import org.junit.Test;


import net.javafxchina.xmdp.addon.AddonsLauncher;
import net.javafxchina.xmdp.core.XMDPParams;
public class AddonServiceSetTest {

	@Test
	public void test() throws Exception {
		AddonsLauncher serviceSet = AddonsLauncher.getInstance();
		serviceSet.init();
		XMDPParams params=(XMDPParams) serviceSet.getService("xmdpParams");
		System.out.println("debug="+params.isDebug());
		System.out.println("xmdp.isDebug="+params.getParam("xmdp.isDebug"));
		System.out.println("xmdp.testPWD.PWD="+params.getParam("xmdp.testPWD.PWD"));
	}

}
