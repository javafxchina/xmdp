package net.javafxchina.xmdp.ui.dk.test;

import net.javafxchina.xmdp.ui.dk.EntryDefineInfo;
import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.ModuleInfo;
import net.javafxchina.xmdp.ui.dk.ExeRunner;

public class ExeRunnerTest {
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		ModuleInfo mi=new EntryDefineInfo().new ModuleInfo();
		mi.type=mi.TYPE_EXTERN_EXE;
		mi.uri="notepad.exe";
		ExeRunner runner=new ExeRunner(mi);
		String info=runner.run("");
		System.out.println(info);
	}
}
