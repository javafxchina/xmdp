package net.javafxchina.xmdp.core.test;

import static org.junit.Assert.*;

import org.junit.Test;

import net.javafxchina.xmdp.core.Params;
import net.javafxchina.xmdp.core.Params.DESSecurityUtil;

public class ParamTest {

	@Test
	public void test() throws Exception {
		DESSecurityUtil des  = new Params().new DESSecurityUtil();
		String result = String.valueOf(des.encrypt("xman"));
		System.out.println(result);
		String actual="J6PlnFoM9UU=";
		System.out.println(des.decrypt(actual));
		assertEquals(result, actual);
	}

}
