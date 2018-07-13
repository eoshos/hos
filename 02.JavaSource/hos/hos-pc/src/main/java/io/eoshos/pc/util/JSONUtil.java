/**    
 * @Description: 
 * @copyright www.chuangke18.com All Rights Reserved  
 * @author nsf@chuangke18.com
 * @date 2016年3月8日 上午9:52:00  
 * @version V2.0 
 */
package io.eoshos.pc.util;

import java.util.List;

import com.alibaba.fastjson.JSON;

public class JSONUtil {

	/**
	 * 列表转json
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String listToJson(List list){
		return JSON.toJSONString(list);
	}
	
	
	public static String objectToJson(Object obj){
		return JSON.toJSONString(obj);
	}
	
	
	
}
