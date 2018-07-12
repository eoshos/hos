package io.eoshos.pc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	/**
	 * 添加记录 zyx@chuangke18.com 2016-08-30 am
	 */
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	// 常用的时间格式
	public static final String fullDate = "yyyy-MM-dd HH:mm:ss";
	// 简化了得时间格式
	public static final String simpleDate = "yyyy-MM-dd H:m:s";
	// 没有时分秒的时间格式
	public static final String DATE = "yyyy-MM-dd";

	public static final String FULLYMD = "yyyyMMdd";
	// 月日格式
	public static final String MMDD_DATE = "MM-dd";
	public static final String JOINDATE = "yyyyMMddHHmmss";
	private static final String[] ALL_DT_PATTERN = new String[] { fullDate };
	
	public static String dateStrFormat(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String str = format.format(date);
		return str;
	}

	public static Date dateParse(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String str = format.format(date);
		try {
			return format.parse(str);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static String dateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 hh:mm");
		String str = format.format(date);
		return str;
	}

	public static String dateStr(Date date, String f) {
		SimpleDateFormat format = new SimpleDateFormat(f);
		String str = format.format(date);
		return str;
	}

	public static String dateStr2(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	public static String dateStr5(Date date) {
		// 招商贷需求变更 短信时间由12小时改为24小时制 高才 2012/08/12 update start
		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒");
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		// 高才 2012/08/12 update end
		String str = format.format(date);
		return str;
	}

	public static String dateStr3(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(date);
		return str;
	}

	public static String dateStr4(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;

	}

	public static String dateStr6(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		String str = format.format(date);
		return str;
	}

	public static String dateStr7(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 获取yyyyMMdd格式当前日期
	 * 
	 * @return
	 */
	public static String getCurdate() {
		return dateStr7(new Date());
	}

	public static String dateStr8(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 获取HHMMss格式当前日期
	 * 
	 * @return
	 */
	public static String getCurtime() {
		return dateStr9(new Date());
	}

	public static String dateStr9(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		String str = format.format(date);
		return str;
	}

	/**
	 * 将秒转换成时间
	 * 
	 * @param times
	 * @return
	 */
	public static Date getDate(String times) {
		long time = Long.parseLong(times);
		return new Date(time * 1000);
	}

	public static String dateStr(String times) {
		return dateStr(getDate(times));
	}

	public static String dateStr2(String times) {
		return dateStr2(getDate(times));
	}

	public static String dateStr3(String times) {
		return dateStr3(getDate(times));
	}

	public static String dateStr4(String times) {
		return dateStr4(getDate(times));
	}

	public static String dateStr5(String times) {
		return dateStr5(getDate(times));
	}

	public static String dateStr7(String times) {
		return dateStr7(getDate(times));
	}

	public static long getTime(Date date) {
		return date.getTime() / 1000;
	}

	public static int getDay(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * s - 表示 "yyyy-mm-dd" 形式的日期的 String 对象
	 * 
	 * @param f
	 * @return
	 */
	public static Date valueOf(String s) {
		final int YEAR_LENGTH = 4;
		final int MONTH_LENGTH = 2;
		final int DAY_LENGTH = 2;
		final int MAX_MONTH = 12;
		final int MAX_DAY = 31;
		int firstDash;
		int secondDash;
		int threeDash = 0;
		int fourDash = 0;
		Date d = null;

		if (s == null) {
			throw new java.lang.IllegalArgumentException();
		}

		firstDash = s.indexOf('-');
		secondDash = s.indexOf('-', firstDash + 1);
		if (s.contains(":")) {
			threeDash = s.indexOf(':');
			fourDash = s.indexOf(':', threeDash + 1);
		}
		if ((firstDash > 0) && (secondDash > 0) && (secondDash < s.length() - 1)) {
			String yyyy = s.substring(0, firstDash).trim();
			String mm = s.substring(firstDash + 1, secondDash).trim();
			String dd = "";
			String hh = "";
			String MM = "";
			String ss = "";
			if (s.contains(":")) {
				dd = s.substring(secondDash + 1, threeDash - 3).trim();
				hh = s.substring(threeDash - 2, threeDash).trim();
				MM = s.substring(threeDash + 1, fourDash).trim();
				ss = s.substring(fourDash + 1).trim();
			} else {
				dd = s.substring(secondDash + 1).trim();
			}
			if (yyyy.length() == YEAR_LENGTH && mm.length() == MONTH_LENGTH && dd.length() == DAY_LENGTH) {
				int year = Integer.parseInt(yyyy);
				int month = Integer.parseInt(mm);
				int day = Integer.parseInt(dd);
				int hour = 0;
				int minute = 0;
				int second = 0;
				if (s.contains(":")) {
					hour = Integer.parseInt(hh);
					minute = Integer.parseInt(MM);
					second = Integer.parseInt(ss);
				}
				if (month >= 1 && month <= MAX_MONTH) {
					int maxDays = MAX_DAY;
					switch (month) {
					// February determine if a leap year or not
					case 2:
						if ((year % 4 == 0 && !(year % 100 == 0)) || (year % 400 == 0)) {
							maxDays = MAX_DAY - 2; // leap year so 29 days in
													// February
						} else {
							maxDays = MAX_DAY - 3; // not a leap year so 28 days
													// in February
						}
						break;
					// April, June, Sept, Nov 30 day months
					case 4:
					case 6:
					case 9:
					case 11:
						maxDays = MAX_DAY - 1;
						break;
					}
					if (day >= 1 && day <= maxDays) {
						Calendar cal = Calendar.getInstance();
						cal.set(year, month - 1, day, hour, minute, second);
						cal.set(Calendar.MILLISECOND, 0);
						d = cal.getTime();
					}
				}
			}
		}
		if (d == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return d;
	}

	/**
	 * @author lijie
	 * @param Begin
	 * @param end
	 *            传入开始时间 和 结束时间 格式如：2012-09-07
	 * @return 返回Map 获取相隔多少年 get("YEAR")及为俩个时间年只差，月 天，类推 Key ： YEAR MONTH DAY
	 *         如果开始时间 晚于 结束时间 return null；
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getApartTime(String Begin, String end) {
		String[] temp = Begin.split("-");
		String[] temp2 = end.split("-");
		if (temp.length > 1 && temp2.length > 1) {
			Calendar ends = Calendar.getInstance();
			Calendar begin = Calendar.getInstance();

			begin.set(NumberUtil.getInt(temp[0]), NumberUtil.getInt(temp[1]), NumberUtil.getInt(temp[2]));
			ends.set(NumberUtil.getInt(temp2[0]), NumberUtil.getInt(temp2[1]), NumberUtil.getInt(temp2[2]));
			if (begin.compareTo(ends) < 0) {
				Map map = new HashMap();
				ends.add(Calendar.YEAR, -NumberUtil.getInt(temp[0]));
				ends.add(Calendar.MONTH, -NumberUtil.getInt(temp[1]));
				ends.add(Calendar.DATE, -NumberUtil.getInt(temp[2]));
				map.put("YEAR", ends.get(Calendar.YEAR));
				map.put("MONTH", ends.get(Calendar.MONTH) + 1);
				map.put("DAY", ends.get(Calendar.DATE));
				return map;
			}
		}
		return null;
	}

	public static Date rollHour(Date d, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	public static Date rollDay(Date d, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	public static Date rollMon(Date d, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, mon);
		return cal.getTime();
	}

	public static Date rollYear(Date d, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	public static Date rollDate(Date d, int year, int mon, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, mon);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 获取当前时间的秒数字符串
	 * 
	 * @return
	 */
	public static String getNowTimeStr() {
		String str = Long.toString(System.currentTimeMillis() / 1000);
		return str;
	}

	// v1.6.6.2 RDPROJECT-277 xx 2013-10-22 start
	/**
	 * 获取当前时间-时间戳
	 * 
	 * @return
	 */
	public static int getNowTime() {
		return Integer.parseInt((System.currentTimeMillis() / 1000) + "");
	}

	/**
	 * 获取当前时间-时间戳
	 * 
	 * @return
	 */
	public static long getNowTimeLong() {
		return Long.parseLong((System.currentTimeMillis() / 1000) + "");
	}

	// v1.6.6.2 RDPROJECT-277 xx 2013-10-22 end

	public static String getTimeStr(Date time) {
		long date = time.getTime();
		String str = Long.toString(date / 1000);
		return str;
	}

	public static String getTimeStr(String dateStr, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date;
		date = sdf.parse(dateStr);
		String str = DateUtil.getTimeStr(date);
		return str;
	}

	public static String rollMonth(String addtime, String time_limit) {
		Date t = DateUtil.rollDate(DateUtil.getDate(addtime), 0, NumberUtil.getInt(time_limit), 0);
		return t.getTime() / 1000 + "";
	}

	public static String rollDay(String addtime, String time_limit_day) {
		Date t = DateUtil.rollDate(DateUtil.getDate(addtime), 0, 0, NumberUtil.getInt(time_limit_day));
		return t.getTime() / 1000 + "";
	}

	public static Date getIntegralTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getLastIntegralTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getLastSecIntegralTime(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(d.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static long getTime(String format) {
		long t = 0;
		if (StringUtil.isBlank(format))
			return t;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(format);
			t = date.getTime() / 1000;
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return t;
	}

	// 获取本周日的日期
	public static String getCurrentWeekday() {
		int mondayPlus = DateUtil.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(Calendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	@SuppressWarnings("unused")
	public static String getMondayOFWeek() {
		int weeks = 0;
		int mondayPlus = DateUtil.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(Calendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获取当前月第一天：
	public static String getFirstDayOfMonth(String first) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		first = format.format(c.getTime());
		return first;
	}

	// 获取当月最后一天
	public static String getLastDayOfMonth(String last) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		last = format.format(ca.getTime());
		return last;
	}

	/**
	 * 日期月份处理
	 * 
	 * @param d
	 *            时间
	 * @param month
	 *            相加的月份，正数则加，负数则减
	 * @return
	 */
	public static Date timeMonthManage(Date d, int month) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(d);
		rightNow.add(Calendar.MONTH, month);
		return rightNow.getTime();
	}

	/**
	 * 获取指定年月的最后一天
	 * 
	 * @param year_time
	 *            指定年
	 * @param month_time
	 *            指定月
	 * @return
	 */
	public static Date monthLastDay(int year_time, int month_time) {
		Calendar cal = Calendar.getInstance();
		cal.set(year_time, month_time, 0, 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * 获取指定年月的第一天
	 * 
	 * @param year_time
	 *            指定年
	 * @param month_time
	 *            指定月
	 * @return
	 */
	public static Date monthFirstDay(int year_time, int month_time) {
		Calendar cal = Calendar.getInstance();
		cal.set(year_time, month_time - 1, 1, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定时间月的第一天
	 * 
	 * @param d
	 *            指定时间，为空代表当前时间
	 * @return
	 */
	public static Date currMonthFirstDay(Date d) {
		Calendar cal = Calendar.getInstance();
		if (d != null)
			cal.setTime(d);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定时间月的最后一天
	 * 
	 * @param d
	 *            指定时间，为空代表当前时间
	 * @return
	 */
	public static Date currMonthLastDay(Date d) {
		Calendar cal = Calendar.getInstance();
		if (d != null)
			cal.setTime(d);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * 获取指定时间的年
	 * 
	 * @param date
	 *            指定时间
	 * @return
	 */
	public static int getTimeYear(Date date) {
		if (date == null)
			date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取指定时间的月
	 * 
	 * @param date
	 *            指定时间
	 * @return
	 */
	public static int getTimeMonth(Date date) {
		if (date == null)
			date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定时间的天
	 * 
	 * @param date
	 *            指定时间
	 * @return
	 */
	public static int getTimeDay(Date date) {
		if (date == null)
			date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	public static Date getFirstSecIntegralTime(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(d.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.DATE, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定时间天的结束时间
	 * 
	 * @param d
	 * @return
	 */
	public static Date getDayEndTime(long d) {
		Date day = new Date(d * 1000);
		if (d <= 0)
			day = new Date();
		Calendar cal = Calendar.getInstance();
		if (day != null)
			cal.setTimeInMillis(day.getTime());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * 获取指定时间天的开始时间
	 * 
	 * @param d
	 * @return
	 */
	public static Date getDayStartTime(long d) {
		Date day = new Date(d * 1000);
		if (d <= 0)
			day = new Date();
		Calendar cal = Calendar.getInstance();
		if (day != null)
			cal.setTimeInMillis(day.getTime());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		return cal.getTime();
	}

	public static Date parseDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param beginDate
	 *            yyyy-MM-dd
	 * @param endDate
	 *            yyyy-MM-dd
	 * @param compareDate
	 *            yyyy-MM-dd
	 * @return
	 */
	public static boolean compareDate(String beginDate, String endDate, String compareDate) {
		Date begin = parseDate(beginDate + " 00:00:00");
		Date end = parseDate(endDate + " 23:59:59");
		Date compare = parseDate(compareDate + " 12:00:00");

		return compare.getTime() > begin.getTime() && compare.getTime() < end.getTime();
	}

	/**
	 * 14位
	 * 
	 * @param d
	 * @return
	 */
	public static String getDayTimeStr(long d) {
		Date day = new Date(d * 1000);
		return dateStr3(day);
	}

	/**
	 * 将20150401转化为2015-04-01
	 * 
	 * @return
	 */
	public static String parseString(String dateString) {
		if (dateString == null || dateString.length() != 8) {
			return dateString;
		}
		return dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6, 8);
	}

	/**
	 * 将101212转化为10:12:12
	 * 
	 * @return
	 */
	public static String parseTimeString(String dateString) {
		if (dateString == null || dateString.length() != 8) {
			return dateString;
		}
		return dateString.substring(0, 2) + ":" + dateString.substring(2, 4) + ":" + dateString.substring(4, 6);
	}

	public static String defaultToday(String date) {
		if (StringUtil.isBlank(date)) {
			return dateStr2(new Date());
		}
		return date;
	}

	/**
	 * 
	 * @param date
	 *            时间格式的字符串
	 * @param format
	 *            要转化的时间格式
	 * @param flag
	 *            当为0时为起始时间，为1时为截止时间,在原来的时间上加一天
	 *            最后将得到的millistime/1000得到mysql符合的时间戳
	 * @return
	 * @author zyx@chuangke18.com
	 * @throws ParseException
	 * @date 2016年8月28日
	 */
	public static String parseUnixTimeStampString(String dateStr, String format, int flag) throws ParseException {
		if (StringUtil.isBlank(dateStr)) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(format);

			Date date;
			date = sdf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (flag == 0) {
				return String.valueOf(cal.getTime().getTime() / 1000);
			} else if (flag == 1) {
				cal.add(Calendar.DATE, 1);
				return String.valueOf(cal.getTime().getTime() / 1000);
			}
		}
		return null;
	}

	
	public static Date stampToDate(Long lt){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lt*1000);
        /*res = simpleDateFormat.format(date);
        return res;*/
        return date;
    }
	
	/**
	 * 
	 * @Description 判断是否是时间格式
	 * @Author zyx@chuangke18.com
	 * @Date 2016年8月30日 下午3:14:26
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime() / 1000);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static Long date2Unix(String date_str, String format, Integer day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(date_str);
		if (day > 0) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);
			date = calendar.getTime();
		}
		return date.getTime() / 1000;
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static Long date2UnixByPos(String date_str, Integer day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.fullDate);
		date_str += " 00:00:00";
		Date date = sdf.parse(date_str);
		Calendar calendar = Calendar.getInstance();
		if (day == 1) {
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);
			date = calendar.getTime();
		}
		return date.getTime() / 1000;
	}

	/**
	 * 判断时间是否符合yyyy-MM-dd格式
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean isYMDFormat(String dateStr) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		Pattern pattern = Pattern.compile(datePattern1);
		Matcher match = pattern.matcher(dateStr);
		return match.matches();
	}

	public static Long stringDate2TimeStamp(String dateStr) {
		Long timestamp = 0l;
		if (isYMDFormat(dateStr)) {
			dateStr += " 00:00:00";
			timestamp = Long.parseLong(date2TimeStamp(dateStr, fullDate));
		}
		return timestamp;
	}

	public static String getNowFullDate(String type) {
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		Date date = new Date();
		String datestr = sdf.format(date);
		return datestr;
	}

	/**
	 * 时间戳转换为日期对象
	 */
	public static String unix2Date(Long unix,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(unix);
		String datestr = sdf.format(date);
		return datestr;
	}

	/**
	 * 时间转这符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate2String(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String datestr = sdf.format(date);
		return datestr;
	}

	/**
	 * 时间戳换成时间
	 * 
	 * @param i
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Calendar unixToDate(Long unix) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.fullDate);
		Date date = new Date(unix * 1000);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}
	
	/**
	 * 计算出指定的日期
	 * 
	 * @param date
	 * @parm days
	 * @return
	 */
	public static Date getCalculatedDate(Date date, int days) {
		return new Date(date.getTime() + (long)days * 24 * 60 * 60 * 1000);
	}	
	
	public static String parseStrToFormat(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.JOINDATE);
		Date date = sdf.parse(dateStr);
		SimpleDateFormat sdf1 = new SimpleDateFormat(DateUtil.fullDate);
		return sdf1.format(date);
	}

	/**
	 * 生成还款计划 生成还款计划 规则以当天为基准，依下个月当天生成还款计划，但是会也碰到29号30号31号这种情况时，需要向后顺延一天 使用方法
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String args[]) throws ParseException {
		//System.out.println(getNowTimeLong());
		/*System.out.println(getTimeStr("20170408111927", fullDate));*/
	//	System.out.println(sdf.format(date));
		System.out.println(fullDate.length());
		System.out.println(stampToDate(1491374599l)); 
		//System.out.println(getTimeStr("20170408111927"));
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.JOINDATE);
		Date date = sdf.parse("20170408111927");
		SimpleDateFormat sdf1 = new SimpleDateFormat(DateUtil.fullDate);
		System.out.println(sdf1.format(date));
	}

	/**
	 * 
	 * @return
	 */
	public static String getNowTimeStamp(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dft = new SimpleDateFormat(JOINDATE);
		String timeStamp = dft.format(cal.getTime());
		return timeStamp;
	}
	/**
	 * 
	 * 描述:获取下一个月.
	 * 
	 * @return
	 */
	public static String getNextMonthDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, 1);
		SimpleDateFormat dft = new SimpleDateFormat(fullDate);
		String preMonth = dft.format(cal.getTime());
		return preMonth;
	}

	/**
	 * 获取当前时间str，格式yyyyMMddHHmmssssss
	 * 精确到毫秒
	 * @return
	 * @author guoyx
	 */
	public static String getCurrentDateTimeStr() {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmssssss");
		Date date = new Date();
		String timeString = dataFormat.format(date);
		return timeString;
	}

	
	/**
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeToMis(){
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String timeString = dataFormat.format(date);
		return timeString;
	}
	
	public static String getCurrentDateTimeSS(){
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmssss");
		Date date = new Date();
		String timeString = dataFormat.format(date);
		return timeString;
	}
	
	/**
	 * 页面上的日时转化成Date类型。
	 * 
	 * 使用中
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTime(final String str) throws ParseException {
		Date date1 = null;
		// try {
		date1 = DateUtils.parseDate(str, null, ALL_DT_PATTERN);
		// } catch (Exception e) {
		// logger.debug("DateUtil.parseYMDDate():" + e.getMessage());
		// }
		return date1;
	}
}

