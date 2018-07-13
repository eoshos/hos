package io.eoshos.console.simple.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**  
* 
* @ClassName: DateUtil
* @Description: TODO
* @author hehongjian
* @date 2018年7月2日 下午6:10:30
*
*/
public class DateUtil {
	
	public static long getTime(String format) {
		long t = 0;
		if (StringUtils.isBlank(format))
			return t;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(format);
			t = date.getTime() / 1000;
		} catch (ParseException e) {
			//logger.error(e.getMessage());
		}
		return t;
	}

}
