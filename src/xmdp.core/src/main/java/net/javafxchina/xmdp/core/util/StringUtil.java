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
	 * 是否为合法的日期表达式YYYY-MM-DD
	 * 
	 * @param timeValue
	 *            待判断的日期表达式
	 * @return 若合法则返回true，否则为false
	 */
	public static boolean isValidDateTime(String timeValue) {
		// 带平年闰年YYYY-MM-DD格式的日期验证正则表达式
		Pattern pattern = Pattern
				.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
		Matcher matcher = pattern.matcher(timeValue);
		return matcher.matches();
	}
	/**是否为合法的日期表达式YYYY-MM-DD,其中的横线分隔符由实际的sep来指定
	 * @param timeValue 待分辨的时间字符串
	 * @param sep YYYY、MM、DD之间的分隔符
	 * @return 若合法则返回true，否则为false
	 */
	public static boolean isValidDateTime(String timeValue,String sep) {
		if(sep!=null&&sep.equals("-")) {
			return isValidDateTime(timeValue);
		}else if(sep==null||sep.trim().length()==0) {
			if(timeValue!=null&&timeValue.length()==8) {
				String tmpYYYY=timeValue.substring(0, 4);
				String tmpMM=timeValue.substring(4,6);
				String tmpDD=timeValue.substring(6,8);
				String tmp=tmpYYYY+"-"+tmpMM+"-"+tmpDD;
				return isValidDateTime(tmp);
			}else {
				return false;
			}
		}else {
			String tmp=timeValue.replace(sep, "-");
			return isValidDateTime(tmp);
		}
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
		
		System.out.println(isValidDateTime("20180902",""));
		System.out.println(isValidDateTime("2018090a",""));
		System.out.println(isValidDateTime("2018090",""));
		System.out.println(isValidDateTime("2018-09-02"));
		System.out.println(isValidDateTime("2018.09.02","."));
		System.out.println(isValidDateTime("2018.02.30","."));
		System.out.println(isValidDateTime("2018.02.28","."));
	}

}
