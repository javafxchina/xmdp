package net.javafxchina.xmdp.ui.dk.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.javafxchina.xmdp.ui.dk.EntryDefineInfo.ModuleInfo;
import net.javafxchina.xmdp.ui.util.ResourceUtil;

/**
 * @author Victor
 *
 */
public class ExeRunner {
	private static Logger logger = LoggerFactory.getLogger(ExeRunner.class);
	private ModuleInfo moduleInfo;
	private String filePath;
	private File workDir;// 命令文件的工作路径，一般为命令文件的父文件夹

	public ExeRunner(ModuleInfo module) {
		this.moduleInfo = module;
		if (!moduleInfo.type.equalsIgnoreCase(ModuleInfo.TYPE_EXTERN_EXE)) {
			throw new RuntimeException("ExeRunner不支持的模块类型：" + moduleInfo.type);
		}
		if (!moduleInfo.uri.endsWith(".exe")) {
			throw new RuntimeException("ExeRunner不支持的运行文件：" + moduleInfo.uri);
		}
		filePath = ResourceUtil.getFullFilePathByRelativePath(moduleInfo.uri);
	}

	/**
	 * 相对路径，例如当前工作空间下的extern/ocr/xxxx.exe
	 * 
	 * @param path
	 */
	public ExeRunner(String path) {
		this.filePath = ResourceUtil.getFullFilePathByRelativePath(path);
		workDir = new File(filePath).getParentFile();
	}

	/**
	 * 使用给定的参数运行对应的可执行文件
	 * 
	 * @param params 运行参数
	 * @return
	 * @throws Exception
	 */
	public String run(String params) throws Exception {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(filePath + " " + params, null, workDir);// 如果workDir不对，可能会导致崩溃（找不到资源）
			printErrorMessage(p.getErrorStream());
			int re = p.waitFor();
			logger.info(filePath + " " + params + "执行结果为：" + re);
			
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					throw e;
				}
			}
		}

	}

	/**
	 * 吃掉错误输出信息流，避免出现因为错误流阻塞卡死
	 * @param input
	 */
	private void printErrorMessage( InputStream input) {
		new Thread(new Runnable() {
			public void run() {
				Reader reader = new InputStreamReader(input);
				BufferedReader bf = new BufferedReader(reader);
				String line = null;
				try {
					while ((line = bf.readLine()) != null) {
						logger.error(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					if(bf!=null) {
						try {
							bf.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	public File getWorkDir() {
		return workDir;
	}

	public void setWorkDir(File workDir) {
		this.workDir = workDir;
	}

}
