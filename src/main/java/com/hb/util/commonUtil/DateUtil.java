package com.hb.util.commonUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SpringMVC+Hibernate +MySql+ EasyUI ---CRUD
 * 
 * @author 宗潇帅 类名称：DateUtil
 * @date 2014-11-15 下午4:36:07 备注：
 */
public class DateUtil {

	private static Log log = LogFactory.getLog(DateUtil.class);

	public static final String FORMATER_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMATER_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 返回java.sql.Date类型的当前时间
	 * 
	 * @return
	 */
	public static java.sql.Date getSqlDate() {
		return getSqlDate(new Date());
	}

	/**
	 * 返回java.sql.Date类型的时间
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 以"yyyy-MM-dd"格式来格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatFromDate(Date date) {
		return formatFromDate(FORMATER_YYYY_MM_DD, date);
	}

	public static Date getCurrentDate() {
		Date now = getCurrentDateTime();
		return strToDate(dateToStr(now));
	}

	public static String dateToStr(Date date) {
		String s = "";
		if (date == null) {
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			s = sdf.format(date);
		} catch (Exception e) {

		}

		return s;
	}

	public static Date strToDate(String s) {
		Date d = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			d = sdf.parse(s);
		} catch (Exception e) {

		}

		return d;
	}

	/**
	 * @return
	 */
	public static Date getCurrentDateTime() {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 按照给定的格式，格式化日期
	 * 
	 * @param formater
	 *            需要的格式，常用的例如"yyyy-MM-dd HH:mm:ss"
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatFromDate(String formater, Date date) {
		DateFormat df = new SimpleDateFormat(formater);
		return df.format(date);
	}

	/**
	 * 按照给定的格式，格式化日期
	 * 
	 * @param formater
	 *            需要的格式，常用的例如"yyyy-MM-dd HH:mm:ss"
	 * @param s
	 *            可格式化为日期的字符串
	 * @return
	 */
	public static String formatFromString(String formater, String s) {
		DateFormat df = new SimpleDateFormat(formater);
		return df.format(s);
	}

	public static void main(String[] args) {
		System.out.println(formatFromString("yyyy-MM-dd", "2012-01-05"));
	}

	/**
	 * 字符串转化为日期</br>
	 * 
	 * @param str
	 *            需要被转换为日期的字符串
	 * @param format
	 *            格式，常用的为 yyyy-MM-dd HH:mm:ss
	 * @return java.util.Date，如果出错会返回null
	 */
	public static Date StringToDate(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			if (log.isErrorEnabled()) {
				log.error("将字符串转换成日期时出错", e);
			}
		}
		return date;
	}

	/**
	 * 计算两个日期之间的天数</br> 任何一个参数传空都会返回-1</br> 返回两个日期的时间差，不关心两个日期的先后</br>
	 * 
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public static long getDaysBetweenTwoDate(Date dateStart, Date dateEnd) {
		if (null == dateStart || null == dateEnd) {
			return -1;
		}
		long l = Math.abs(dateStart.getTime() - dateEnd.getTime());
		l = l / (1000 * 60 * 60 * 24l);
		return l;
	}

	/**
	 * 判断某字符串是否是日期类型
	 * 
	 * @param strDate
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param date
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

}
