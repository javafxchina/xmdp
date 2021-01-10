package net.javafxchina.xmdp.addon.h2db;

import java.io.File;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * UUID生成器
 * 
 * @version 1.0.0
 */
public class SIDCreator {
	/**
	 * 最长128位，最后11位"_XXXXX.EEEE"累加计数和扩展名占位
	 */
	public  static int MAXLEN = 117;
	
	/**
	 * 获取随机UUID
	 * 
	 * @return 随机UUID
	 */
	public static String getUUID() {
		// 除了数字和字母外的均替换掉
		UUID uuid = UUID.randomUUID();
		String regEx = "[^a-zA-Z0-9_]";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(uuid.toString());
		String id = matcher.replaceAll("");
		return id;
	}

	/**
	 * yyMMdd+hhmmssSSS+UUID+原主文件名，长度不超过{@link #MAXLEN}位，<br>
	 * @param time
	 *            年月日yyMMdd格式的时间串
	 * @param fileName
	 *            供参考使用的文件名，也可以为空字符串
	 * @return 正常返回一个理论上的唯一编号字符串
	 * @throws Exception
	 *             若异常则抛出
	 */
	public static  String getTimestampUUID(String fileName) {
		String time=DateTimeUtil.getCurretnFullTime("yyMMddHHmmss");
		
		String sourceName = "";
		int indexOfSep = fileName.lastIndexOf(File.separatorChar);
		int indexOfDot = fileName.lastIndexOf(".");
		if (indexOfDot == -1 || indexOfDot < indexOfSep) {
			indexOfDot = fileName.length();
		}
		try {
			sourceName = fileName.substring(indexOfSep + 1, indexOfDot);
			if (sourceName == null || sourceName.trim().length() == 0) {
				sourceName = fileName;
			}
		} catch (Exception e) {
			sourceName = fileName;
		}
		String randomName = UUID.randomUUID().toString();
		String tmpName =  time + randomName + "_"+sourceName;

		
		String regEx = "[^a-zA-Z0-9_]";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(tmpName);
		String newName = matcher.replaceAll("");

		if (newName.length() > MAXLEN) {// 最多MAXLEN位，
			return newName.substring(0, MAXLEN);
		} else {
			return newName;
		}
	}

	public static void main(String[] args) {
		for (int i = 1; i <10100; i++) {
//			String string = SIDCreator.getTimestampUUID("");
			String string =SIDCreator.getUUID();
			System.err.println(string);
		}

	}
}
