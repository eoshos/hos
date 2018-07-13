package io.eoshos.pc.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtil {
	public static double format(double d,String format){
		DecimalFormat df = new DecimalFormat(format); 
		String ds=df.format(d);
		return Double.parseDouble(ds);
	}
	//v1.6.7.2 RDPROJECT-725 wcw 2014-3-14 start
	public static double format2(double d){
		return BigDecimalUtil.decimal(d, 2);
	}
	
	public static String format2Str(double d){
		return BigDecimalUtil.decimal(d, 2)+"";
	}
	
	public static double format4(double d){
		return BigDecimalUtil.decimal(d, 4);
	}
	
	public static double format6(double d){
		return BigDecimalUtil.decimal(d, 6);
	}
	public static double getDouble(String str){
		if(str==null||str.equals(""))
			return 0.0;
		double ret=0.0;
		try {
			ret=Double.parseDouble(str);
		} catch (NumberFormatException e) {
			ret=0.0;
		}
		return format6(ret);
	}

	public static double getDouble2(String str) {
		if (str == null || str.equals(""))
			return 0.0;
		double ret = 0.0;
		try {
			ret = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			ret = 0.0;
		}
		return format2(ret);
	}

	// v1.6.7.2 RDPROJECT-548 lx 2013-12-13 end
	public static long getLong(String str){
		if(str==null||str.equals(""))
			return 0L;
		long ret=0;
		try {
			ret=Long.parseLong(str.trim());
		} catch (NumberFormatException e) {
			ret=0;
		}
		return ret;
	}
	
	public static Long[] getLongs(String[] str){
		
		if(str==null||str.length<1)
			return new Long[]{0L};
		Long[] ret=new Long[str.length];
		for(int i=0;i<str.length;i++){
			ret[i]=getLong(str[i]);
		}
		return ret;
	}
	
	public static int getInt(String str){
		if(str==null||str.equals(""))
			return 0;
		int ret=0;
		try {
			ret=Integer.parseInt(str);
		} catch (NumberFormatException e) {
			ret=0;
		}
		return ret;
	}
	
	public static int compare(double x,double y){
		BigDecimal val1=new BigDecimal(x);
		BigDecimal val2=new BigDecimal(y);
		return val1.compareTo(val2);
	}
	
	/**
	 * @param d
	 * @param len
	 * @return
	 */
	public static double ceil(double d,int len){
		String str=Double.toString(d);
		int a=str.indexOf(".");
		if(a+3>str.length()){
			a=str.length();
		}else{
			a=a+3;
		}
		str=str.substring(0, a);
		return Double.parseDouble(str);
	}
	
	public static double ceil(double d){
		return ceil(d,2);
	}	
	
	public static String formatMoney(double money){
		 DecimalFormat d1 =new DecimalFormat("#,##0.####;(#)");
	  	 return d1.format(money);
	}
	
	
	/**
	 * 获取最大整数倍金额
	 *@param useAccount
	 *@param lowestAccount
	 *@return
	 */
	public static double getMaxTenderMoney(double useAccount,double lowestAccount){
		if(useAccount < lowestAccount){
			return 0;
		}
		return ((int)(useAccount/lowestAccount))*lowestAccount;
	}
	
	public static void main(String[] args) {
        System.out.println(getMaxTenderMoney(400,200));
	}
	
	/**
	 * 通过对时间格式 包括“/” "-" 将/与-替换为空字符串
	 * @param str
	 * @return
	 * @author zyx@chuangke18.com
	 * @date 2016年8月28日
	 */
	public static String dateToInt(String str){
		if(StringUtil.isBlank(str)){
			return "0";
		}else {
			String date = "";
			if(str.contains("/")|| str.contains("-")){
				date = str.replaceAll("/", "");
			}else {
				date = str;
			}
			return  date;
		}
	}
}
