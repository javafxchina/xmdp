package net.javafxchina.xmdp.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期时间处理工具类
 *
 * @author Victor
 *
 */
public class DateTimeUtil {
	/**
	 * 获取当前日期的完整字符串值
	 * 
	 * @return 当前时间的字符串值,格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		Date dt = new Date();
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String s = df.format(dt);
		return s;
	}

	/**
	 * 获取当前时间的完整字符串值
	 * 
	 * @return 当前时间的字符串值,格式为"HH:mm:ss SSS"
	 */
	public static String getCurrentTime() {
		Date dt = new Date();
		java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm:ss SSS");
		String s = df.format(dt);
		return s;
	}

	/**
	 * 获取当前时间的完整字符串值
	 * 
	 * @return 当前时间的字符串值,格式为"yyyy-MM-dd HH:mm:ss.SSS"
	 */
	public static String getCurrentFullTime() {
		java.util.Date current = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String timeString = sdf.format(current);
		return timeString;
	}
	/**
	 * 获取当前时间的完整字符串值
	 * @param pattern 例如yyyy-MM-dd HH:mm:ss.SSS
	 * @return 当前时间的字符串值
	 */
	public static String getCurretnFullTime(String pattern) {
		java.util.Date current = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String timeString = sdf.format(current);
		return timeString;
	}

	/**
	 * 获取指定时间的字符串值
	 * 
	 * @param date
	 *            待获取字符串值的时间
	 * @return 转换后的字符串值,格式为"yyyy-MM-dd[ HH:mm:ss[.SSS]]"，其中[]部分可以省略
	 */
	public static String getDateString(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String timeString = sdf.format(date);
		return timeString;
	}

	/**
	 * 将字符串格式化为日期
	 * 
	 * @param timeString
	 *            日期时间字符串，格式必须为"yyyy-MM-dd[ HH:mm:ss[.SSS]]"，其中[]部分可以省略
	 * @return 对应的Date对象
	 * @throws Exception
	 *             如果格式不符合则会抛出异常
	 */
	public static Date getDateFromString(String timeString) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		if (timeString.length() == 10) {
			timeString += " 00:00:00.000";
		} else if (timeString.length() == 19) {
			timeString += ".000";
		}
		return sdf.parse(timeString);
	}

	/**
	 * 将时间字符串转化为日历对象
	 * 
	 * @param timeString
	 *            时间字符串,格式必须为"yyyy-MM-dd[ HH:mm:ss[.SSS]]"，其中[]部分可以省略
	 * @return 转化后的日历对象
	 */
	public static Calendar parseDateTime(String timeString) {
		Calendar cal = null;
		cal = new GregorianCalendar();
		Date date;
		try {
			date = getDateFromString(timeString);
			cal.setTime(date);
			return cal;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 获取时间字符串中的日期
	 * 
	 * @param timeString
	 *            时间字符串,格式必须为"yyyy-MM-dd[ HH:mm:ss[.SSS]]"，其中[]部分可以省略
	 * @return 日期
	 */
	public static int getDay(String timeString) {
		Calendar cal = parseDateTime(timeString);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 获取时间字符串中的月份
	 * 
	 * @param timeString
	 *            时间字符串,格式必须为"yyyy-MM-dd[ HH:mm:ss[.SSS]]"，其中[]部分可以省略
	 * @return 月份
	 */
	public static int getMonth(String timeString) {
		Calendar cal = parseDateTime(timeString);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取时间字符串所代表的日期是一周中的第几天
	 * 
	 * @param timeString
	 *            时间字符串,格式必须为"yyyy-MM-dd[ HH:mm:ss[.SSS]]"，其中[]部分可以省略
	 * @return 第几天（注意，周日是第1天，周一是第2天，依次类推）
	 */
	public static int getWeekDay(String timeString) {
		Calendar cal = parseDateTime(timeString);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取时间字符串所代表的日期是星期几的中文字符串
	 * 
	 * @param timeString
	 *            时间字符串,格式必须为"yyyy-MM-dd[ HH:mm:ss[.SSS]]"，其中[]部分可以省略
	 * @return 星期几
	 */
	public static String getWeekDayName(String timeString) {
		String mName[] = { "日", "一", "二", "三", "四", "五", "六" };
		int iWeek = getWeekDay(timeString);
		iWeek = iWeek - 1;
		return mName[iWeek];
	}

	/**
	 * 获取时间字符串所代表的日期年份
	 * 
	 * @param timeString
	 *            时间字符串,格式必须为"yyyy-MM-dd[ HH:mm:ss[.SSS]]"，其中[]部分可以省略
	 * @return 年份
	 */
	public static int getYear(String timeString) {
		Calendar cal = parseDateTime(timeString);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取时间字符串所代表的日期是一年中的第几周
	 * 
	 * @param timeString
	 *            时间字符串,格式必须为"yyyy-MM-dd[ HH:mm:ss[.SSS]]"，其中[]部分可以省略
	 * @return 第几周
	 */
	public static int getWeekOfYear(String timeString) {
		Calendar cal = parseDateTime(timeString);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 日期相减
	 * 
	 * @param date2
	 *            被减数
	 * @param date1
	 *            减数
	 * @return 相差日期，如果date1大于date2则会返回负数
	 * @throws Exception
	 */
	public static long sub(String date2, String date1) throws Exception {
		java.util.Date beginDate = getDateFromString(date1);
		java.util.Date endDate = getDateFromString(date2);
		long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 格式化日期字符串，将形如2014-5-5的串格式化成2014-05-05
	 */
	public static String formatDate(String dateStr) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateStr);
		String newString = sdf.format(date);
		return newString;
	}
	
	public static Date[] toDate10Len(String value, String split) {
		String[] values = value.split(split);
		Date[] dates = new Date[values.length];
		for (int i = 0; i < values.length; i++) {
			dates[i] = toDate10Len(values[i]);
		}
		return dates;
	}
	
	public static Date toDate10Len(String value) {
		SimpleDateFormat dateFormat10Len = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat10Len.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date[] toDateTime(String value, String split) {
		String[] values = value.split(split);
		Date[] dates = new Date[values.length];
		for (int i = 0; i < values.length; i++) {
			dates[i] = toDateTime(values[i]);
		}
		return dates;
	}
	
	
	public static Date toDateTime(String value) {
		SimpleDateFormat dateFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateFormatDateTime.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date[] toDateTime(String value, String split, String appendTime) {
		String[] values = value.split(split);
		Date[] dates = new Date[values.length];
		for (int i = 0; i < values.length; i++) {
			dates[i] = toDateTime(values[i] + appendTime);
		}
		return dates;
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(DateTimeUtil.getCurrentDate());
		// System.out.println(DateTimeUtil.getCurrentTime());
		// String time = getCurrentFullTime();
		// System.out.println(time);
		// String timeStirng = "2013-12-28";
		// int year = getYear(timeStirng);
		// int weekDay = getWeekDay(timeStirng);
		// String weekdayName = getWeekDayName(timeStirng);
		// int weekOfYear = getWeekOfYear(timeStirng);
		// System.out.println(year + "年周[" + weekDay + "],星期" + weekdayName
		// + ",是第" + weekOfYear + "周");
		// java.util.Date date = null;
		// try {
		// date = getDateFromString(time);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println(getDateString(date));
		// System.out.println("===========");
		// System.out.println(DateTimeUtil.sub("2014-10-02","2014-09-30"));
	}
}
