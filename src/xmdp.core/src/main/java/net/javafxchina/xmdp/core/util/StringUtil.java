package net.javafxchina.xmdp.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.debatty.java.stringsimilarity.Cosine;


/**
 * 字符串处理工具包
 * @author Victor
 *
 */
public class StringUtil {
	/**
	 * 是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 *是否是中文
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**在str中是否包含了给定的字符串
	 * @param str 待进行检索的字符串
	 * @param strings 待查找的子字符串
	 * @return
	 */
	public static boolean isInclude(String str,String ...strings) {
		String patternString=".*(";
		for(String s:strings) {
			patternString+="|"+s;
		}
		patternString=patternString.replaceFirst("\\|", "");
		patternString+=").*";
		Pattern pattern = Pattern.compile(patternString);
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 删除开头的非数字字符
	 */
	public static void deleteCharPrefix(StringBuffer sb) {
		int index = 0;
		boolean foundit = false;
		for (index = 0; index < sb.length(); index++) {
			String str = sb.charAt(index) + "";
			if (isNumeric(str)) {
				break;
			} else {
				foundit = true;
			}
		}
		if (foundit) {
			sb.delete(0, index);
		}
	}

	/**
	 * 删除结尾的非数字字符
	 * 
	 * @param sb
	 */
	public static void deleteCharSuffix(StringBuffer sb) {
		int index = sb.length() - 1;
		boolean foundit = false;
		for (; index >= 0; index--) {
			String str = sb.charAt(index) + "";
			if (isNumeric(str)) {
				break;
			} else {
				foundit = true;
			}
		}
		if (foundit) {
			sb.delete(index + 1, sb.length());
		}
	}

	/**
	 * 删除非中文前缀
	 * 
	 * @param sb
	 */
	public static void deleteNotCNPrefix(StringBuffer sb) {
		int index = 0;
		boolean foundit = false;
		for (index = 0; index < sb.length(); index++) {
			String str = sb.charAt(index) + "";
			if (isChinese(str)) {
				break;
			} else {
				foundit = true;
			}
		}
		if (foundit) {
			sb.delete(0, index);
		}
	}
	
	/**
	 * 删除非中文后缀
	 * 
	 * @param sb
	 */
	public static void deleteNotCNSuffix(StringBuffer sb) {
		int index = sb.length() - 1;
		boolean foundit = false;
		for (; index >= 0; index--) {
			String str = sb.charAt(index) + "";
			if (isChinese(str)) {
				break;
			} else {
				foundit = true;
			}
		}
		if (foundit) {
			sb.delete(index + 1, sb.length());
		}
	
	}

	/**
	 * 提取符合模式的分组值
	 * 
	 * @param source 待提取内容的源字符串
	 * @param rgex 待匹配的正则表达式
	 * @return
	 */
	public static List<String> getSubString(String source, String rgex) {
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile(rgex);// 匹配的模式
		Matcher m = pattern.matcher(source);
		int j = m.groupCount();
		m.find();
		for (int i = 1; i <= j; i++) {
			list.add(m.group(i));
		}
		return list;
	}
	/**
	 * 提取符合模式的分组值
	 * 
	 * @param source 待提取内容的源字符串
	 * @param rgex 待匹配的正则表达式
	 * @return
	 */
	public static List<String> getGroups(String source, String rgex) {
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile(rgex);// 匹配的模式
		Matcher m = pattern.matcher(source);
		while (m.find()){
			list.add(m.group());
        }
		return list;
	}
	/**
	 * 提取符合模式的分组值，若匹配到多个的话就返回第一个
	 * 
	 * @param source 待提取内容的源字符串
	 * @param rgex 待匹配的正则表达式
	 * @return
	 */
	public static String getFirstSubString(String source, String rgex) {
		Pattern pattern = Pattern.compile(rgex);// 匹配的模式
		Matcher m = pattern.matcher(source);
		while (m.find()) {
			return m.group(1);
		}
		return "";
	}
	
	public static String getSubStringAt(String source,String rgex,int index) {
		List<String>re=getSubString(source,rgex);
		if(index<0) {
			return null;
		}else if(index>=re.size()){
			return null;
		}else {
			return re.get(index);
		}
	}

	/**
	 * 获得余弦相似度
	 * @param source 源字符串
	 * @param target 目标字符串
	 * @param k 在分词时的最短词长，一般可以设置为1，注意必须比source和target的长度小，否则结果会出现NAN
	 * @return 相似度，如果k比source和target中任一个的长度大，则结果为NAN
	 */
	public static Double getCosineSimilarity(String source, String target, int k) {
		Cosine cosine = new Cosine(k);
		Map<String, Integer> profile1 = cosine.getProfile(source);
		Map<String, Integer> profile2 = cosine.getProfile(target);
		Double similarity = cosine.similarity(profile1, profile2);
		return similarity;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "Box 1 coordinates (x1, y1, x2, y2): 116 1,217 305 55";
		String rgex = "Box(.*?)coordinates \\(x1, y1, x2, y2\\):(.*?)$";
		System.out.println(getSubString(str, rgex));
		System.out.println(getFirstSubString(str, rgex));
		System.out.println(getSubStringAt(str, rgex,1));
		String str2="{\"name\":\"@{name}\",\"value\":\"@{value}\",\"valueFixed\":\"@{valueFixed}\",\"done\":\"@{done}\"}";
		String reg2="(@\\{.*?})";
		List<String>res=getGroups(str2, reg2);
		System.out.println(res.size()+":"+res);
	}

}
