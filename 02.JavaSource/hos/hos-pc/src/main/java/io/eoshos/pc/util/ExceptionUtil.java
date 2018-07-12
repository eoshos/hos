package io.eoshos.pc.util;

import java.sql.SQLException;

import io.eoshos.core.api.bean.ExceptionCommonBean;

/**
 * 对异常的进行捕获封装
 * @author zyx@chuangke18.com
 * @date 2017年4月6日
 * @version 1.0
 */
public class ExceptionUtil {
	public static ExceptionCommonBean logException(Throwable exThrowable) {
		if (exThrowable instanceof Exception) {
			exThrowable = (Exception) exThrowable;
		}
		if (exThrowable instanceof SQLException) {
			exThrowable = (SQLException) exThrowable;
		}
		StackTraceElement stackTraceElement = exThrowable.getStackTrace()[exThrowable.getStackTrace().length-1]; 
		ExceptionCommonBean exceptionCommonBean = new ExceptionCommonBean();
		exceptionCommonBean.setErrLile("<br/>异常文件："+stackTraceElement.getFileName());
		exceptionCommonBean.setErrLile("<br/>异常行数："+stackTraceElement.getLineNumber());
		exceptionCommonBean.setErrLile("<br/>异常方法："+stackTraceElement.getMethodName());
		return exceptionCommonBean;
	}
}
