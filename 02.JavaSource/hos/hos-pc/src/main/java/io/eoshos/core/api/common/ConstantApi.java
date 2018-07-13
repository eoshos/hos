package io.eoshos.core.api.common;

public interface ConstantApi{
	
	/**
	 * ERROR_CODE
	 */
	interface ERROR_CODE {
		String SUCCESS = "0000";
		String ERROR = "9999";
		/** 手機號碼不能為空！  */
		String CODE_9998 = "9998";

		/** 圖形驗證碼不能為空！ */
		String CODE_9997 = "9997";

		/** 短信驗證碼不能為空！*/
		String CODE_9996 = "9996";

		/** 密碼不能為空！*/
		String CODE_9995 = "9995";

		/** 手機號碼已存在！*/
		String CODE_9994 = "9994";

		/** 手機號碼格式不對！*/
		String CODE_9993 = "9993";

		/** 圖形驗證碼錯誤！*/
		String CODE_9992 = "9992";

		/** 短信驗證碼錯誤！*/
		String CODE_9991 = "9991";

		/** 請輸入（6-18位字母和數字）密碼！*/
		String CODE_9990 = "9990";

		/** 邀請碼不存在！*/
		String CODE_9989 = "9989";

		/** 手機號碼錯誤*/
		String CODE_9988 = "9988";

		/** 輸入錯誤，請重新輸入*/
		String CODE_9987 = "9987";
		
		/** 註冊失敗，請稍後重試 */
		String CODE_9986 = "9986";

		/** 您輸入的身份證號碼已經被認證過！ */
		String CODE_9985 = "9985";
		
		/** 同一ip注册次数超限，请24h后重试！ */
		String CODE_9984 = "9984";
	}	

	/**
	 * ERROR_MSG
	 */
	interface ERROR_MSG {
		String SUCCESS = "成功";
		String ERROR = "失败";
		/** 手機號碼不能為空！  */
		String MSG_9998 = "手機號碼不能為空！";

		/** 圖形驗證碼不能為空！ */
		String MSG_9997 = "圖形驗證碼不能為空！";

		/** 短信驗證碼不能為空！*/
		String MSG_9996 = "短信驗證碼不能為空！";

		/** 密碼不能為空！*/
		String MSG_9995 = "密碼不能為空！";

		/** 手機號碼已存在！*/
		String MSG_9994 = "手機號碼已存在！";

		/** 手機號碼格式不對！*/
		String MSG_9993 = "手機號碼格式不對！";

		/** 圖形驗證碼錯誤！*/
		String MSG_9992 = "圖形驗證碼錯誤！";

		/** 短信驗證碼錯誤！*/
		String MSG_9991 = "短信驗證碼錯誤！";

		/** 請輸入（6-18位字母和數字）密碼！*/
		String MSG_9990 = "請輸入（6-18位字母和數字）密碼！";

		/** 邀請碼不存在！*/
		String MSG_9989 = "邀請碼不存在！";

		/** 手機號碼錯誤*/
		String MSG_9988 = "手機號碼錯誤！";

		/** 輸入錯誤，請重新輸入*/
		String MSG_9987 = "輸入錯誤，請重新輸入！";
		
		/** 註冊失敗，請稍後重試*/
		String MSG_9986 = "註冊失敗，請稍後重試！";
		
		/** 您輸入的身份證號碼已經被認證過！ */
		String MSG_9985 = "您輸入的身份證號碼已經被認證過！";
		
		/** 同一ip注册次数超限，请24h后重试！ */
		String MSG_9984 = "同一ip注册次数超限，请24h后重试！";

	}
	
	
	
}
