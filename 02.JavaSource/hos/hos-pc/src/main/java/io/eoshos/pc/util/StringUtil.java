package io.eoshos.pc.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	private static String REGEX_NUMBER = "123456789.";

	public static String null2String(String str) {
		return str == null ? "" : str.trim();
	}

	public static boolean isBlank(String str) {
		// return str==null||str.trim().getBytes().length==0?true:false;
		return StringUtil.isNull(str).equals("");
	}

	/**
	 * 隐藏手机号码中间四位
	 * 
	 * @param phone
	 * @return
	 */
	public static String hidePhone(String phone) {
		if (phone == null || phone.trim().length() != 11) {
			return phone;
		}
		return phone.trim().substring(0, 3) + "****" + phone.trim().substring(7);
	}

	/**
	 * 隐藏身份证号生日
	 * 
	 * @param phone
	 * @return
	 */
	public static String hideCardId(String cardId) {
		if (cardId == null || (cardId.length() != 15 && cardId.length() != 18)) {
			return cardId;
		}
		if (cardId.length() == 18) {
			return "***************" + cardId.substring(15);
		} else if (cardId.length() == 15) {
			return "************" + cardId.substring(12);
		}
		return cardId;
	}

	public static String hideBankCard(String bankCard) {
		if (isBlank(bankCard) || bankCard.length() < 11) {
			return bankCard;
		}
		StringBuffer result = new StringBuffer("");
		for (byte i = 0; i < bankCard.length(); i++) {
			if (i + 4 >= bankCard.length()) {
				result.append(bankCard.charAt(i));
			} else {
				if ((bankCard.length() - 4 - i - 1) % 4 == 0) {
					result.append("* ");
				} else {
					result.append("*");
				}
			}
		}
		return result.toString();
	}

	public static String hideRealName(String realname) {
		if (realname == null || "".equals(realname.trim())) {
			return realname;
		}
		return realname.substring(0, 1) + "**" + realname.substring(realname.length() - 1);
	}

	/**
	 * string字符串中参数替换
	 * 
	 * @param str
	 * @param args
	 * @return
	 */
	public static String replaceStringVarable(String str, String... args) {
		String retString = str;
		for (int i = 0; i < args.length; i++) {
			retString = retString.replace("${" + i + "}", args[i]);
		}
		return retString;
	}

	/**
	 * 如果str为null，返回“”,否则返回str
	 * 
	 * @param str
	 * @return
	 */
	public static String isNull(String str) {
		if (str == null) {
			return "";
		}
		// v1.6.7.1 去除参数前后空格 xx 2013-11-04 start
		return str.trim();
		// v1.6.7.1 去除参数前后空格 xx 2013-11-04 end
	}

	public static String isNull(Object o) {
		if (o == null) {
			return "";
		}
		String str = "";
		if (o instanceof String) {
			str = (String) o;
		} else {
			str = o.toString();
		}
		return str;
	}

	// v1.6.5.3 RDPROJECT-147 xx 2013.09.11 start
	/**
	 * 检验手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		phone = isNull(phone);
		Pattern regex = Pattern
				// .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
				.compile("^((1[34578]{1}[0-9]))\\d{8}$");
		Matcher matcher = regex.matcher(phone);
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	// v1.6.5.3 RDPROJECT-147 xx 2013.09.11 end

	// v1.6.6.2 注册时加强真实姓名验证 start
	/**
	 * 检查是否全中文，返回true 表示是 反之为否
	 * 
	 * @param realname
	 * @return
	 */
	public static boolean isChinese(String realname) {
		realname = isNull(realname);
		// v1.6.7.1 RDPROJECT-423 xx 2013-11-15 start
		Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
		// v1.6.7.1 RDPROJECT-423 xx 2013-11-15 end
		Matcher matcher = regex.matcher(realname);
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	// v1.6.6.2 注册时加强真实姓名验证 end

	/**
	 * 检查email是否是邮箱格式，返回true表示是，反之为否
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		email = isNull(email);
		Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 检查身份证的格式，返回true表示是，反之为否
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isCard(String cardId) {
		cardId = isNull(cardId);
		// 身份证正则表达式(15位)
		Pattern isIDCard1 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		// 身份证正则表达式(18位)
		Pattern isIDCard2 = Pattern
				.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
		Matcher matcher1 = isIDCard1.matcher(cardId);
		Matcher matcher2 = isIDCard2.matcher(cardId);
		boolean isMatched = matcher1.matches() || matcher2.matches();
		return isMatched;
	}

	/**
	 * 判断字符串是否为整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern regex = Pattern.compile("\\d*");
		Matcher matcher = regex.matcher(str);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (isEmpty(str)) {
			return false;
		}

		Pattern regex = Pattern.compile("(-)?\\d*(.\\d*)?");
		Matcher matcher = regex.matcher(str);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String firstCharUpperCase(String s) {
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1, s.length()));
		return sb.toString();
	}

	public static String hideChar(String str, int len) {
		String s = "";
		for (int i = 0; i < len; i++) {
			s += "*";
		}
		str = str.charAt(0) + s + str.charAt(str.length() - 1);
		return str;
	}

	public static String hideFirstChar(String str, int len) {
		if (str == null)
			return null;
		char[] chars = str.toCharArray();
		if (str.length() <= len) {
			for (int i = 0; i < 1; i++) {
				chars[i] = '*';
			}
		} else {
			for (int i = 0; i < len; i++) {
				chars[i] = '*';
			}
		}
		str = new String(chars);
		return str;
	}

	public static String hideLastChar(String str, int len) {
		if (str == null)
			return null;
		char[] chars = str.toCharArray();
		if (str.length() <= len) {
			for (int i = 0; i < chars.length; i++) {
				chars[i] = '*';
			}
		} else {
			for (int i = chars.length - 1; i > chars.length - len - 1; i--) {
				chars[i] = '*';
			}
		}
		str = new String(chars);
		return str;
	}

	/**
	 * 
	 * @return
	 */
	public static String format(String str, int len) {
		if (str == null)
			return "-";
		if (str.length() <= len) {
			int pushlen = len - str.length();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < pushlen; i++) {
				sb.append("0");
			}
			sb.append(str);
			str = sb.toString();
		} else {
			String newStr = str.substring(0, len);
			str = newStr;
		}
		return str;
	}

	public static String contact(Object[] args) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 是否包含在以“，”隔开字符串内
	 * 
	 * @param s
	 * @param type
	 * @return
	 */
	public static boolean isInSplit(String s, String type) {
		if (isNull(s).equals("")) {
			return false;
		}
		List<String> list = Arrays.asList(s.split(","));
		if (list.contains(type)) {
			return true;
		}
		return false;
	}

	public static String array2Str(Object[] arr) {
		StringBuffer s = new StringBuffer();
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				s.append(arr[i]);
				if (i < arr.length - 1) {
					s.append(",");
				}
			}
		}
		return s.toString();
	}

	public static String array2Str(int[] arr) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			s.append(arr[i]);
			if (i < arr.length - 1) {
				s.append(",");
			}
		}
		return s.toString();
	}

	/**
	 * 指定起始位置字符串隐藏
	 * 
	 * @param str
	 * @param index1
	 * @param index2
	 * @return
	 */
	public static String hideStr(String str, int index1, int index2) {
		if (str == null)
			return null;
		String str1 = str.substring(index1, index2);
		String str2 = str.substring(index2);
		String str3 = "";
		if (index1 > 0) {
			str1 = str.substring(0, index1);
			str2 = str.substring(index1, index2);
			str3 = str.substring(index2);
		}
		char[] chars = str2.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] = '*';
		}
		str2 = new String(chars);
		String str4 = str1 + str2 + str3;
		return str4;
	}

	// 四舍五入保留两位小数点
	public static String SetNumberFractionDigits(String str) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		return nf.format(Float.valueOf(str));
	}

	public static String getMonday(String the_rq) {
		int n = getXC_days(the_rq);
		// System.out.println("n="+n);
		n = n * -1;
		return Q_N_Day(n, the_rq);
	}

	// 获取输入日期的星期天日期

	public static String getSunday(String the_rq) {
		int n = getXC_days(the_rq);
		// System.out.println("n="+n);
		n = (6 - (n * -1)) * -1;
		return Q_N_Day(n, the_rq);
	}

	// 获得输入日期与本周一相差的天数
	public static int getXC_days(String rq) {
		SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");// formatYMD表示的是yyyy-MM-dd格式
		SimpleDateFormat formatD = new SimpleDateFormat("E");// "E"表示"day in
																// week"
		Date d = null;
		String weekDay = "";
		int xcrq = 0;
		try {
			d = formatYMD.parse(rq);// 将String 转换为符合格式的日期
			weekDay = formatD.format(d);
			if (weekDay.equals("星期一")) {
				xcrq = 0;
			} else {
				if (weekDay.equals("星期二")) {
					xcrq = -1;
				} else {
					if (weekDay.equals("星期三")) {
						xcrq = -2;
					} else {
						if (weekDay.equals("星期四")) {
							xcrq = -3;
						} else {
							if (weekDay.equals("星期五")) {
								xcrq = -4;
							} else {
								if (weekDay.equals("星期六")) {
									xcrq = -5;
								} else {
									if (weekDay.equals("星期日")) {
										xcrq = -6;
									}

								}

							}

						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return xcrq;
	}

	public static String Q_N_Day(int N, String d1) {//
		String[] d2 = d1.split("-");
		int year = Integer.parseInt(d2[0]);
		int month = Integer.parseInt(d2[1]);
		int day = Integer.parseInt(d2[2]);
		if (day - N <= 0) {
			if (month == 1) {
				year = year - 1;
				month = 12;
				day = day + 31 - N;
			} else {
				month = month - 1;
				if (month == 2) {
					if (year % 4 == 0) {
						day = day + 29 - N;
					} else {
						day = day + 28 - N;
					}
				} else {
					if (month == 4 || month == 6 || month == 9 || month == 11) {
						day = day + 30 - N;
					} else {
						day = day + 31 - N;
					}
				}
			}
		} else {
			///////////////////////////////////////////////////////////////////////////////////
			if (month == 12) {
				if ((day - N) > 31) {
					year = year + 1;
					month = 1;
					day = (day - N) - 31;
				} else {
					day = day - N;
				}
			} else {
				if (month == 2) {
					if (year % 4 == 0) {
						if ((day - N) > 29) {
							month++;
							day = (day - N) - 29;
						} else {
							day = day - N;
						}
					} else {
						if ((day - N) > 28) {
							month++;
							day = (day - N) - 28;
						} else {
							day = day - N;
						}
					}
				} else {
					if (month == 4 || month == 6 || month == 9 || month == 11) {
						if ((day - N) > 30) {
							month++;
							day = (day - N) - 30;
						} else {
							day = day - N;
						}
					} else {
						if ((day - N) > 31) {
							month++;
							day = (day - N) - 31;
						} else {
							day = day - N;
						}
					}
				}
			}
		}
		String str = String.valueOf(year);
		if (month < 10) {
			str = str + "-0" + String.valueOf(month);
		} else {
			str = str + "-" + String.valueOf(month);
		}
		if (day < 10) {
			str = str + "-0" + String.valueOf(day);
		} else {
			str = str + "-" + String.valueOf(day);
		}

		return str;
	}

	public static String fillTemplet(String templet, String phstr, String[] paras) {
		StringBuffer templetSB = new StringBuffer(templet);
		int i = 0;
		while (templetSB.indexOf(phstr) >= 0 && i < paras.length) {
			templetSB.replace(templetSB.indexOf(phstr), templetSB.indexOf(phstr) + phstr.length(), paras[i]);
			i++;
		}
		return templetSB.toString();
	}

	public static int[] strarr2intarr(String[] strarr) {
		int[] result = new int[strarr.length];
		for (int i = 0; i < strarr.length; i++) {
			result[i] = Integer.parseInt(strarr[i]);
		}
		return result;
	}

	/**
	 * 大写字母转成“_”+小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toUnderline(String str) {
		char[] charArr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		sb.append(charArr[0]);
		for (int i = 1; i < charArr.length; i++) {
			if (charArr[i] >= 'A' && charArr[i] <= 'Z') {
				sb.append('_').append(charArr[i]);
			} else {
				sb.append(charArr[i]);
			}
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 根据身份证计算性别
	 * 
	 * @param cardId
	 * @return
	 */
	public static int getSexByCardid(String cardId) {

		int sexNum = 0;
		if (cardId.length() == 15) {
			sexNum = cardId.charAt(13);
		} else {
			sexNum = cardId.charAt(16);
		}
		if (sexNum % 2 == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 根据身份证计算生日
	 * 
	 * @param cardId
	 * @return
	 */
	public static String getBirthdayByCardid(String cardId) {
		String birth = null;
		if (cardId.length() == 15) {
			birth = cardId.substring(6, 12);
		} else {
			birth = cardId.substring(6, 14);
		}
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		String birthday = null;
		try {
			birthday = sf2.format(sf1.parse(birth));
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return birthday;
	}

	public static String fillTemplet(String template, Map<String, Object> sendData) {
		template = template.replace('`', '\'');
		try {
			return FreemarkerUtil.renderTemplate(template, sendData);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}

	public synchronized static String generateTradeNO(long userid, String type) {
		String s;
		s = type + userid + getFullTimeStr();
		return s;
	}

	public static String getFullTimeStr() {
		String s = DateUtil.dateStr3(Calendar.getInstance().getTime());
		return s;
	}

	/**
	 * 
	 * @Description 判断是否是数字类型
	 * @Author zyx@chuangke18.com
	 * @Date 2016年8月30日 下午2:55:26
	 * @param str
	 * @return
	 */
	public static boolean assertNumber(String str) {
		if (!isBlank(str)) {
			for (int i = 0; i < str.length(); i++) {
				if (REGEX_NUMBER.indexOf(str.charAt(i)) == -1) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static int[] stringToIntArr(String intarrs) {
		char[] chars = intarrs.toCharArray();
		int[] ints = new int[2];
		for (int i = 0; i < chars.length; i++) {
			ints[i] = Integer.parseInt(String.valueOf(chars[i]));
		}
		return ints;
	}

	public static String lpad(String s, int n, String replace) {
		while (s.length() < n) {
			s = replace + s;
		}
		return s;
	}

	public static String rpad(String s, int n, String replace) {
		while (s.length() < n) {
			s = s + replace;
		}
		return s;
	}
	
	/**
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String firstLetterUpperCase(String sourceStr){
		return sourceStr.substring(0, 1).toUpperCase()+sourceStr.substring(1, sourceStr.length());
	}

	public static long[] stringToLong(String stringArray[]) {
        if (stringArray == null || stringArray.length < 1) {
            return null;
        }
        long longArray[] = new long[stringArray.length];
        for (int i = 0; i < longArray.length; i++) {
            try {
                longArray[i] = Long.valueOf(stringArray[i]);
            } catch (NumberFormatException e) {
                longArray[i] = 0;
                continue;
            }
        }
        return longArray;
    }
	
	public static Long[] string2Long(String stringArray[]) {
        if (stringArray == null || stringArray.length < 1) {
            return null;
        }
        Long longArray[] = new Long[stringArray.length];
        for (int i = 0; i < longArray.length; i++) {
            try {
                longArray[i] = Long.valueOf(stringArray[i]);
            } catch (NumberFormatException e) {
                longArray[i] = 0l;
                continue;
            }
        }
        return longArray;
    }
	
	public static boolean isNotNull(Object o){
		if (o == null){
			return false;
		}
		if (StringUtil.isBlank((String)o)){
			return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		char a = '0';

		/*String jdbc ="jdbc:mysql://114.55.149.136:3306/cfsdb?characterEncoding=utf8";
		int first  = jdbc.indexOf("3306/");
		String db = jdbc.substring(jdbc.lastIndexOf("/")+1, jdbc.indexOf("?"));
		System.out.println(db);
		System.out.println(first);
		System.out.println((int) a);
		System.out.println("06".toCharArray()[0] == 0);
		System.out.println("06".indexOf("0"));*/
		System.out.println(lpad("11", 6, "0"));
	}
}
