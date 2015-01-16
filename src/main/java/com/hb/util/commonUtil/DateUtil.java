package com.hb.util.commonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @date 2014-11-15 下午4:36:07 备注：
 */
public class DateUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

	/** 时间格式：yyyy-MM-dd  */
	public static final String FORMATER_YYYY_MM_DD = "yyyy-MM-dd";
	
	/** 时间格式：yyyy-MM-dd HH:mm */
	public static final String FORMATER_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	
	/** 时间格式：yyyy-MM-dd HH:mm:ss */
	public static final String FORMATER_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日
	 */
	public final static int INTERVAL_DAY = 1;
	/**
	 * 周
	 */
	public final static int INTERVAL_WEEK = 2;
	/**
	 * 月
	 */
	public final static int INTERVAL_MONTH = 3;
	/**
	 * 年
	 */
	public final static int INTERVAL_YEAR = 4;
	/**
	 * 小时
	 */
	public final static int INTERVAL_HOUR = 5;
	/**
	 * 分钟
	 */
	public final static int INTERVAL_MINUTE = 6;
	/**
	 * 秒
	 */
	public final static int INTERVAL_SECOND = 7;

	/**
	 * date = 1901-01-01
	 */
	public final static Date tempDate = new Date(new Long("-2177481952000"));;

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
		return formatFromDate(date,FORMATER_YYYY_MM_DD);
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
	public static String formatFromDate(Date date,String formater) {
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
	public static String formatFromString(String s,String formater) {
		DateFormat df = new SimpleDateFormat(formater);
		return df.format(s);
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
	public static Date stringToDate(String str, String format) {
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

	/**
	 * 测试是否是当天
	 * 
	 * @param date
	 *            - 某一日期
	 * @return true-今天, false-不是
	 */
	@SuppressWarnings("deprecation")
	public static boolean isToday(Date date) {
		Date now = new Date();
		boolean result = true;
		result &= date.getYear() == now.getYear();
		result &= date.getMonth() == now.getMonth();
		result &= date.getDate() == now.getDate();
		return result;
	}

	/**
	 * 两个日期相减，取天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long DaysBetween(Date date1, Date date2) {
		if (date2 == null)
			date2 = new Date();
		long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 比较两个日期 if date1<=date2 return true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(String date1, String date2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = format.parse(date1);
			Date d2 = format.parse(date2);
			return !d1.after(d2);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 字符型转换成日期型
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static Date dateFormat(String date, String dateFormat) {
		if (date == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		if (date != null) {
			try {
				return format.parse(date);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 使用默认格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @author Robin Chang
	 * @param date
	 * @return
	 */
	public static Date dateFormat(String date) {
		return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期型转换成字符串
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateFormat(Date date, String dateFormat) {
		if (date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		if (date != null) {
			return format.format(date);
		}
		return "";
	}

	/**
	 * 由于生日增加保密属性，现决定1900为保密对应值，如果遇到1900的年份，则隐掉年份
	 * 
	 * @param date
	 * @param dateFormat
	 * @return 不保密显示1981-12-01保密则显示`12-01
	 */
	public static String birthdayFormat(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat format = null;
		if (date.before(tempDate)) {
			format = new SimpleDateFormat("MM-dd");
		} else {
			format = new SimpleDateFormat("yyyy-MM-dd");
		}
		if (date != null) {
			return format.format(date);
		}
		return "";
	}

	/**
	 * 使用默认格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static boolean isExpiredDay(Date date1) {
		long day = (new Date().getTime() - date1.getTime())
				/ (24 * 60 * 60 * 1000);
		if (day >= 1)
			return true;
		else
			return false;
	}

	public static Date getYesterday() {
		Date date = new Date();
		long time = (date.getTime() / 1000) - 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	public static Date getWeekAgo() {
		Date date = new Date();
		long time = (date.getTime() / 1000) - 7 * 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	public static String getDaysAgo(int interval) {
		Date date = new Date();
		long time = (date.getTime() / 1000) - interval * 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.format(date);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "";
	}

	public static Date getTomorrow() {
		Date date = new Date();
		long time = (date.getTime() / 1000) + 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	public static Date getBeforeDate(String range) {
		Calendar today = Calendar.getInstance();
		if ("week".equalsIgnoreCase(range))
			today.add(Calendar.WEEK_OF_MONTH, -1);
		else if ("month".equalsIgnoreCase(range))
			today.add(Calendar.MONTH, -1);
		else
			today.clear();
		return today.getTime();
	}

	public static Date getThisWeekStartTime() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.DAY_OF_WEEK, today.getFirstDayOfWeek());
		Calendar weekFirstDay = Calendar.getInstance();
		weekFirstDay.clear();
		weekFirstDay.set(Calendar.YEAR, today.get(Calendar.YEAR));
		weekFirstDay.set(Calendar.MONTH, today.get(Calendar.MONTH));
		weekFirstDay.set(Calendar.DATE, today.get(Calendar.DATE));
		return weekFirstDay.getTime();
	}

	public static String getToday(String format) {
		String result = "";
		try {
			Date today = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
			result = simpleFormat.format(today);
		} catch (Exception e) {
		}
		return result;
	}

	public static Date getStartDay(int year, int month) {
		Calendar today = Calendar.getInstance();
		today.clear();
		today.set(Calendar.YEAR, year);
		today.set(Calendar.MONTH, month - 1);
		today.set(Calendar.DAY_OF_MONTH, 1);
		return today.getTime();
	}

	public static List<Integer> getBeforeYearList(int before) {
		Calendar today = Calendar.getInstance();
		int theYear = today.get(Calendar.YEAR);
		List<Integer> list = new ArrayList<Integer>();
		for (int i = before; i >= 0; i--)
			list.add(theYear - i);

		return list;
	}

	/**
	 * 增加时间
	 * 
	 * @param interval
	 *            [INTERVAL_DAY,INTERVAL_WEEK,INTERVAL_MONTH,INTERVAL_YEAR,
	 *            INTERVAL_HOUR,INTERVAL_MINUTE]
	 * @param date
	 * @param n
	 *            可以为负数
	 * @return
	 */
	public static Date dateAdd(int interval, Date date, int n) {
		long time = (date.getTime() / 1000); // 单位秒
		switch (interval) {
		case INTERVAL_DAY:
			time = time + n * 86400;// 60 * 60 * 24;
			break;
		case INTERVAL_WEEK:
			time = time + n * 604800;// 60 * 60 * 24 * 7;
			break;
		case INTERVAL_MONTH:
			time = time + n * 2678400;// 60 * 60 * 24 * 31;
			break;
		case INTERVAL_YEAR:
			time = time + n * 31536000;// 60 * 60 * 24 * 365;
			break;
		case INTERVAL_HOUR:
			time = time + n * 3600;// 60 * 60 ;
			break;
		case INTERVAL_MINUTE:
			time = time + n * 60;
			break;
		case INTERVAL_SECOND:
			time = time + n;
			break;
		default:
		}

		Date result = new Date();
		result.setTime(time * 1000);
		return result;
	}

	/**
	 * 计算两个时间间隔
	 * 
	 * @param interval
	 *            [INTERVAL_DAY,INTERVAL_WEEK,INTERVAL_MONTH,INTERVAL_YEAR,
	 *            INTERVAL_HOUR,INTERVAL_MINUTE]
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int dateDiff(int interval, Date begin, Date end) {
		long beginTime = (begin.getTime() / 1000); // 单位：秒
		long endTime = (end.getTime() / 1000); // 单位: 秒
		long tmp = 0;
		if (endTime == beginTime) {
			return 0;
		}

		// 确定endTime 大于 beginTime 结束时间秒数 大于 开始时间秒数
		if (endTime < beginTime) {
			tmp = beginTime;
			beginTime = endTime;
			endTime = tmp;
		}

		long intervalTime = endTime - beginTime;
		long result = 0;
		switch (interval) {
		case INTERVAL_DAY:
			result = intervalTime / 86400;// 60 * 60 * 24;
			break;
		case INTERVAL_WEEK:
			result = intervalTime / 604800;// 60 * 60 * 24 * 7;
			break;
		case INTERVAL_MONTH:
			result = intervalTime / 2678400;// 60 * 60 * 24 * 31;
			break;
		case INTERVAL_YEAR:
			result = intervalTime / 31536000;// 60 * 60 * 24 * 365;
			break;
		case INTERVAL_HOUR:
			result = intervalTime / 3600;// 60 * 60 ;
			break;
		case INTERVAL_MINUTE:
			result = intervalTime / 60;
			break;
		case INTERVAL_SECOND:
			result = intervalTime / 1;
			break;
		default:
		}

		// 做过交换
		if (tmp > 0) {
			result = 0 - result;
		}
		return (int) result;
	}

	/**
	 * 当前年份
	 * 
	 * @return
	 */
	public static int getTodayYear() {
		int yyyy = Integer.parseInt(dateFormat(new Date(), "yyyy"));
		return yyyy;
	}

	public static Date getNow() {
		return new Date();
	}

	/**
	 * 把日期格式为rss格式兼容的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatRss(Date date) {
		if (date != null) {
			return dateFormat(date, "E, d MMM yyyy H:mm:ss") + " GMT";
		}
		return "";
	}

	/**
	 * 判断当前日期是否在两个日期之间
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            　结束时间
	 * @return　
	 */
	public static boolean betweenStartDateAndEndDate(Date startDate,
			Date endDate) {
		boolean bool = false;
		Date curDate = new Date();
		if (curDate.after(startDate)
				&& curDate.before(DateUtil.dateAdd(INTERVAL_DAY, endDate, 1))) {
			bool = true;
		}
		return bool;

	}

	/**
	 * 判断当前时间是否在在两个时间之间
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            　结束时间
	 * @return　
	 */
	public static boolean nowDateBetweenStartDateAndEndDate(Date startDate,
			Date endDate) {
		boolean bool = false;
		Date curDate = new Date();
		if (curDate.after(startDate) && curDate.before(endDate)) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 判断当前时间是否在date之后
	 * 
	 * @param date
	 * @return　
	 */
	public static boolean nowDateAfterDate(Date date) {
		boolean bool = false;
		Date curDate = new Date();
		if (curDate.after(date)) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 判断二个日期相隔的天数,结束时间为null时，，取当前时间
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            　结束时间
	 * @return　
	 */
	public static int getBetweenTodaysStartDateAndEndDate(Date startDate,
			Date endDate) {
		int betweentoday = 0;
		if (startDate == null) {
			return betweentoday;
		}
		if (endDate == null) {
			Calendar calendar = Calendar.getInstance();
			String year = new Integer(calendar.get(Calendar.YEAR)).toString();
			String month = new Integer((calendar.get(calendar.MONTH) + 1))
					.toString();
			String day = new Integer(calendar.get(calendar.DAY_OF_MONTH))
					.toString();
			String strtodaytime = year + "-" + month + "-" + day;
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				endDate = formatter.parse(strtodaytime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (endDate.after(startDate)) {
			betweentoday = (int) ((endDate.getTime() - startDate.getTime()) / 86400000);
		} else {
			betweentoday = (int) ((startDate.getTime() - endDate.getTime()) / 86400000);
		}
		return betweentoday;
	}

	/**
	 * 取得指定长度日期时间字符串{不含格式}
	 * 
	 * @param format
	 *            时间格式由常量决定 8: 　YYMMDDHH 8位 10:　YYMMDDHHmm 10位 12:　YYMMDDHHmmss
	 *            12位 14:　YYYYMMDDHHmmss 14位 15:　YYMMDDHHmmssxxx 15位 (最后的xxx
	 *            是毫秒)
	 */
	public static String getTime(int format) {
		StringBuffer cTime = new StringBuffer(10);
		Calendar time = Calendar.getInstance();
		int miltime = time.get(Calendar.MILLISECOND);
		int second = time.get(Calendar.SECOND);
		int minute = time.get(Calendar.MINUTE);
		int hour = time.get(Calendar.HOUR_OF_DAY);
		int day = time.get(Calendar.DAY_OF_MONTH);
		int month = time.get(Calendar.MONTH) + 1;
		int year = time.get(Calendar.YEAR);
		if (format != 14) {
			if (year >= 2000)
				year = year - 2000;
			else
				year = year - 1900;
		}
		if (format >= 2) {
			if (format == 14)
				cTime.append(year);
			else
				cTime.append(getFormatTime(year, 2));
		}
		if (format >= 4)
			cTime.append(getFormatTime(month, 2));
		if (format >= 6)
			cTime.append(getFormatTime(day, 2));
		if (format >= 8)
			cTime.append(getFormatTime(hour, 2));
		if (format >= 10)
			cTime.append(getFormatTime(minute, 2));
		if (format >= 12)
			cTime.append(getFormatTime(second, 2));
		if (format >= 15)
			cTime.append(getFormatTime(miltime, 3));
		return cTime.toString();
	}

	/**
	 * 　产生任意位的字符串
	 * 
	 * @param time
	 *            要转换格式的时间
	 * @param format
	 *            　转换的格式
	 * @return　String 转换的时间
	 */
	private static String getFormatTime(int time, int format) {
		StringBuffer numm = new StringBuffer();
		int length = String.valueOf(time).length();
		if (format < length)
			return null;
		for (int i = 0; i < format - length; i++) {
			numm.append("0");
		}
		numm.append(time);
		return numm.toString().trim();
	}

	/**
	 * 根据生日去用户年龄
	 * 
	 * @param birthday
	 * @return int
	 * @exception
	 * @author 豆皮
	 * @Date Apr 24, 2008
	 */
	public static int getUserAge(Date birthday) {
		if (birthday == null)
			return 0;
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		cal.setTime(birthday);// 给时间赋值
		int yearBirth = cal.get(Calendar.YEAR);
		return yearNow - yearBirth;
	}

	/**
	 * 将int型时间(1970年至今的秒数)转换成Date型时间
	 * 
	 * @param unixTime
	 *            1970年至今的秒数
	 * @return
	 * @author 郑卿
	 */
	public static Date getDateByUnixTime(int unixTime) {
		return new Date(unixTime * 1000L);
	}

	/**
	 * 将Date型时间转换成int型时间(1970年至今的秒数)
	 * 
	 * @param unixTime
	 *            1970年至今的秒数
	 * @return
	 * @author 郑卿
	 */
	public static int getUnixTimeByDate(Date date) {
		return (int) (date.getTime() / 1000);
	}

	public static Date getNextDay(Date date) {
		long time = (date.getTime() / 1000) + 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;

	}

	/**
	 * @param date
	 * @return 复制新Date，不改变参数
	 */
	public static Date nextDay(Date date) {
		Date newDate = (Date) date.clone();
		long time = (newDate.getTime() / 1000) + 60 * 60 * 24;
		newDate.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newDate = format.parse(format.format(newDate));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return newDate;

	}

	@SuppressWarnings("unused")
	public static Date getNowTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateStr = dateFormat(date);
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getTomorrow(Date date1) {

		// 创建当前时间对象
		Calendar now = Calendar.getInstance();
		now.setTime(date1);
		// 日期[+1]day
		now.add(Calendar.DATE, 1);
		return now.getTime();
	}

	public static Date getWeekAgo(Date date) {
		Date newDate = (Date) date.clone();
		long time = (newDate.getTime() / 1000) - 60 * 60 * 24 * 7;
		newDate.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newDate = format.parse(format.format(newDate));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return newDate;
	}

	public static Date getDatebyTime(Date date, int n) {
		String str = DateUtil.dateFormat(date, "yyyy-MM-dd");
		String[] strs = str.split("-");
		int month = Integer.parseInt(strs[1]);
		int monthnow = (month + n) % 12;
		int year = Integer.parseInt(strs[0]) + (month + n) / 12;
		str = String.valueOf(year) + "-" + String.valueOf(monthnow) + "-"
				+ strs[2];
		return DateUtil.dateFormat(str, "yyyy-MM-dd");
	}

	/**
	 * @param date
	 * @return 复制新Date，不改变参数
	 */
	public static Date yesterday(Date date) {
		Date newDate = (Date) date.clone();
		long time = (newDate.getTime() / 1000) - 60 * 60 * 24;
		newDate.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newDate = format.parse(format.format(newDate));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return newDate;
	}

	public static Date getYesterday(Date date) {
		long time = (date.getTime() / 1000) - 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	private static SimpleDateFormat format = null;

	@SuppressWarnings("unused")
	public static String getStringNowTime() {
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateStr = dateFormat(date);

		return dateStr;
	}

	/**
	 * 指定时间的秒数 指定时间零点的秒数加指定天数的秒数
	 * 
	 * @param time
	 *            时间
	 * @param range
	 *            天
	 * @return
	 */
	public static long getSpecifyTimeSec(long time, int range) {
		Date date = new Date(
				(time * 1000 + (23 - Calendar.ZONE_OFFSET) * 3600000)
						/ 86400000 * 86400000 - (23 - Calendar.ZONE_OFFSET)
						* 3600000);
		long zeroTime = date.getTime() / 1000;
		long specifyTime = range * 24 * 3600;
		return (zeroTime + specifyTime);
	}

	/**
	 * 将int型时间(1970年至今的秒数)转换成指定格式的时间
	 * 
	 * @param unixTime
	 *            1970年至今的秒数
	 * @param dateFormat
	 *            时间格式
	 * @return
	 * @author sky
	 */
	public static String formatDateByUnixTime(long unixTime, String dateFormat) {
		return dateFormat(new Date(unixTime * 1000), dateFormat);
	}
	
	
	 /**  
     * 以友好的方式显示时间  
     *   
     * @param sdate  
     * @return  
     */  
    public static String friendly_time(String sdate) {  
    	
        Date time = stringToDate(sdate,FORMATER_YYYY_MM_DD_HH_MM);;  
        
        if (time == null) {  
            return "Unknown";  
        }  
        String ftime = "";  
        Calendar cal = Calendar.getInstance();  
  
        // 判断是否是同一天  
        String curDate = formatFromDate(cal.getTime(),FORMATER_YYYY_MM_DD);
        String paramDate = formatFromDate(time,FORMATER_YYYY_MM_DD); 
        if (curDate.equals(paramDate)) {  
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);  
            if (hour == 0)  
                ftime = Math.max(  
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)  
                        + "分钟前";  
            else  
                ftime = hour + "小时前";  
            return ftime;  
        }  
  
        long lt = time.getTime() / 86400000;  
        long ct = cal.getTimeInMillis() / 86400000;  
        int days = (int) (ct - lt);  
        if (days == 0) {  
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);  
            if (hour == 0)  
                ftime = Math.max(  
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)  
                        + "分钟前";  
            else  
                ftime = hour + "小时前";  
        } else if (days == 1) {  
            ftime = "昨天";  
        } else if (days == 2) {  
            ftime = "前天";  
        } else if (days > 2 && days <= 10) {  
            ftime = days + "天前";  
        } else if (days > 10) {  
            ftime = formatFromDate(time,FORMATER_YYYY_MM_DD);  
        }  
        return ftime;  
    }  
    
    /**  
     * 判断给定字符串时间是否为今日  
     *   
     * @param sdate  
     * @return boolean  
     */  
    public static boolean isToday(String sdate) {  
        boolean b = false;  
        Date time = stringToDate(sdate,FORMATER_YYYY_MM_DD_HH_MM);  
        Date today = new Date();  
        if (time != null) {  
            String nowDate = formatFromDate(today);  
            String timeDate = formatFromDate(time);  
            if (nowDate.equals(timeDate)) {  
                b = true;  
            }  
        }  
        return b;  
    } 
    
    
    /**
     * 毫秒转格式为：days + "天，" + hours + "小时，" + minutes + "分"
     * @param mss 毫秒
     * @return
     */
    public static String formatDuring(long mss) {  
        long days = mss / (1000 * 60 * 60 * 24);  
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
        long seconds = (mss % (1000 * 60)) / 1000;  
        return days + "天，" + hours + "小时，" + minutes + "分";  
    }  
  
    /**
     * 获得两个时间相差的友好时间显示方案：days + "天，" + hours + "小时，" + minutes + "分"
     * @param begin
     * @param end
     * @return
     */
    public static String formatDuring(Date begin, Date end) {  
        return formatDuring(end.getTime() - begin.getTime());  
    } 

	
	public static void main(String[] args) {
		Date date1 = dateFormat("1981-01-01 00:00:00");
		Date date2 = dateFormat("1900-12-31 00:00:00");
		System.out.println(birthdayFormat(date1));
		
		System.out.println(birthdayFormat(date2));
		
		
		System.out.println(formatFromString("yyyy-MM-dd", "2012-01-05"));
	}

}
