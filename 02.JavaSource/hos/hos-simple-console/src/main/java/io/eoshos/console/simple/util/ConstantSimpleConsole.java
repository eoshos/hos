package io.eoshos.console.simple.util;
/**  
* 
* @ClassName: ConstantSimpleConsole
* @Description: TODO
* @author hehongjian
* @date 2018年7月2日 下午3:36:30
*
*/
public interface ConstantSimpleConsole {
	
	/**
	 *  状态 
	 */
	interface STAT {
		/** 0 */
		String ZERO = "0";	
		/** 1*/
		String ONE = "1";		
	}		

	/**
	 *  实名状态 
	 */
	interface REAL_STAT {
		/** 待认证 */
		String AUTH = "0";	
		/** 认证中*/
		String AUTHING = "1";	
		/** 认证成功*/
		String AUTHED = "2";	
		/** 认证失败*/
		String AUTHFAIL = "3";		
	}	
	
	/**
	 *  交易标志：D-借方(资金减少) C-贷方(资金增加) N-己方交易
	 */
	interface DC_FLAG {
		/** 借方(资金减少) */
		String D = "D";	

		/** 贷方(资金增加)*/
		String C = "C";	
		
		/** N-己方交易*/
		String N = "N";	
	}	

	/**
	 *  交易类型：1-活动增加 9-己方交易时的解冻 8-己方交易时的冻结
	 */
	interface TRANS_TYPE {
		/** 活动增加 */
		String GIFT = "1";	

		/** 冻结*/
		String FREEZE = "8";	
		
		/** 解冻*/
		String DEFREEZE = "9";	
	}	
}
