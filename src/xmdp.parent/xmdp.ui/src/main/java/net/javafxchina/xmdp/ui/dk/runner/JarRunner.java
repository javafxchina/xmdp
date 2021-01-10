package net.javafxchina.xmdp.ui.dk.runner;

import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.*;
import net.javafxchina.xmdp.ui.util.ResourceUtil;

/**
 * 外部jar包运行器
 * @author Victor
 *
 */
public class JarRunner {
	private ModuleInfo moduleInfo;

	public JarRunner(ModuleInfo module) {
		this.moduleInfo = module;
		if (!moduleInfo.type.equalsIgnoreCase(ModuleInfo.TYPE_EXTERN_JAR)) {
			throw new RuntimeException("JarRunner不支持的模块类型：" + moduleInfo.type);
		}
		if (!moduleInfo.uri.endsWith(".jar")) {
			throw new RuntimeException("JarRunner不支持的运行文件：" + moduleInfo.uri);
		}
	}

	public void run() throws Exception {
		String filePath=ResourceUtil.getFullFilePathByRelativePath(moduleInfo.uri);
		String cmd=
				"java -jar "+filePath;
		Runtime.getRuntime().exec(cmd);
	}

}
